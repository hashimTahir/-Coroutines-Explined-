/*
 * Copyright (c) 2020/  8/ 3.  Created by Hashim Tahir
 */

package com.hashim.coroutinesexplained

import android.app.Application
import timber.log.Timber

class ApplicationClass : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(object : Timber.DebugTree() {
                override fun log(
                    priority: Int,
                    tag: String?,
                    message: String,
                    t: Throwable?
                ) {
                    super.log(priority, java.lang.String.format(Constants.hTag, tag), message, t)
                }
            })
        }
    }
}