package com.turkiyedenemeleri.base;

/**
 * Created by safakesberk on 01/05/2017.
 */

public class BaseEvent {
    private int eventCode;
    private Object data;

    public BaseEvent(int eventCode, Object data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public void setEventCode(int eventCode) {
        this.eventCode = eventCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
