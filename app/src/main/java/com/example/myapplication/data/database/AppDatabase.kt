package com.example.myapplication.data.database
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.data.database.entity.AuthorizationDataEntity
import com.example.myapplication.data.database.dao.AuthorizationDataDao

@Database(entities = [AuthorizationDataEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun authorizationDataDao(): AuthorizationDataDao
}
