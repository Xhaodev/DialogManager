package com.x_x.dialogmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.x_x.dialogmanger.DialogManager
import com.x_x.dialogmanger.core.DialogConfig

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dialog = AlertDialog.Builder(this)
            .setTitle("Dialog1")
            .setMessage("Message1")
            .setOnDismissListener {
                /**
                 * Will not be executed!
                 * Use "DialogConfig().setDismissListener()" instead
                 */
                Log.e("----", "dialog 1 dismiss")
            }
            .setOnDismissListener {
                /**
                 * Will not be executed!
                 * Use "DialogConfig().setOnShowListener()" instead
                 */
                Log.e("----", "dialog 1 show")
            }
            .create()


        val dialog2 = AlertDialog.Builder(this)
            .setTitle("Dialog2")
            .setMessage("Message2")
            .create()

        val dialog3 = AlertDialog.Builder(this@MainActivity)
            .setTitle("Dialog3")
            .setMessage("Message3")
            .create()


        DialogManager.put(this, dialog,
            DialogConfig()
                .setPriority(0)
                .setImmediately(false)
                .setDismissListener {
                    Log.e("----", "dialog 1 dismiss")
                }
                .setOnShowListener {
                    Log.e("----", "dialog 1 show")
                }
        )
        DialogManager.put(this, dialog2,
            DialogConfig()
                .setPriority(2)
                .setImmediately(true)
                .setDismissListener {
                    Log.e("----", "dialog 2 dismiss")
                }
                .setOnShowListener {
                    Log.e("----", "dialog 2 show")
                }
        )

        DialogManager.put(this@MainActivity, dialog3,
            DialogConfig()
                .setPriority(3)
                .setImmediately(true)
                .setDismissListener {
                    startActivity(Intent(this, MainActivity2::class.java))
                    Log.e("----", "dialog3 dismiss")
                }
                .setOnShowListener {
                    Log.e("----", "dialog3 show")
                }
        )

    }
}