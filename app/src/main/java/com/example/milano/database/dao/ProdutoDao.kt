package com.example.milano.database.dao

import androidx.room.*
import com.example.milano.model.Produto

@Dao
interface ProdutoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inserir(vararg  produto: Produto)

    @Query("SELECT * FROM Produto ORDER BY  id DESC")
    fun all():List<Produto>

    @Query("DELETE  FROM Produto ")
    fun deletar()
}