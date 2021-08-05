package com.paul.wang.myapplication

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.function.Function

class MainActivity : AppCompatActivity() {
    // A CompositeDisposable can be used to replace multiple Disposables or DisposableObservers are used.
    private var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)

        // just() is the most simple observable.
        val observable = Observable.just("ab")
        // fromArray() will emit one item at a time when using varargs, if using a whole array, only the array will be emitted once.
        val arrayObservable = Observable.fromArray(arrayOf("a", "b", "c"))
        // range operator emits a range of Int or Long. Could throw IllegalArgumentException if range is greater than Integer.MAX_VALUE.
        val rangeObservable = Observable.range(1, 10)
        // Observable.create<T> creates a callback, which will be invoked whenever an Observer subscribes to it. ObservableEmitter will emit type T object.
        val createObservable = Observable.create<String> { observableEmitter ->
            arrayOf("a", "b", "c").forEach { str ->
                observableEmitter.onNext(str)
            }
            observableEmitter.onComplete()
        }.map {
            'f'
        }

        compositeDisposable.add(
            // Learning subscribeOn(), observeOn() on different Schedulers.
            createObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).flatMap{
                // Adding flatMap(), which transforms the items emitted by an Observable into Observables, then flattens the emissions from different Observables into a single Observable.
                Observable.just('d')
            }.concatMap {
                // Concat is similar to flatMap, but it will wait until one observable is finished before moving on to the next observable.
                Observable.just('a')
            }.subscribeWith(
                // A DisposableObserver essentially replaces 2 different objects, Disposable and Observer.
                object : DisposableObserver<Char>() {
                    override fun onNext(t: Char) {
                        textView?.text = t.toString()
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
