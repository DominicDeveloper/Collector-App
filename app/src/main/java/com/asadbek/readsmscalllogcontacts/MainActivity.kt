package com.asadbek.readsmscalllogcontacts

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.asadbek.readsmscalllogcontacts.databinding.ActivityMainBinding

/**
 * Owner: Telegram: @Dominic_Azimov
 * Date: 17.08.2024
 * Warning: This app is just learning
 * Yaxshi niyatda foydalaning va qiladigan ishlaringizga men javob bermayman
 * Bu shunchaki ta`lim uchun
 */

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkPermission()

        binding.btnSms.setOnClickListener {
            val intent = Intent(this,SmsActivity::class.java)
            startActivity(intent)
        }
        binding.btnContacts.setOnClickListener {
            val intent = Intent(this,ContactsActivity::class.java)
            startActivity(intent)
        }
        binding.btnCallLog.setOnClickListener {
            val intent = Intent(this,CallLogActivity::class.java)
            startActivity(intent)
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkPermission(){
        val readSms = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_SMS)
        val readContacts = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)
        val readCallLogs = ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)

        if (readSms == PackageManager.PERMISSION_GRANTED && readContacts == PackageManager.PERMISSION_GRANTED && readCallLogs == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(this, "Dasturdan foydalanishingiz mumkin!", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_SMS,android.Manifest.permission.READ_CONTACTS,android.Manifest.permission.READ_CALL_LOG),2)
            Toast.makeText(this, "Dastur to`g`ri ishlashi uchun permissionlarga ruxsat bering!", Toast.LENGTH_SHORT).show()
        }
    }

}