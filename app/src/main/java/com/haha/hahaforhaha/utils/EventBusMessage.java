package com.haha.hahaforhaha.utils;


public class EventBusMessage<T> {
    private T code;
    private T data;

    public EventBusMessage(T code) {
        this.code = code;
    }

    public EventBusMessage(T code, T data) {
        this.code = code;
        this.data = data;
    }

    public T getCode() {
        return code;
    }

    public void setCode(T code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "EventMessage{" +
                "code=" + code +
                ", data=" + data +
                '}';
    }


}
