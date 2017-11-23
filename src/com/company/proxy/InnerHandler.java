package com.company.proxy;

import com.company.task.TResult;

import java.util.Map;

/**
 * Created by shishaojie on 2017/11/23 0023.
 */
public interface InnerHandler {
    void postResult(Map<String, TResult> resultMap);
}
