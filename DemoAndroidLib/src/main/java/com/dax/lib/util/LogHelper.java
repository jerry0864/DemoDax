
package com.dax.lib.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public final class LogHelper {

    public static boolean IS_DEBUG = true;

    private static String TAG = "dax_test";

    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()_%s";

    //日志写入本地调试开关
    public static boolean LOG2FILE_DEBUG = false;

    public static void log(String str) {
        if (IS_DEBUG) {
            StackTraceElement traceElement = Thread.currentThread()
                    .getStackTrace()[3];// 从堆栈信息中获取当前被调用的方法信息
            String className = traceElement.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            String logText = String.format(CLASS_METHOD_LINE_FORMAT,
                    className,
                    traceElement.getMethodName(),
                    String.valueOf(traceElement.getLineNumber()));
            Log.i(TAG, logText + "->" + str);// 打印Log
            log2File(str);
        }
    }

    public static void printStackTrace(Throwable throwable) {
        if (IS_DEBUG) {
            Log.w(TAG, "", throwable);
            error2File(throwable);
        }
    }

    public static void d(String log) {
        d(TAG, log);
    }

    public static void d(String tag, String log) {
        if (IS_DEBUG) {
            Log.d(tag, log);
            log2File(tag,log);
        }
    }

    public static void e(String log) {
        e(TAG, log);
    }

    public static void e(String tag, String log) {
        if (IS_DEBUG) {
            Log.e(tag, log);
            log2File(tag,log);
        }
    }

    public static void i(String log) {
        i(TAG, log);
    }

    public static void i(String tag, String log) {
        if (IS_DEBUG) {
            Log.i(tag, log);
            log2File(tag,log);
        }
    }

    public static void log(Throwable throwable) {
        if (IS_DEBUG) {
            throwable.printStackTrace();
            error2File(throwable);
        }
    }

    public static void uploadCatchedException(Throwable throwable) {
        log(throwable);
    }

    /**
     * 将普通日志写入本地
     * @param log
     */
    private static void log2File(String log){
        if(LOG2FILE_DEBUG){
            writeLog2File(log);
        }
    }

    /**
     * 将普通日志写入本地
     * @param tag
     * @param log
     */
    private static void log2File(String tag, String log){
        if(LOG2FILE_DEBUG){
            writeLog2File("tag= " + tag + " | " + log);
        }
    }

    /**
     * 将错误日志写入本地
     * @param throwable
     */
    private static void error2File(Throwable throwable){
        if(LOG2FILE_DEBUG){
            writeLog2File(getErrorLog(throwable));
        }
    }

    public static void writeLog2File(String content) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }

        String logPath = Environment.getExternalStorageDirectory() + "/dax/log";
        String logName = "log_" + TimeUtil.formatTime(System.currentTimeMillis(), "yyyy-MM-dd") + ".txt";
        File logFile = new File(logPath, logName);

        StringBuffer sb = new StringBuffer()
                .append("["+TimeUtil.formatTime(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss")+"] ")
                .append(content)
                .append("\n========\n");
        FileUtil.write(logFile, sb.toString(), true);
    }

    private static String getErrorLog(Throwable ex) {
        if (ex == null) {
            return "null error response";
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String sb = writer.toString();
        return sb;
    }
}
