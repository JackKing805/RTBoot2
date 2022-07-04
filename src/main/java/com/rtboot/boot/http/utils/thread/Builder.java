package com.rtboot.boot.http.utils.thread;

import java.io.Serializable;

public interface Builder<T> extends Serializable {
    /**
     * 构建
     *
     * @return 被构建的对象
     */
    T build();
}