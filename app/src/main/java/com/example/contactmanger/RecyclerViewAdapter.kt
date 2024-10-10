package com.example.contactmanger

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.contactmanger.R.layout
import com.example.contactmanger.databinding.CardItemBinding
import com.example.contactmanger.room.Contact

class RecyclerViewAdapter(private val contactList: List<Contact>,
    private val clickListener: (Contact) -> Unit): RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


        class MyViewHolder(val binding: CardItemBinding): RecyclerView.ViewHolder(binding.root){

            fun bind(contact: Contact, clickListener: (Contact) -> Unit){

                binding.nameTextview.text = contact.contact_name
                binding.emailTextview.text = contact.contact_email

                val color = Color.parseColor("#"+ contact.contact_name)
                binding.listItemLayout.setBackgroundColor(color)
                binding.listItemLayout.setOnClickListener{
                    clickListener(contact)
                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: CardItemBinding = DataBindingUtil.inflate(layoutInflater,R.layout.card_item,parent,false)
        return MyViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.bind(contactList[position],clickListener)
    }

}