package com.example.observableassignment

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.BackpressureStrategy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // declare variable
        val button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this, "Stream has started", Toast.LENGTH_LONG).show()
        }
        startStream()
    }

    @SuppressLint("CheckResult")
    fun startStream() {
        val observable = PublishSubject.create<Int>()
        observable
            .toFlowable(BackpressureStrategy.DROP)
            .observeOn(Schedulers.computation())
            .subscribe(

                {
                    println("The Number Is: $it")
                },
                { t ->
                    print(t.message)
                }
            )

        for (i in 0..1000000) {
            observable.onNext(i)
        }
    }

    override fun onStop() {
        super.onStop()
        //observable.dispose()
    }
}
