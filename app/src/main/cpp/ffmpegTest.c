#include "ffmpegTest.h"
#include <android/log.h>

#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavfilter/avfiltergraph.h"
#include "libavfilter/buffersink.h"
#include "libavfilter/buffersrc.h"
#include "libavutil/opt.h"


#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "ffmpegtest", format, ##__VA_ARGS__)

JNIEXPORT jint

JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test
        (JNIEnv *env, jclass cls, jstring jstring_input_path, jstring jstring_output_path) {
    //输入地址
    const char *input_path = (*env)->GetStringUTFChars(env, jstring_input_path, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    //输出地址
    const char *output_path = (*env)->GetStringUTFChars(env, jstring_output_path, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_output_path, output_path);
    LOGI("input_path= %s \n output_path= %s \n", input_path, output_path);

    AVFormatContext *avFormatContext;
    int ret;

    av_register_all();
    avFormatContext = avformat_alloc_context();
    if ((ret = avformat_open_input(&avFormatContext, input_path, NULL, NULL)) < 0) {
        LOGI("open failed %d", ret);
        return -1;
    } else {
        LOGI("open success");
    }

    int audio_stream_index = -1;
    AVCodec *avCodec;
    if ((audio_stream_index = av_find_best_stream(avFormatContext,
                                                  AVMEDIA_TYPE_AUDIO, -1,
                                                  -1, &avCodec, 0)) < 0) {
        LOGI("not find audio");
        return -1;
    } else {
        LOGI("find audio %d", audio_stream_index);
    }

    AVCodecContext *avCodecContext;
    avCodecContext = avcodec_alloc_context3(avCodec);
    if (!avCodecContext) {
        LOGI("avCodecContext null");
        return -1;
    }
    avcodec_parameters_to_context(avCodecContext,
                                  avFormatContext->streams[audio_stream_index]->codecpar);
    av_opt_set_int(avCodecContext, "refcounted_frames", 1, 0);

    if ((ret = avcodec_open2(avCodecContext, avCodec, NULL)) < 0) {
        LOGI("avcodec_open2 fail %d", ret);
        return -1;
    } else {
        LOGI("avcodec_open2 success");
    }

    LOGI("finish");
    return 0;
}