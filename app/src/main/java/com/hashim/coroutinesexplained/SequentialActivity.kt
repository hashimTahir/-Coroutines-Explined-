/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sequential.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlin.system.measureTimeMillis

class SequentialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequential)
        hSetupView()

    }

    private fun hSetupView() {
        hClickMeB.setOnClickListener {
            hFakeApiRequest()

        }
    }

    private fun hFakeApiRequest() {
        CoroutineScope(IO).launch {
            val hExecutionTime = measureTimeMillis {
                val hLaunch1 = async {
                    hSetTextOnMain("Thread name is ${Thread.currentThread().name}")
                    hGetResultFromApi1()
                }.await()
                val hLaunch2 = async {
                    hSetTextOnMain("Thread name is ${Thread.currentThread().name}")
                    try {
                        hGetResultFromApi2("lklkj")
                    } catch (e: CancellationException) {
                        e.message
                    }
                }.await()
                if (hLaunch2 != null) {
                    hSetTextOnMain(hLaunch2)
                }
            }
            hSetTextOnMain("Total execution time $hExecutionTime")
        }
    }


    private fun hSetTextOnMain(hText: String) {
        CoroutineScope(Dispatchers.Main).launch {
            hSetTextToTv(hText)
        }
    }

    private fun hSetTextToTv(hText: String) {
        val text = hDetialTv.text.toString()
        hDetialTv.text = text + "\n$hText"
    }

    private suspend fun hGetResultFromApi1(): String {
        delay(1000)
        return Constants.H_RESULT_1

    }

    private suspend fun hGetResultFromApi2(hLaunch1: String): String {
        delay(1700)
        if (hLaunch1.equals(Constants.H_RESULT_1)) {
            return Constants.H_RESULT_2
        }
        throw CancellationException("Result 1 was incorrect")

    }
}