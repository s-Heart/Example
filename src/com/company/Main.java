package com.company;

import com.company.callback.CallbackListener;
import com.company.proxy.ComputeProxy;

public class Main {

    private static ComputeProxy computeProxy;
    private static String directoryPath;

    public static void main(String[] args) {

        if (args == null || args[0].isEmpty()) {
            System.out.println("please input directory path");
            return;
        }

        computeProxy = new ComputeProxy(args[0]);
        directoryPath = args[0];

        computeProxy.setCallbackListener(new CallbackListener() {
            @Override
            public void onFinish() {

            }
        });

        computeProxy.execute();

    }
}
