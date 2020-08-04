/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_jobs_cancelation.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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
            hJobProgressBar.hStartOrCancelJob(hCompletableJob)
        }
    }

    private fun ProgressBar.hStartOrCancelJob(completableJob: CompletableJob) {
        if (this.progress > 0) {
            Timber.d("${completableJob} is already activie.")
            hResetJob()
        } else {
            hStartJobB.text = "Cancel Job"
            /*Launches coroutines with hcompleteable job, can be added more all
            are indenpendent of each other*/
            CoroutineScope(IO + completableJob).launch {
                Timber.d("$this coroutine is activated with ${completableJob}")

                for (i in Constants.H_PROGRESS_START..Constants.H_PROGRESS_MAX) {
                    delay((Constants.H_JOB_TIME / Constants.H_PROGRESS_MAX).toLong())
                    this@hStartOrCancelJob.progress = i
                }
                hSetText("${completableJob} is complete")
            }
            /*if socpe . cancel used all will be cancelled to cancel jst one job
            * use job .cancel*/
        }

    }

    private fun hSetText(hText: String) {
        GlobalScope.launch(Main) {
            hJobTv.text = hText
        }
    }

    private fun hResetJob() {
        if (hCompletableJob.isActive || hCompletableJob.isCompleted) {
            hCompletableJob.cancel(CancellationException("Reseting job"))
        }
        hInitJob()

    }

    private fun hInitJob() {
        hStartJobB.text = "Start Job"
        hSetText("")
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
        GlobalScope.launch(Main) {
            Toast.makeText(this@JobsCancelationActivity, hMessage, Toast.LENGTH_SHORT).show()
        }
    }
}