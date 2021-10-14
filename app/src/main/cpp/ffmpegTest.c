#include "ffmpegTest.h"
#include <android/log.h>

#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libswscale/swscale.h"


#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "clog", format, ##__VA_ARGS__)

JNIEXPORT jint

JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test
        (JNIEnv *env, jclass cls, jstring jstring_input_path) {
    const char *TAG = "ffmpegtest";

    const char *input_path = (*env)->GetStringUTFChars(env, jstring_input_path, 0);
    (*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    LOGI("%s path=%s", TAG, input_path);

    AVFormatContext *pFormatCtx;
    int ret;

    av_register_all();
    pFormatCtx = avformat_alloc_context();

    ret = avformat_open_input(&pFormatCtx, input_path, NULL, NULL);
    if (ret != 0) {
        LOGI("%s %d Couldn't open input stream.\n", TAG, ret);
        return -1;
    } else {
        LOGI("%s open input stream.\n", TAG);
    }

    return 0;
}