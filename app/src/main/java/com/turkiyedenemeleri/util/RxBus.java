package com.turkiyedenemeleri.util;


import com.turkiyedenemeleri.base.BaseEvent;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by safakesberk on 01/05/2017.
 */

public class RxBus {

    private final Subject<BaseEvent, BaseEvent> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(BaseEvent o) {
        _bus.onNext(o);
    }

    public Observable<BaseEvent> toObserverable() {
        return _bus;
    }
}