package com.company.proxy;

import com.company.callback.CallbackListener;
import com.company.task.ComputeTask;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class ComputeProxy {
    private final ExecutorService executorService;
    private int threadCount;
    private File[] files;
    private CallbackListener listener;

    public ComputeProxy(String directoryPath) {
        File root = new File(directoryPath);
        if (root.isDirectory()) {
            files = root.listFiles();
            threadCount = files.length;
        }
        executorService = Executors.newFixedThreadPool(files.length);
    }


    public void setCallbackListener(CallbackListener callbackListener) {
        this.listener = callbackListener;
    }

    public void execute() {
        for (File file : files) {
            ComputeTask task = new ComputeTask(file, listener);
            executorService.execute(task);
        }
    }
}
