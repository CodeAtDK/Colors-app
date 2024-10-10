package com.example.contactmanger

import ViewModelFactory
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactmanger.databinding.ActivityMainBinding
import com.example.contactmanger.repository.ContactRepository
import com.example.contactmanger.room.ConcatDatabase
import com.example.contactmanger.room.Contact
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class MainActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityMainBinding
    private lateinit var contactViewModel: ContactViewModel
    val db  = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // ROOM Database

        val dao = ConcatDatabase.getInstance(applicationContext).contactDAO

        val repository = ContactRepository(dao)
        val factory = ViewModelFactory(repository)

        // View Model
        contactViewModel = ViewModelProvider(this,factory)
            .get(ContactViewModel::class.java)

        binding.contactViewModel = contactViewModel

        // use this LiveData and Data Binding integration

        binding.lifecycleOwner = this
        initRecyclerView()





    }

    private fun initRecyclerView() {

        binding.recyclerView.layoutManager = GridLayoutManager(this,2)
        DisplayUserList()

    }


    private fun DisplayUserList() {


        contactViewModel.contacts.observe(this, Observer {
            binding.recyclerView.adapter = RecyclerViewAdapter(
                it, {selectedItem: Contact -> listItemClicked(selectedItem)}
            )
        })

    }

    private fun listItemClicked(selectedItem: Contact) {

        Toast.makeText(this, "Selected name is ${selectedItem.contact_name}",
            Toast.LENGTH_LONG).show()

        contactViewModel.initUpdateAndDelete(selectedItem)

    }
}