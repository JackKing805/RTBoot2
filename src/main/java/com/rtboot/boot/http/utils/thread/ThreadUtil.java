package com.rtboot.boot.http.utils.thread;

import java.util.concurrent.*;
import java.util.function.Supplier;

/**
 * 线程池工具
 *
 * @author luxiaolei
 */
public class ThreadUtil {
    public static ThreadPoolExecutor newExecutor(int corePoolSize, int maximumPoolSize) {
        return ExecutorBuilder.create()
                .setCorePoolSize(corePoolSize)
                .setMaxPoolSize(maximumPoolSize)
                .build();
    }
}
