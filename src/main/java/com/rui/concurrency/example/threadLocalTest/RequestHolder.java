package com.rui.concurrency.example.threadLocalTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestHolder {
    public static final ThreadLocal<Long> requestHolder = new ThreadLocal<Long>();

    public static void set(Long id) {
        log.info("set id={}", id);
        requestHolder.set(id);
    }

    public static Long getId() {
        log.info("return id={}", requestHolder.get());
        return requestHolder.get();
    }

    public static void remove() {
        requestHolder.remove();
    }

}
