package com.example.groceriestracker.repository

import androidx.lifecycle.LiveData
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.database.UpcAssociationDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpcAssociationRepository(private val upcAssociationDao: UpcAssociationDao) {
    val allUpcs: LiveData<List<UpcAssociation>> = upcAssociationDao.getAll()

    suspend fun update(upcAssociation: UpcAssociation) {
        withContext(Dispatchers.IO) {
            upcAssociationDao.update(upcAssociation)
        }
    }

    suspend fun delete(upcAssociation: UpcAssociation) {
        withContext(Dispatchers.IO) {
            upcAssociationDao.delete(upcAssociation)
        }
    }

    suspend fun insert(upcAssociation: UpcAssociation) {
        withContext(Dispatchers.IO) {
            upcAssociationDao.insertAll(upcAssociation)
        }
    }

    fun getUpcAssociation(upc: String): UpcAssociation? {
        val allUpcsList = allUpcs.value // NOTE seems to always return nul
        return allUpcsList?.find{
            upcAssociation -> upcAssociation.upc == upc
        }
    }
}