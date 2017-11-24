package com.company.proxy;

import com.company.callback.CallbackListener;
import com.company.task.ComputeTask;
import com.company.task.TResult;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class ComputeProxy {
    private final ExecutorService executorService;
    private int taskFinishNum;
    private int fileSum;
    private File[] files;
    private CallbackListener listener;
    private Map<String, TResult> sumMap = new HashMap<>();
    private List<Map<String, TResult>> finishList = new ArrayList<>();

    public ComputeProxy(String directoryPath) {
        File root = new File(directoryPath);
        if (root.isDirectory()) {
            files = root.listFiles();
            fileSum = files.length;
            System.out.println("fileSum==============" + fileSum);
        }
        //todo 机械硬盘/SSD 分别测试执行效率
        executorService = Executors.newFixedThreadPool(1);
        taskFinishNum = 0;
    }

    public void setCallbackListener(CallbackListener callbackListener) {
        this.listener = callbackListener;
    }

    private Object lock=new Object();
    private InnerHandler proxyListener = new InnerHandler() {
        @Override
        public void postResult(Map<String, TResult> threadMap) {
//            synchronized (lock) {
                taskFinishNum++;
                System.out.println("taskFinishNum==========="+ taskFinishNum);
                finishList.add(threadMap);
//                doStat(threadMap);
                if (taskFinishNum == fileSum) {
                    doStat(finishList);
//                    listener.onFinish(sumMap);
                }
//            }
        }
    };

    private void doStat(List<Map<String, TResult>> finishList) {
        for (Map<String, TResult> threadMap : finishList) {
            doStat(threadMap);
        }

        listener.onFinish(sumMap);
    }

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
