package com.example.rxkotlinretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.example.rxkotlinretrofit.Models.TimeModel
import com.example.rxkotlinretrofit.Retrofit.TimeAPI
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var txtTime:TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtTime = findViewById(R.id.txtTime)
        progressBar = findViewById(R.id.progressBar)

        var url = "http://worldtimeapi.org/"

        var retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(url)
            .build()
        var timeAPI = retrofit.create(TimeAPI::class.java)

        var time = timeAPI.getTime()

        time.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .repeatWhen { complete -> complete.delay(1000, TimeUnit.MILLISECONDS) }
            .subscribe(object :Observer<TimeModel>{  //Observer ==>io.reactivex
                override fun onComplete() {
                    Log.d("AAA", "completed chay thu 3")
                }
                override fun onSubscribe(d: Disposable) {
                    Log.d("AAA", "onSubscribe chay thu 1")
                }
                override fun onNext(t: TimeModel) {
                    progressBar.visibility = View.VISIBLE
                    txtTime.text = t.unixtime
                    Log.d("AAA", "onNext chay thu 2" + t.unixtime)
                }
                override fun onError(e: Throwable) {
                    Log.d("AAA", "Effor neu loi chay thu 2" + e.message)
                }
            })
    }
}
