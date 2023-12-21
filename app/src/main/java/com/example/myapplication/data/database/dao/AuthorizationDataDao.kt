package com.example.myapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.database.entity.AuthorizationDataEntity

@Dao
interface AuthorizationDataDao {
    //Inserta datos y si hay datos con la misma clave primaria lo reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAuthorizationData(authorizationDataEntity: AuthorizationDataEntity)

    @Update
    fun updateAuthorizationData(authorizationDataEntity: AuthorizationDataEntity)

    @Delete
    fun deleteAuthorizationData(authorizationDataEntity: AuthorizationDataEntity)

    @Query("SELECT * FROM transactions")
    fun getAllAuthorizationData(): List<AuthorizationDataEntity>

    // Actualiza el estado de autorización (aprobado/anulado) en la base de datos utilizando el ID de transacción.
    @Query("UPDATE transactions SET status = :newStatus WHERE id = :transactionId")
    fun updateStatus(transactionId: String, newStatus: Boolean)

    @Query("SELECT * FROM transactions WHERE status = :status")
    fun getAuthorizationDataByStatus (status: Boolean): List<AuthorizationDataEntity>
}