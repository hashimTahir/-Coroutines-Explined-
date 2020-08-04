/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_global_scope_example.*
import kotlinx.coroutines.*
import timber.log.Timber

class GlobalScopeExampleActivity : AppCompatActivity() {
    lateinit var hJob: Job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global_scope_example)
        hMain()

        hClickMeB.setOnClickListener {
            hCancelJob()
        }
    }

    private fun hCancelJob() {
        hJob.cancel()
    }

    /*Cancelling a parent job cancels all children jobs*/


    private fun hMain() {
        val hCurrentTime = System.currentTimeMillis()
        Timber.d("Starting Parent Job")
        hJob = CoroutineScope(Dispatchers.Main).launch {
            /*Global scope here means under jobs are now independent of parent job
            Global job are fully independent.
            * and cancelling the parent wont affect the children jobs.*/

            GlobalScope.launch { hStartWork(1) }
            GlobalScope.launch { hStartWork(2) }
            GlobalScope.launch { hStartWork(3) }
        }
        hJob.invokeOnCompletion {
            if (it != null) {
                Timber.d("Exception ${it?.message} job was cancelled after ${System.currentTimeMillis() - hCurrentTime}")
            } else {
                val hEndTime = System.currentTimeMillis()
                val hTotalTime = hEndTime - hCurrentTime

                Timber.d("Done in $hTotalTime")
            }
        }
    }

    private suspend fun hStartWork(number: Int) {
        delay(3000)
        Timber.d("Work $number being done on ${Thread.currentThread().name}")

    }
}