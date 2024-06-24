package com.aryeetey.driverroutesapp.data.localdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DriverEntity::class, RouteEntity::class], version = 1)
abstract class DriverRoutesDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao

    companion object {
        @Volatile private var instance: DriverRoutesDatabase? = null

        fun getDatabase(context: Context): DriverRoutesDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                DriverRoutesDatabase::class.java, "driver-routes-database")
                .build()
    }
}