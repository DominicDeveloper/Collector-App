package com.asadbek.readsmscalllogcontacts

import android.annotation.SuppressLint
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asadbek.readsmscalllogcontacts.databinding.ActivityContactsBinding
import com.asadbek.readsmscalllogcontacts.models.MyContact

class ContactsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactsBinding
    lateinit var listContact:ArrayList<MyContact>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listContact = ArrayList()
        readContacts()

        var cont = ""
        listContact.forEach {
            cont += "$it\n"
        }
        binding.tvContacts.setText(cont)

    }
    @SuppressLint("Range")
    private fun readContacts(){
        if (listContact.isNotEmpty()) listContact.clear()

        val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)
        while(contacts!!.moveToNext()){
            val contactName = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)) // ismi
            val contactNumber = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)) // telefon raqami
            listContact.add(MyContact(contactName,contactNumber))
        }
        contacts.close()
    }
}