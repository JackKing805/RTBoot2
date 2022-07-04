package com.rtboot.boot.http.utils;

import com.rtboot.boot.http.utils.thread.ThreadUtil;

import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class ThreadAgentUtils {
    private static ThreadAgentUtils instance;

    public synchronized static ThreadAgentUtils getInstance(){
        if(instance == null){
            instance = new ThreadAgentUtils();
        }
        return instance;
    }

    ThreadPoolExecutor threadPoolExecutor;

    private ThreadAgentUtils(){
        threadPoolExecutor = ThreadUtil.newExecutor(5, Integer.MAX_VALUE);
    }

    public void execute(Runnable runnable){
        if(threadPoolExecutor!=null){
            threadPoolExecutor.execute(runnable);
        }
    }

    public Future<?> submit(Runnable runnable){
        if(threadPoolExecutor!=null){
            return threadPoolExecutor.submit(runnable);
        }
        return null;
    }

    public void shutdown(boolean isNow){
        if(threadPoolExecutor!=null){
            if(isNow){
                threadPoolExecutor.shutdownNow();
            }else {
                threadPoolExecutor.shutdown();
            }
        }
    }
}
