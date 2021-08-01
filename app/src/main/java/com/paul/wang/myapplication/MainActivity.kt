package com.paul.wang.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    var disposable: Disposable? = null
    // A DisposableObserver essentially replaces 2 different objects, Disposable and Observer.
    var disposableObserver: DisposableObserver<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        val observable = Observable.just("abc")

        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                // Disposable here can be used to unsubscribe.
                disposable = d
            }

            override fun onNext(t: String?) {
                textView?.text = t
            }

            override fun onError(e: Throwable?) {
            }

            override fun onComplete() {
            }
        }

        disposableObserver = object : DisposableObserver<String>() {
            override fun onNext(t: String?) {
                TODO("Not yet implemented")
            }
            override fun onError(e: Throwable?) {
                TODO("Not yet implemented")
            }
            override fun onComplete() {
                TODO("Not yet implemented")
            }
        }

        // Learning subscribeOn(), observeOn() on different Schedulers.
        observable.subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).subscribe(disposableObserver)
    }

    override fun onPause() {
        super.onPause()

        disposable?.dispose()

        disposableObserver?.dispose()
    }
}
