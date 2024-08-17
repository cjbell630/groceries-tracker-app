package com.example.groceriestracker.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.groceriestracker.models.UpcAssociation

@Dao
interface UpcAssociationDao {
    @Query("SELECT * FROM upcAssociation")
    fun getAll(): LiveData<List<UpcAssociation>>

    @Query("SELECT * FROM upcAssociation WHERE upc LIKE :upcQuery LIMIT 1")
    fun findByUpc(upcQuery: String): UpcAssociation?

    @Insert
    suspend fun insertAll(vararg upcAssociations: UpcAssociation)

    @Delete
    fun delete(upcAssociation: UpcAssociation)

    @Update
    fun update(vararg upcAssociations: UpcAssociation)
}