/*
 * Copyright (c) 2020/  8/ 4.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_asynchronous.*
import kotlinx.android.synthetic.main.activity_sequential.*
import kotlinx.android.synthetic.main.activity_sequential.hClickMeB
import kotlinx.android.synthetic.main.activity_sequential.hDetialTv
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SequentialActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sequential)
        hSetupView()

    }

    private fun hSetupView() {
        hClickMeB.setOnClickListener {

        }
    }



    private fun hSetTextOnMain(hText: String) {
        CoroutineScope(Dispatchers.Main).launch {
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