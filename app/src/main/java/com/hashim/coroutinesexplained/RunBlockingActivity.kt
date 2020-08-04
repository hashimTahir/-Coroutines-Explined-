/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.random.Random

class RunBlockingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_run_blocking)

        hMain()
    }
    /*Run blocking  will block the entire thread until it gets completed.*/

    private fun hMain() {
        CoroutineScope(Main)
            .launch {
                Timber.d("Current thread name ${Thread.currentThread().name}")
                Timber.d("Result 1 with value: ${hGetResult()}")
                Timber.d("Result 2 with value:${hGetResult()}")
                Timber.d("Result 3 with value:${hGetResult()}")
                Timber.d("Result 4 with value:${hGetResult()}")
                Timber.d("Result 5 with value:${hGetResult()}")
                Timber.d("Result 6 with value:${hGetResult()}")
                Timber.d("Result 7 with value:${hGetResult()}")
            }
        CoroutineScope(Main).launch {
            delay(1000)
            runBlocking {
                Timber.d("Blocking Current thread name ${Thread.currentThread().name}")
                delay(4000)
                Timber.d("Done Blocking Current thread name ${Thread.currentThread().name}")

            }
        }

    }

    private suspend fun hGetResult(): Int {
        delay(1000)
        return Random.nextInt(0, 100)
    }
}