package com.asadbek.readsmscalllogcontacts

import android.annotation.SuppressLint
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.CallLog
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.asadbek.readsmscalllogcontacts.databinding.ActivityCallLogBinding
import com.asadbek.readsmscalllogcontacts.models.MyLogs
import java.text.SimpleDateFormat
import java.util.Locale

class CallLogActivity : AppCompatActivity() {
    lateinit var binding: ActivityCallLogBinding
    lateinit var listLogs:ArrayList<MyLogs>
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCallLogBinding.inflate(layoutInflater)
        setContentView(binding.root)
        listLogs  = ArrayList()
        readCallLogs()

        var logs = ""
        listLogs.forEach {
            logs += "$it\n"
        }
        binding.tvCallLog.setText(logs)
    }
    @SuppressLint("Range")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun readCallLogs(){
        if (listLogs.isNotEmpty()) listLogs.clear()

        val uriCallLogs: Uri = Uri.parse("content://call_log/calls")
        val cursorCallLogs: Cursor = contentResolver.query(uriCallLogs,null,null,null)!!

        while (cursorCallLogs.moveToNext()){
            val number = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.NUMBER)) // telefon raqami
            val name = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.CACHED_NAME)) // ismi
            val duration = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.DURATION)) // gaplashilgan vaqti
            val type = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.TYPE)) // kiruvchi yoki chiquvchi qabul qilinmagan qo`ng`iroq turlari
            val date = cursorCallLogs.getString(cursorCallLogs.getColumnIndex(CallLog.Calls.DATE)) // vaqti
            val dataTime = SimpleDateFormat("hh:mm:dd:MM:yyyy", Locale.US).format(date.toLong()).toString()
            listLogs.add(MyLogs(number,name,duration,type,dataTime))
        }
        cursorCallLogs.close()

        /*
               type 5 - rad etilgan qo`ng`iroq
               type 4 -
               type 3 - qabul qilinmagan qo`ng`iroq
               type 2 - chiquvchi qo`ng`iroq
               type 1 - kiruvchi qo`ng`iroq
         */

        // duration - sekundda keladi uni 60 ga bo`lish kerak keyin daqiqani olish mumkin
    }
}