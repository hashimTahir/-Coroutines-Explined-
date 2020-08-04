/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_freeze_ui.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class FreezeUiActivity : AppCompatActivity() {
    /*Coroutines run on threads they are not threads.*/
    var hCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freeze_ui)

        hMain()

        hClickMeB.setOnClickListener {
            hDetialTv.text = (hCount++).toString()
        }
    }

    private suspend fun hFakeNetworkRequest() {
        Timber.d("Starting Network request")
        delay(3000)
        Timber.d("Finished Network request")

    }

    private fun hMain() {

        CoroutineScope(Main).launch {
            /*Delay only delays the this current job running on ui thread
            * whereas thread.sleep will freeze the ui thread*/
            Timber.d("Current thread ${Thread.currentThread().name}")
//            delay(3000)
//            Thread.sleep(3000)

            for (i in 1..5) {
                launch {
                    hFakeNetworkRequest()
                }

            }
        }
    }
}