package com.paul.wang.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    // A CompositeDisposable can be used to replace multiple Disposables or DisposableObservers are used.
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        val observable = Observable.just("ab")

        compositeDisposable.add(
            // Learning subscribeOn(), observeOn() on different Schedulers.
            observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                // A DisposableObserver essentially replaces 2 different objects, Disposable and Observer.
                object : DisposableObserver<String>() {
                    override fun onNext(t: String?) {
                        textView?.text = t
                    }

                    override fun onError(e: Throwable?) {
                    }

                    override fun onComplete() {
                    }
                })
        )
    }

    override fun onPause() {
        super.onPause()
        compositeDisposable.dispose()
    }
}
