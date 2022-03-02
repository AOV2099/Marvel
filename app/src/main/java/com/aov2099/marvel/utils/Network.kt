package com.aov2099.marvel.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity

class Network {
    companion object{
        @RequiresApi(Build.VERSION_CODES.M)
        fun conExistsFrag(act: FragmentActivity): Boolean {
            val connectivityManager = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetwork != null
        }

        @RequiresApi(Build.VERSION_CODES.M)
        fun conExistsAct(act: Activity): Boolean {
            val connectivityManager = act.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return connectivityManager.activeNetwork != null
        }
    }
}