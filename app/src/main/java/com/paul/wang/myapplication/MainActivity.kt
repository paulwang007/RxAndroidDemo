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
        val createObservable = Observable.create<String> {observable ->
            arrayOf("a", "b", "c").forEach {char ->
                observable.onNext(char)
            }
        }

        compositeDisposable.add(
            // Learning subscribeOn(), observeOn() on different Schedulers.
            createObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(
                // A DisposableObserver essentially replaces 2 different objects, Disposable and Observer.
                object : DisposableObserver<String>() {
                    override fun onNext(t: String) {
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
