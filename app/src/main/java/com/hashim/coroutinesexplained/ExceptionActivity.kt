/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ExceptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exception)
        hMain()
    }

    private fun hMain() {
        val hJob = CoroutineScope(Dispatchers.IO).launch {

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
        return number * 2

    }

}