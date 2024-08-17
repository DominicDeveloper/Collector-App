package com.asadbek.readsmscalllogcontacts

import android.database.Cursor
import android.os.Bundle
import android.provider.Telephony
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asadbek.readsmscalllogcontacts.databinding.ActivitySmsBinding
import com.asadbek.readsmscalllogcontacts.models.Messages
import java.text.SimpleDateFormat
import java.util.Locale

class SmsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySmsBinding
    lateinit var listSms:ArrayList<Messages>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySmsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listSms = ArrayList()
        var sms = ""
        readSms()
        listSms.forEach {
            sms += "$it\n"
        }
        binding.tvSms.setText(sms)
    }
    private fun readSms(){
        if (listSms.isNotEmpty()) listSms.clear()

        val contentResolver = contentResolver
        val cursor: Cursor = contentResolver.query(Telephony.Sms.CONTENT_URI,null,null,null,null)!!
        while (cursor.moveToNext()){
            val number = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)) // telefon raqam
            val message = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.BODY)) // sms habari
            val read = cursor.getString(cursor.getColumnIndexOrThrow("read")) // o`qilganligi haqida
            val _time = cursor.getString(cursor.getColumnIndexOrThrow(Telephony.Sms.DATE)) // vaqti
            val dataTime = SimpleDateFormat("hh:mm:dd:MM:yyyy", Locale.US).format(_time.toLong()).toString() // oddiy vaqtga o`tkazish
            var type = "inbox"
            if (cursor.getString(cursor.getColumnIndexOrThrow("type")).contains("1")){
                type = "inbox" // kelgan habar
            }else{
                type = "sent" // yuborilgan habar
            }
            listSms.add(Messages(number,message,read,dataTime,type))
        }
        cursor.close()
    }
}