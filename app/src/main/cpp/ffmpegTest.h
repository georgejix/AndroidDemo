#ifndef FFMPEGTEST.H_SRC
#define FFMPEGTEST.H_SRC

#include <jni.h>

JNIEXPORT jint

JNICALL Java_com_jx_androiddemo_tool_FfmpegTest_test
        (JNIEnv *, jclass, jstring, jstring);

JNIEXPORT jint JNICALL
Java_com_jx_androiddemo_tool_FfmpegTest_test2(JNIEnv *env, jobject thiz, jstring input_path,
                                              jstring output_path);
#endif