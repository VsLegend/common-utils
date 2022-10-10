package com.utils.thread;

import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

/**
 * @Author Wang Junwei
 * @Date 2022/10/10 15:45
 * @Description
 */

public class ExceptionUtils {
    /**
     * 提取真正的异常
     */
    public static Throwable extractRealException(Throwable throwable) {
        if (throwable instanceof CompletionException || throwable instanceof ExecutionException) {
            if (throwable.getCause() != null) {
                return throwable.getCause();
            }
        }
        return throwable;
    }
}