package com.company;

import com.company.proxy.ComputeProxy;
import com.company.task.TResult;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main {

    private static ComputeProxy computeProxy;
    private static String directoryPath;
    private static StringBuilder stringBuilder;
    private static final int THREAD_COUNT = 4;/*线程数*/

    public static void main(String[] args) {

        if (args == null || args.length == 0) {
            System.out.println("please input directory path");
            return;
        }

        stringBuilder = new StringBuilder();
        long startTime = System.currentTimeMillis();
        logger(String.format("----------------------开始%s(当前时间戳)-----------------------", startTime));
        computeProxy = new ComputeProxy(args[0], THREAD_COUNT);
        directoryPath = args[0];

        computeProxy.setCallbackListener((Map<String, TResult> resultSumMap) -> {
            logger("----------------------结果------------------------");
            //1.study.koolearn.com的访问总数；
            printStudy(resultSumMap);
            //2.平均响应时间最快的top10域名、次数、平均响应时间(单位s，保留小数点后3位)，按响应时间升序排列。
            printTop10(resultSumMap);
            long endTime = System.currentTimeMillis();
            logger(String.format("----------------------结束%s(当前时间戳)-----------------------", endTime));
            logger(String.format("----------------------总耗时：%s ms---------", endTime - startTime));
            //3.计算结果可以在控制台打印，也可以输出到/tmp目录的文件中，要规定格式输出结果
            printToFile(args[1]);
            System.exit(0);
        });
        computeProxy.execute();
    }

    private static void printToFile(String outDirectoryPath) {
        if (outDirectoryPath == null) {
            System.out.println("please input log file path");
            return;
        }
        File file = new File(outDirectoryPath);
        if (!file.exists()) {
            file.mkdir();
        }
        File logFile = new File(outDirectoryPath +
                "/" +
                "stat_" +
                new SimpleDateFormat("yyyy_MM_dd_HHmmsss").format(new Date()) +
                "_threadCount_" + THREAD_COUNT +
                ".log");
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(logFile);
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void logger(String string) {
        System.out.println(string);
        stringBuilder.append(string + "\n");
    }

    private static void printStudy(Map<String, TResult> resultSumMap) {
        String domain = "study.koolearn.com";
        logger("study.koolearn.com" + "\t\t" + (resultSumMap.containsKey(domain) ? resultSumMap.get(domain).getCount() : 0));
    }

    private static void printTop10(Map<String, TResult> resultSumMap) {
        List<TResult> list = new ArrayList<>();
        for (TResult result : resultSumMap.values()) {
            list.add(result);
        }
        Collections.sort(list, (o1, o2) -> {
            if (o1.getAvaTime() > o2.getAvaTime()) {
                return 1;
            } else if (o1.getAvaTime() == o2.getAvaTime()) {
                return 0;
            } else {
                return -1;
            }
        });
        for (int i = 0; i < 10; i++) {
            TResult tResult = list.get(i);
            logger(tResult.getDomain() + "\t\t" + tResult.getCount() + "\t\t" + String.format("%.3f s", tResult.getAvaTime()));
        }
        logger("总域名数:" + list.size());
    }
}
