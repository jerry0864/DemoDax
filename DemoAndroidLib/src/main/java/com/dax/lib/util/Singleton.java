package com.dax.lib.util;

public abstract class Singleton<T> {

    private T value;

    public Singleton() {

    }

    public T get() {
        if (value == null) {
            syncInit();
        }
        return value;
    }

    protected abstract T init();

    private void syncInit() {
        synchronized (this) {
            if (value == null) {
                value = init();
            }
        }
    }

    public void release() {
        value = null;
    }
}
