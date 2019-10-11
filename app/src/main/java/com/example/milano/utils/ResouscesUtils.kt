package com.example.milano.utils

import android.content.Context

class ResouscesUtils {

    companion object{
        fun get(context:Context, id: Int):String{
            return context.resources.getString(id)
        }
    }
}
