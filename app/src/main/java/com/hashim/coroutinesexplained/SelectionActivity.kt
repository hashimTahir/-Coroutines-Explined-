/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_selection.*

class SelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)
        hSetupListeners()

    }

    private fun hSetupListeners() {
        hBasicExampleB.setOnClickListener {
            startActivity(Intent(this, BasicExampleActivity::class.java))
        }
        hCancelationExampleB.setOnClickListener {
            startActivity(Intent(this, JobsCancelationActivity::class.java))

        }
        hAsynchronousExampleB.setOnClickListener {
            startActivity(Intent(this, AsynchronousActivity::class.java))
        }
        hSequentialExampleB.setOnClickListener {
            startActivity(Intent(this, SequentialActivity::class.java))
        }
        hFreezeExampleB.setOnClickListener {
            startActivity(Intent(this, FreezeUiActivity::class.java))
        }
        hRunBlockingExampleB.setOnClickListener {
            startActivity(Intent(this, RunBlockingActivity::class.java))
        }
    }
}