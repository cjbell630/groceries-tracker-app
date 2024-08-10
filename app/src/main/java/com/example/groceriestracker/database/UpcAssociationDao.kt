package com.example.groceriestracker.database

import androidx.lifecycle.LiveData
import androidx.room.*

interface UpcAssociationDao {
    @Query("SELECT * FROM upcAssociation")
    fun getAll(): LiveData<List<UpcAssociation>>

    @Query("SELECT * FROM upcassociation WHERE upc IN (:upcs)")
    fun loadAllByUpcs(upcs: LongArray): List<UpcAssociation>

    @Insert
    suspend fun insertAll(vararg upcAssociations: UpcAssociation)

    @Delete
    fun delete(upcAssociation: UpcAssociation)

    @Update
    fun update(vararg upcAssociations: UpcAssociation)
}