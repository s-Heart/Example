package com.company.callback;

import com.company.task.TResult;

import java.util.Map;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public interface CallbackListener {
    void onFinish(Map<String, TResult> resultSumMap);
}
