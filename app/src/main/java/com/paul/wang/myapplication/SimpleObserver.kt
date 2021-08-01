package com.paul.wang.myapplication

import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class SimpleObserver<String> : Observer<String> {
    override fun onSubscribe(d: Disposable?) {

    }
    override fun onNext(t: String?) {

    }
    override fun onError(e: Throwable?) {
    }
    override fun onComplete() {
    }
}
