package com.x_x.dialogmanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.x_x.dialogmanger.DialogManager
import com.x_x.dialogmanger.core.DialogConfig

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val dialog3 = AlertDialog.Builder(this@MainActivity2)
            .setTitle("Dialog")
            .setMessage("Message")
            .create()


        DialogManager.put(this, dialog3,
            DialogConfig()
                .setPriority(0)
                .setImmediately(false)
                .setDismissListener {
                    Log.e("----", "MainActivity2 dialog dismiss")
                }
                .setOnShowListener {
                    Log.e("----", "MainActivity2 dialog show")
                }
        )
    }
}