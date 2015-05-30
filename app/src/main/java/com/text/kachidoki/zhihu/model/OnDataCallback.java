package com.text.kachidoki.zhihu.model;

/**
 * Created by Frank on 15/5/2.
 */
public interface OnDataCallback<T> {
    void callback(T...list);
}
