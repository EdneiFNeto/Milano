package com.example.milano.utils

import java.text.SimpleDateFormat
import java.util.*

class DataUtils {

    companion object{

        fun getDataAtualHora(): String {
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
            val date = Date()
            return dateFormat.format(date)
        }
    }
}