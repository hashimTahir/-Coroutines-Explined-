/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
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
//                hFakeNetworkRequest()
                hFakeNetworkRequestWithTimeOut()
            }
        }
    }

    /*Replace lauch with time out for specifying timeouts*/
    private suspend fun hFakeNetworkRequestWithTimeOut() {
        withContext(IO) {
            val hJob = withTimeoutOrNull(Constants.H_TIME_OUT) {
                val hResult1 = hGetResultFromApi1()
                hSetTextOnMain(hResult1)

                val hResult2 = hGetResultFromApi2()
                hSetTextOnMain(hResult2)
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
        /*These are synchronous requests
        * hResult2 will be executed after the hresult1
        * */
        val hResult1 = hGetResultFromApi1()
        Timber.d("FakeNetworkRequest $hResult1")
        hSetTextOnMain(hResult1)

        val hResult2 = hGetResultFromApi2()
        Timber.d("FakeNetworkRequest $hResult2")
        hSetTextOnMain(hResult2)
    }


    private suspend fun hGetResultFromApi1(): String {
        hLogThreadName("hGetResultFromApi1")
        delay(1000)
        return Constants.H_RESULT_1

    }

    private fun hLogThreadName(hMethodName: String) {
        Timber.d("Method $hMethodName is running on ${Thread.currentThread().name}")
    }

    private suspend fun hGetResultFromApi2(): String {
        hLogThreadName("hGetResultFromApi2")
        delay(1000)
        return Constants.H_RESULT_2

    }
}