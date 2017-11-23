package com.company.task;

import com.company.callback.CallbackListener;

import java.io.File;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class ComputeTask implements Runnable {
    private final File file;

    public ComputeTask(File file, CallbackListener listener) {
        this.file = file;
    }

    @Override
    public void run() {

    }
}
