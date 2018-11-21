package com.chsLib.deviceTracker;

import android.os.Process;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ThreadExecutor {
    private static final String TAG = ThreadExecutor.class.toString();
    private static final int CORE_POOL_SIZE = 2;
    private static final int MAXIMUM_POOL_SIZE = 2;
    private static final int KEEP_ALIVE = 2;
    private static final BlockingQueue<Runnable> sWorkQueue = new LinkedBlockingQueue<Runnable>(MAXIMUM_POOL_SIZE);

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            final String threadName = "CHS #" + mCount.getAndIncrement();
            final Thread thread = new Thread(r, threadName);
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };
    private static final ThreadPoolExecutor sExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE, TimeUnit.SECONDS, sWorkQueue, sThreadFactory);

    public static void runTask(Runnable task) {
        sExecutor.execute(task);
    }

}
