/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_asynchronous.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.system.measureTimeMillis

class AsynchronousActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_asynchronous)
        hSetupView()
    }

    private fun hSetupView() {
        hClickMeB.setOnClickListener {
            hSetTextToTv("Button Clicked")
            hFakeApiRequest()
        }
    }

    private fun hFakeApiRequest() {
        val hStartTime = System.currentTimeMillis()
        val hParentJob = CoroutineScope(IO).launch {

            var hTotalTime = measureTimeMillis {

                var launch1 = launch {
                    val hTime1 = measureTimeMillis {
                        val hGetResultFromApi1 = hGetResultFromApi1()
                        hSetTextOnMain("Result From api 1 $hGetResultFromApi1")
                    }
                    hSetTextOnMain("Time for job 1 $hTime1")


                }
                var launch2 = launch {
                    val hTime2 = measureTimeMillis {
                        val hGetResultFromApi2 = hGetResultFromApi2()
                        hSetTextOnMain("Result From api 2 $hGetResultFromApi2")
                    }
                    hSetTextOnMain("Time for job 2 $hTime2")
                }


            }
            hSetTextOnMain("Total time from parent $hTotalTime")
        }
        hParentJob.invokeOnCompletion {
            hSetTextOnMain("Total Time is ${System.currentTimeMillis() - hStartTime}")
        }
    }


    private fun hSetTextOnMain(hText: String) {
        CoroutineScope(Main).launch {
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

    private suspend fun hGetResultFromApi2(): String {
        delay(1700)
        return Constants.H_RESULT_2

    }

}
