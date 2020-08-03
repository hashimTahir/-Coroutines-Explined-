/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_jobs_cancelation.*
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber

class JobsCancelationActivity : AppCompatActivity() {
    private lateinit var hCompletableJob: CompletableJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jobs_cancelation)
        hSetupView()

    }

    private fun hSetupView() {
        hStartJobB.setOnClickListener {
            /*Check for the initilization of lateInit var*/
            if (!::hCompletableJob.isInitialized) {
                hInitJob()
            }
        }
    }

    private fun hInitJob() {
        hStartJobB.text = "Start Job"
        hJobTv.text = ""
        hCompletableJob = Job()
        hCompletableJob.invokeOnCompletion {
            it?.message.let {
                var hMessage = it
                if (hMessage.isNullOrBlank()) {
                    hMessage = "Unknow cancellation error"
                }
                Timber.d("$hCompletableJob was cancelled, Reason was: $hMessage")
                hShowToastMessage(hMessage)

            }
        }
        hJobProgressBar.max = Constants.H_PROGRESS_MAX
        hJobProgressBar.progress = Constants.H_PROGRESS_START
    }

    private fun hShowToastMessage(hMessage: String) {
        GlobalScope.launch {
            Toast.makeText(this@JobsCancelationActivity, hMessage, Toast.LENGTH_SHORT).show()
        }
    }
}