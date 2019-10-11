package com.example.milano.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.milano.R
import com.example.milano.database.dao.ProdutoDao
import com.example.milano.model.Produto

@Database(
    entities = [Produto::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtoDao(): ProdutoDao

    companion object {

        fun getAppDatabase(context: Context): AppDatabase {

            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                context.resources.getString(R.string.db_name)
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}