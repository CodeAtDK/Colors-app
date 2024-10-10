package com.example.contactmanger.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
@Dao
interface ContactDAO {

    @Insert
    suspend fun incertContact(contact: Contact):Long

    @Update
    suspend fun updateContact(contact: Contact)

    @Delete
    suspend fun deleteContact(contact: Contact)

    @Query("Delete From contacts_table")
    suspend fun deleteAll()

    @Query("SELECT *FROM contacts_table")
    fun getALLContactsInDB():LiveData<List<Contact>>



}