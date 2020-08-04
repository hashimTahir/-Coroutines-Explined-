/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.*
import timber.log.Timber

/*App crashes if any of childer jobs fails/throws an exception
*
* To catch exception attach coroutine exception handler to the parent job
* */
class ExceptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)
        hMain()
    }

    private fun hMain() {
        val hExceptionHandler = CoroutineExceptionHandler { _, hException ->
            Timber.d("Exception thrown in one of the children $hException")
        }
        val hJob = CoroutineScope(Dispatchers.IO).launch(hExceptionHandler) {

            val hJob1 = launch {
                Timber.d("Result hjob1 ${hGetResult(1)}")
            }
            hJob1.invokeOnCompletion {
                if (it != null) {
                    Timber.d("Exception occured on hJob1 $it")
                }
            }

            val hJob2 = launch {
                Timber.d("Result hjob2 ${hGetResult(2)}")
            }
            hJob2.invokeOnCompletion {
                if (it != null) {
                    Timber.d("Exception occured on hJob2 $it")
                }
            }


            val hJob3 = launch {
                Timber.d("Result hjob3 ${hGetResult(3)}")
            }
            hJob1.invokeOnCompletion {
                if (it != null) {
                    Timber.d("Exception occured on hJob3 $it")
                }
            }

        }

        hJob.invokeOnCompletion {
            if (it != null) {
                Timber.d("Parent Job failed  $it")

            } else {
                Timber.d("Parent Job completed")
            }
        }
    }

    suspend fun hGetResult(number: Int): Int {
        delay(number * 550L)
        if (number == 2) {
            throw Exception("Job 2 failed with number code $number")
        }
        return number * 2

    }

}