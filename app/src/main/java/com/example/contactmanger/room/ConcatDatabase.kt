package com.example.contactmanger.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ConcatDatabase : RoomDatabase() {

    abstract val contactDAO: ContactDAO

    // Singleton Design Pattern
    // Only one instance of the database exists avoiding
    // unnecessary overhead associated with repeated database creation

    // companion object: define a static singleton instance of this DB class

    companion object{
        @Volatile
        private var INSTANCE : ConcatDatabase? = null

        fun getInstance(context: Context): ConcatDatabase {

            synchronized(this){

                var instance = INSTANCE
                if(instance == null){

                    // create the databaseobject
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ConcatDatabase::class.java,
                        "contacts_db"
                    ).build()
                }
                INSTANCE = instance
                return instance
            }

        }

    }

}