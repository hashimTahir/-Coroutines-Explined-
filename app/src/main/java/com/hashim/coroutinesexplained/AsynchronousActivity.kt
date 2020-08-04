/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_asynchronous.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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
//            hFakeApiRequest()
            hFakeApiRequest2()
        }
    }

    private fun hFakeApiRequest2() {
        /*with async result is available right here
        *
        * wheareas result is stuck inside coroutine scope. has to get from
        * Main*/
        CoroutineScope(IO).launch {
            val hExecutionTime = measureTimeMillis {

                val launch1: Deferred<String> = async {
                    hSetTextOnMain("Thread Name is  ${Thread.currentThread().name}")
                    hGetResultFromApi1()
                }
                val launch2: Deferred<String> = async {
                    hSetTextOnMain("Thread Name is  ${Thread.currentThread().name}")
                    hGetResultFromApi2()
                }

                hSetTextOnMain("Got ${launch1.await()}")
                hSetTextOnMain("Got ${launch2.await()}")
            }

            hSetTextOnMain("Total Time $hExecutionTime")

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
