#include "ffmpegTest.h"
#include <android/log.h>

#define LOGI(format, ...)  __android_log_print(ANDROID_LOG_INFO,  "clog", format, ##__VA_ARGS__)

JNIEXPORT jint JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test
        (JNIEnv *env, jclass cls){
    LOGI("%s", "ffmpegtest!");
    jclass clazz = (*env)->GetObjectClass(env, cls);
    jmethodID mID = (*env)->GetMethodID(env, clazz, "myCallback", "(I)V");
    (*env)->CallVoidMethod(env, cls, mID, 10);
    return 0;
}