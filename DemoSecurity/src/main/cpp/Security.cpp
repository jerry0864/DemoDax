//
// Created by N6057 on 2017/9/27.
//

#include "Security.h"
#include <jni.h>
#include <string>
#include "md5.h"
extern "C"
JNIEXPORT jstring JNICALL
Java_com_dax_demo_security_Jni_stringFromJNI(JNIEnv *env, jclass type, jstring source_) {
    const char *source = env->GetStringUTFChars(source_, 0);
    MD5 md5(source);
    //
//    jclass TestProvider = (*jniEnv)->FindClass(jniEnv,"com/duicky/TestProvider");
//    TestProvider mTestProvider = (*jniEnv)->NewObject(jniEnv, TestProvider,construction_id);
//    jmethodID getTime = (*jniEnv)->GetStaticMethodID(jniEnv, TestProvider, "getTime","()Ljava/lang/String;"); //都是通过类找到方法
//    jmethodID sayHello = (*jniEnv)->GetMethodID(jniEnv, TestProvider, "sayHello","(Ljava/lang/String;)V"); //都是通过类找到方法

    env->ReleaseStringUTFChars(source_, source);
    return env->NewStringUTF(md5.toStr().c_str());
}




