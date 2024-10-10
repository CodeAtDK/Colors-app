package com.example.contactmanger


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.contactmanger.repository.ContactRepository
import com.example.contactmanger.room.Contact
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import java.time.LocalDateTime

// View Model: store and mange UI-related Data
// separating the UI

class ContactViewModel(private val repository: ContactRepository) : ViewModel(),Observable{

    val db = Firebase.firestore

    var add:Int = 0
    val contacts = repository.contacts
    private var isUpdateOrDelere = false
    private lateinit var contactToUpdateOrDelete: Contact

    // Data Binding with Live Data

    @Bindable
    val inputName = MutableLiveData<String?>()
    @Bindable
    val inputEmail = MutableLiveData<String?>()
    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()
    @Bindable
    val clealAllOrDeleteButtonText = MutableLiveData<String>()
    @Bindable
    val Synctext = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clealAllOrDeleteButtonText.value = "Clear All"
        Synctext.value = add.toString() + " Sync"
    }

    fun insert(contact : Contact) = viewModelScope.launch {

        repository.insert(contact)
    }

    fun delete(contact: Contact) = viewModelScope.launch {

        // Resetting the button and fields
        repository.delete(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelere = false
        saveOrUpdateButtonText.value = "Save"
        clealAllOrDeleteButtonText.value = "Clear All"

    }

    fun update(contact: Contact) = viewModelScope.launch {

        // Resetting the button and fields
        repository.delete(contact)
        inputName.value = null
        inputEmail.value = null
        isUpdateOrDelere = false
        saveOrUpdateButtonText.value = "Save"
        clealAllOrDeleteButtonText.value = "Clear All"
    }
    fun clearALl() = viewModelScope.launch {

        repository.deleteAll()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveOrUpdate(){

        if(isUpdateOrDelere){
            // make an update
            contactToUpdateOrDelete.contact_name = inputName.value!!
            contactToUpdateOrDelete.contact_email = "Updated By\n"  +LocalDateTime.now().toString()
            add(inputName.value!!,"Updated By\n"  +LocalDateTime.now().toString())
            update(contactToUpdateOrDelete)

        }
        else{
            // Inserting a new contact
            val name = inputName.value!!
            val email ="Created By\n"  +LocalDateTime.now().toString()
            add(name,email)
            insert(Contact(0,name,email))


            // Resetting the name and email
            inputName.value = null
            inputEmail.value = null

        }
        add = add + 1;
        val data = hashMapOf(
            add to 1
        )

                    Synctext.value = add.toString()+" Sync"
    }

    fun clearAllorDelete(){
        if(isUpdateOrDelere){
            delete(contactToUpdateOrDelete)
        }

        else{
            clearALl()
            add = 0;
            Synctext.value = add.toString()+" Sync"
        }

    }

    fun initUpdateAndDelete(contact: Contact){
        inputName.value = contact.contact_name
        inputEmail.value = contact.contact_email
        isUpdateOrDelere = true
        contactToUpdateOrDelete = contact
        saveOrUpdateButtonText.value = "Update"
        clealAllOrDeleteButtonText.value = "Delete"
    }

    fun Sync(){

        add = 0;
        Synctext.value = add.toString() + " Sync"



    }

    fun add(colour:String, DateTime:String){

        val city = Contact(
            3,
            "$colour",
            "$DateTime"
        )
        db.collection("Data").document("$colour").set(city)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {


    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}