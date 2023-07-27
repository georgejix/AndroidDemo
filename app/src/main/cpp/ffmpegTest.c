#include "ffmpegTest.h"
#include <android/log.h>

#include "libavcodec/avcodec.h"
#include "libavformat/avformat.h"
#include "libavfilter/buffersink.h"
#include "libavfilter/buffersrc.h"
#include "libavutil/opt.h"
#include "libswresample/swresample.h"
#include "libavutil/samplefmt.h"

#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "ffmpegtest", format, ##__VA_ARGS__)
#define MAX_AUDIO_FARME_SIZE 48000 * 2

void freeObj(AVFormatContext *fmt_ctx, AVFormatContext *ofmt_ctx) {
    //最后别忘了释放内存
    if (fmt_ctx) {
        avformat_close_input(&fmt_ctx);
    }
    if (ofmt_ctx) {
        avio_close(ofmt_ctx->pb);
    }
}

JNIEXPORT jint

JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test
        (JNIEnv *env, jclass cls, jstring jstring_input_path, jstring jstring_output_name) {
    //输入地址
    const char *input_path = (*env)->GetStringUTFChars(env, jstring_input_path, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    //输出地址
    const char *output_name = (*env)->GetStringUTFChars(env, jstring_output_name, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_output_name, output_name);
    LOGI("input_path= %s \n output_path= %s \n", input_path, output_name);


    //定义参数
    //上下文
    AVFormatContext *fmt_ctx = NULL;
    AVFormatContext *ofmt_ctx = NULL;
    //支持各种各样的输出文件格式，MP4，FLV，3GP等等
    AVOutputFormat *output_fmt = NULL;
    //输入流
    AVStream *in_stream = NULL;
    //输出流
    AVStream *out_stream = NULL;
    //存储压缩数据
    AVPacket packet;
    //要拷贝的流
    int audio_stream_index = -1;
    //错误码
    int err_code;

    //1.打开输入文件，提取参数
    //打开输入文件，关于输入文件的所有就保存到fmt_ctx中了
    fmt_ctx = avformat_alloc_context();
    err_code = avformat_open_input(&fmt_ctx, input_path, NULL, NULL);
    if (err_code < 0) {
        LOGI("avformat_open_input fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }
    //找到最好的音频流
    audio_stream_index = av_find_best_stream(fmt_ctx, AVMEDIA_TYPE_AUDIO, -1, -1, NULL, 0);
    if (audio_stream_index < 0) {
        LOGI("av_find_best_stream fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }
    //拿到文件中音频流
    in_stream = fmt_ctx->streams[audio_stream_index];
    //参数信息
    AVCodecParameters *in_codecpar = in_stream->codecpar;


//  //拿到文件中音频流 或者 视频流，所有流都在streams数组中
//  in_stream = fmt_ctx->streams[1];
//  //找到最好的视频流
//  video_stream_index = av_find_best_stream(fmt_ctx, AVMEDIA_TYPE_VIDEO, -1, -1, NULL, 0);
//  packet.dts = av_rescale_q_rnd(packet.dts, in_stream->time_base, out_stream->time_base, (AV_ROUND_NEAR_INF|AV_ROUND_PASS_MINMAX));


    AVCodec *input_codec;
    input_codec = avcodec_find_decoder(in_codecpar->codec_id);
    if (!input_codec) {
        LOGI("avcodec_find_decoder fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }
    char output_path[100];
    sprintf(output_path, "%s.%s", output_name, input_codec->name);

    //2.准备输出文件，输出流
    // 输出上下文
    ofmt_ctx = avformat_alloc_context();
    //根据目标文件名生成最适合的输出容器
    output_fmt = av_guess_format(NULL, output_path, NULL);
    if (!output_fmt) {
        LOGI("av_guess_format fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }
    ofmt_ctx->oformat = output_fmt;
    //新建输出流
    out_stream = avformat_new_stream(ofmt_ctx, NULL);
    if (!out_stream) {
        LOGI("avformat_new_stream fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }

    //3. 数据拷贝
    //3.1 参数信息
    // 将参数信息拷贝到输出流中，我们只是抽取音频流，并不做音频处理，所以这里只是Copy
    if ((err_code = avcodec_parameters_copy(out_stream->codecpar, in_codecpar)) < 0) {
        LOGI("avcodec_parameters_copy fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }
    //3.2 初始化AVIOContext
    //初始化AVIOContext,文件操作由它完成
    if ((err_code = avio_open(&ofmt_ctx->pb, output_path, AVIO_FLAG_WRITE)) < 0) {
        LOGI("avio_open fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }

    //3.3 开始拷贝
    //初始化 AVPacket， 我们从文件中读出的数据会暂存在其中
    av_init_packet(&packet);
    packet.data = NULL;
    packet.size = 0;
    // 写头部信息
    if (avformat_write_header(ofmt_ctx, NULL) < 0) {
        LOGI("avformat_write_header fail");
        freeObj(fmt_ctx, ofmt_ctx);
        return -1;
    }

    jclass clazz = (*env)->GetObjectClass(env, cls);
    jmethodID mID = (*env)->GetMethodID(env, clazz, "onProgressCallBack",
                                        "(JJLjava/lang/String;)V");

    //每读出一帧数据
    while (av_read_frame(fmt_ctx, &packet) >= 0) {
        if (packet.stream_index == audio_stream_index) {
            (*env)->CallVoidMethod(env, cls, mID, in_stream->duration, packet.pts,
                                   (*env)->NewStringUTF(env, output_path));
            //时间基计算，音频pts和dts一致
            packet.pts = av_rescale_q_rnd(packet.pts, in_stream->time_base, out_stream->time_base,
                                          (AV_ROUND_NEAR_INF | AV_ROUND_PASS_MINMAX));
            packet.dts = packet.pts;
            packet.duration = av_rescale_q(packet.duration, in_stream->time_base,
                                           out_stream->time_base);
            packet.pos = -1;
            packet.stream_index = 0;
            //将包写到输出媒体文件
            av_interleaved_write_frame(ofmt_ctx, &packet);
            //减少引用计数，避免内存泄漏
            av_packet_unref(&packet);
        }
    }
    //写尾部信息
    av_write_trailer(ofmt_ctx);
    freeObj(fmt_ctx, ofmt_ctx);
    (*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    (*env)->ReleaseStringUTFChars(env, jstring_output_name, output_name);
    jmethodID mCompleteId = (*env)->GetMethodID(env, clazz, "complete",
                                                "(ILjava/lang/String;)V");
    (*env)->CallVoidMethod(env, cls, mCompleteId, 0, (*env)->NewStringUTF(env, output_path));

    LOGI("complete");
    return 0;
}

void freeObj2(AVFormatContext *fmt_ctx) {
    //最后别忘了释放内存
    if (fmt_ctx) {
        avformat_close_input(&fmt_ctx);
    }
}

typedef struct WavHeader {
    uint32_t chunk_id;   // "RIFF"
    uint32_t chunk_size; // (44-8) + pcm_data_len
    uint32_t format;     // "WAVE"
    uint32_t sub_chunk_1_id;     // "fmt "
    uint32_t sub_chunk_1_size;   // 16 (PCM)
    uint16_t audio_format;       //  1 - PCM
    uint16_t num_channels;       // 1-mono, 2-stero
    uint32_t sample_rate;
    uint32_t byte_rate;          // sample_rate * block_align
    uint16_t block_align;        // num_channels * bit_per_sample
    uint16_t bit_per_sample;
    uint32_t sub_chunk_2_id;     // "data"
    uint32_t sub_chunk_2_size;   //  pcm_data_len
} WavHeader;

void
writeWavHeader(WavHeader *wavHeader, int32_t pcm_data_len, FILE *wav_file, int channels) {
    memcpy(&wavHeader->chunk_id, "RIFF", 4);
    wavHeader->chunk_size = 36 + pcm_data_len;
    memcpy(&wavHeader->format, "WAVE", 4);
    memcpy(&wavHeader->sub_chunk_1_id, "fmt ", 4);
    wavHeader->sub_chunk_1_size = 16;
    wavHeader->audio_format = 1;
    wavHeader->num_channels = channels;
    wavHeader->sample_rate = 16000;
    wavHeader->bit_per_sample = 16;
    wavHeader->block_align = wavHeader->num_channels * wavHeader->bit_per_sample / 8;
    wavHeader->byte_rate = wavHeader->sample_rate * wavHeader->block_align;
    memcpy(&wavHeader->sub_chunk_2_id, "data", 4);
    wavHeader->sub_chunk_2_size = pcm_data_len;

    // 写wav头、PCM数据
    fwrite(wavHeader, 1, sizeof(WavHeader), wav_file);
}

JNIEXPORT jint JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test2
        (JNIEnv *env, jclass cls, jstring jstring_input_path, jstring jstring_output_name
        ) {

    LOGI("avcodec_configuration = %s",avcodec_configuration());
    //输入地址
    const char *input_path = (*env)->GetStringUTFChars(env, jstring_input_path, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    //输出地址
    const char *output_name = (*env)->GetStringUTFChars(env, jstring_output_name, 0);
    //(*env)->ReleaseStringUTFChars(env, jstring_output_name, output_name);
    LOGI("input_path= %s \n output_path= %s \n", input_path, output_name);

    //定义参数
    //上下文
    AVFormatContext *fmt_ctx = NULL;
    //存储压缩数据
    AVPacket packet;
    //要拷贝的流
    int audio_stream_index = -1;
    //错误码
    int err_code;
    AVFrame *decoded_frame = NULL;
    FILE *outfile;
    AVCodecContext *c = NULL;
    AVCodec *dec;


    fmt_ctx = avformat_alloc_context();
    err_code = avformat_open_input(&fmt_ctx, input_path, NULL, NULL);
    if (err_code < 0) {
        LOGI("avformat_open_input fail");
        freeObj2(fmt_ctx);
        return -1;
    }
    audio_stream_index = av_find_best_stream(fmt_ctx, AVMEDIA_TYPE_AUDIO, -1, -1, &dec, 0);
    if (audio_stream_index < 0) {
        LOGI("av_find_best_stream fail");
        freeObj2(fmt_ctx);
        return -1;
    }
    c = avcodec_alloc_context3(dec);
    if (!c) {
        LOGI("Could not allocate audio codec context\n");
        freeObj2(fmt_ctx);
        return -1;
    }
    avcodec_parameters_to_context(c, fmt_ctx->streams[audio_stream_index]->codecpar);
    if (avcodec_open2(c, dec, NULL) < 0) {
        LOGI("Could not open codec\n");
        freeObj2(fmt_ctx);
        return -1;
    }

    //3.3 开始拷贝
    //初始化 AVPacket， 我们从文件中读出的数据会暂存在其中
    av_init_packet(&packet);
    packet.data = NULL;
    packet.size = 0;

    jclass clazz = (*env)->GetObjectClass(env, cls);
    jmethodID mID = (*env)->GetMethodID(env, clazz, "onProgressCallBack",
                                        "(JJLjava/lang/String;)V");

    decoded_frame = av_frame_alloc();
    if (!decoded_frame) {
        LOGI("Could not allocate audio frame\n");
        freeObj2(fmt_ctx);
        return -1;
    }
    outfile = fopen(output_name, "wb+");
    SwrContext *swrCtx = swr_alloc();

    uint8_t *out_buffer1 = (uint8_t *) av_malloc(MAX_AUDIO_FARME_SIZE);
    uint8_t *out_buffer2 = (uint8_t *) av_malloc(MAX_AUDIO_FARME_SIZE);
    uint8_t *out_buffers[2] = {0};
    out_buffers[0] = out_buffer1;
    out_buffers[1] = out_buffer2;

    int32_t pcm_data_len = 0;
    WavHeader *wavHeader = malloc(sizeof(WavHeader));
    writeWavHeader(wavHeader, pcm_data_len, outfile, 1);

    int initSwr = -1;

    //每读出一帧数据
    while (av_read_frame(fmt_ctx, &packet) >= 0) {
        if (packet.stream_index == audio_stream_index) {
            (*env)->CallVoidMethod(env, cls, mID, fmt_ctx->streams[audio_stream_index]->duration,
                                   packet.pts,
                                   (*env)->NewStringUTF(env, output_name));

            if (avcodec_send_packet(c, &packet) < 0) continue;
            if (avcodec_receive_frame(c, decoded_frame) < 0) continue;
            if (-1 == initSwr) {
                swr_alloc_set_opts(swrCtx,
                                   1, AV_SAMPLE_FMT_S16, 16000,
                                   c->channels, c->sample_fmt, c->sample_rate,
                                   0, NULL);
                swr_init(swrCtx);
                initSwr = 1;
            }

            int len = swr_convert(swrCtx, out_buffers, MAX_AUDIO_FARME_SIZE,
                                  (const uint8_t **) decoded_frame->data,
                                  decoded_frame->nb_samples);
            pcm_data_len += len * 2;
            fwrite(out_buffers[0], 2, len, outfile);
            //减少引用计数，避免内存泄漏
            av_packet_unref(&packet);
        }
    }
    fseek(outfile, 0, SEEK_SET);
    writeWavHeader(wavHeader, pcm_data_len, outfile, 1);
    fclose(outfile);

    swr_free(&swrCtx);
    av_frame_free(&decoded_frame);

    freeObj2(fmt_ctx);
    (*env)->ReleaseStringUTFChars(env, jstring_input_path, input_path);
    (*env)->ReleaseStringUTFChars(env, jstring_output_name, output_name);
    jmethodID mCompleteId = (*env)->GetMethodID(env, clazz, "complete",
                                                "(ILjava/lang/String;)V");
    (*env)->CallVoidMethod(env, cls, mCompleteId, 0, (*env)->NewStringUTF(env, output_name));

    LOGI("complete");
    return 0;
}