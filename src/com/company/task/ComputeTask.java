package com.company.task;

import com.company.proxy.InnerHandler;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public class ComputeTask implements Runnable {
    private final File file;
    private final InnerHandler handler;

    public ComputeTask(File file, InnerHandler handler) {
        this.file = file;
        this.handler = handler;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = null;
        Map<String, TResult> resultMap = new HashMap<>();
        try {
            FileReader reader = new FileReader(file);
            bufferedReader = new BufferedReader(reader);
            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                String[] lines = str.trim().split("\t");
                if (lines.length > 13 && !lines[13].equals("-") && !lines[13].contains(",")) {
                    //domain
                    String domain = lines[10];
                    //respon time
                    float responTime = Float.parseFloat(lines[13]);
                    if (!resultMap.keySet().contains(domain)) {
                        TResult tResult = new TResult(domain, 1, responTime);
                        resultMap.put(domain, tResult);
                    } else {
                        TResult tResult = resultMap.get(domain);
                        tResult.setCount(tResult.getCount() + 1);
                        tResult.setResponTime(tResult.getResponTime() + responTime);
                        resultMap.put(domain, tResult);
                    }
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        // 解析文件,将统计结果回调
        handler.postResult(resultMap);
    }
}
