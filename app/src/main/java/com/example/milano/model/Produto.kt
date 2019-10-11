package com.example.milano.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class Produto(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val nome:String):Serializable{}