/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hSetupListeners()

    }

    private fun hSetupListeners() {
        hNetworkRequestB.setOnClickListener {
            /*IO-> for network and db
            ,Main->for doing stuff on main thread
            ,Default->For heavy computational work.
            */
            hLogThreadName("hSetupListeners")
            CoroutineScope(IO).launch {
                hFakeNetworkRequest()
            }
        }
    }

    private suspend fun hSetTextOnMain(hText: String) {
        withContext(Main) {
            hSetText(hText)
        }
    }

    private fun hSetText(hText: String) {
        val s = hResultTv.text.toString() + "\n$hText"
        hResultTv.text = s
    }

    private suspend fun hFakeNetworkRequest() {
        val hResult1 = hGetResultFromApi()
        Timber.d("FakeNetworkRequest $hResult1")
        hSetTextOnMain(hResult1)
    }


    private fun hSetupView() {
    }

    private suspend fun hGetResultFromApi(): String {
        hLogThreadName("hGetResultFromApi")
        delay(1000)
        return Constants.H_RESULT_1

    }

    private fun hLogThreadName(hMethodName: String) {
        Timber.d("Method $hMethodName is running on ${Thread.currentThread().name}")
    }
}