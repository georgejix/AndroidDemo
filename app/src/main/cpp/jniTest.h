#ifndef JNITEST.H_SRC
#define JNITEST.H_SRC

#include <jni.h>

JNIEXPORT jint JNICALL Java_com_jx_androiddemo_tool_JniTest_test
  (JNIEnv *, jclass);

#endif