package com.paul.wang.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val textView = findViewById<TextView>(R.id.text)
        val observable = Observable.just("abc")

        val observer = object : Observer<String> {
            override fun onSubscribe(d: Disposable?) {
            }
            override fun onNext(t: String?) {
                textView?.text = t
            }
            override fun onError(e: Throwable?) {
            }
            override fun onComplete() {
            }
        }

        observable.subscribe(observer)
    }
}
