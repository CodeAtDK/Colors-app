package com.example.contactmanger.repository

import com.example.contactmanger.room.Contact
import com.example.contactmanger.room.ContactDAO

// Repository: acts a bridge between the ViewModel an Data Source
class ContactRepository (private val contactDAO: ContactDAO) {

    val contacts = contactDAO.getALLContactsInDB()

    suspend fun insert(contact: Contact):Long{
        return contactDAO.incertContact(contact)
    }
    suspend fun delete(contact: Contact){
        return contactDAO.deleteContact(contact)
    }
    suspend fun deleteAll() {
        return contactDAO.deleteAll()
    }
    suspend fun update(contact: Contact){
        return contactDAO.updateContact(contact)
    }




}