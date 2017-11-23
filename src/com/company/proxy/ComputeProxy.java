package com.company.proxy;

import com.company.callback.CallbackListener;
import com.company.task.ComputeTask;
import com.company.task.TResult;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class ComputeProxy {
    private final ExecutorService executorService;
    private int threadFinishNum;
    private int threadCount;
    private File[] files;
    private CallbackListener listener;
    private Map<String, TResult> sumMap = new ConcurrentHashMap<>();

    public ComputeProxy(String directoryPath) {
        File root = new File(directoryPath);
        if (root.isDirectory()) {
            files = root.listFiles();
            threadCount = files.length;
            System.out.println("threadCount==============" + threadCount);
        }
        executorService = Executors.newFixedThreadPool(files.length);
        threadFinishNum = 0;
    }

    public void setCallbackListener(CallbackListener callbackListener) {
        this.listener = callbackListener;
    }

    private InnerHandler proxyListener = new InnerHandler() {
        @Override
        public void postResult(Map<String, TResult> threadMap) {
            threadFinishNum++;
            doStat(threadMap);
            if (threadFinishNum == threadCount) {
                listener.onFinish(sumMap);
            }
        }
    };

    private void doStat(Map<String, TResult> threadMap) {
        for (String domain : threadMap.keySet()) {
            if (!sumMap.containsKey(domain)) {
                sumMap.put(domain, threadMap.get(domain));
            } else {
                TResult sumResult = sumMap.get(domain);
                TResult threadResult = threadMap.get(domain);
                sumResult.setCount(sumResult.getCount() + threadResult.getCount());
                sumResult.setResponTime(sumResult.getResponTime() + threadResult.getResponTime());
                sumMap.put(domain, sumResult);
            }
        }
    }

    public void execute() {
        for (File file : files) {
            ComputeTask task = new ComputeTask(file, proxyListener);
            executorService.execute(task);
        }
    }
}
