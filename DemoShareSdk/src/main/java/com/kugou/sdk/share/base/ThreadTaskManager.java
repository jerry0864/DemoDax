
package com.kugou.sdk.share.base;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 任务线程池管理（取代new Thread()开启子线程）
 *@author liuxiong
 *@since 2017/1/11 17:54
 */
public class ThreadTaskManager {
    private static ExecutorService mExecutorService = Executors.newCachedThreadPool();

    /**
     * 执行任务
     * 
     * @param task
     */
    public static void excuteTask(Runnable task) {
        if (task != null) {
            mExecutorService.execute(task);
        }
    }

    /**
     * 关闭线程池
     */
    public static void shutDownThreadPool() {
        mExecutorService.shutdown();
    }
}
