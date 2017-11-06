#include <jni.h>
#include <string>

extern "C"

JNIEXPORT jstring JNICALL
Java_com_netease_xyqcbg_jni_JNI_stringFromJNI(JNIEnv *env, jclass type) {
    //create so crash for test
    //int a = 10/0;
    std::string hello = "Hello from C++++1";
    return env->NewStringUTF(hello.c_str());
}
