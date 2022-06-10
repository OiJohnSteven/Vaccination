package com.example.sandra

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        val id = sharedpreferences!!.getString("id", "")

        Handler().postDelayed(
            {
                if(id != null){
                    startActivity(Intent(this,Children::class.java))
                    finish()
                }
                else{
                    startActivity(Intent(this,LogIn::class.java))
                    finish()
                }

            },4000)
    }
}

//
//MD5: 7E:46:1D:76:A2:33:E6:08:9F:AA:9D:EA:56:43:39:FC
//SHA1: 3C:99:45:E5:23:07:CB:76:C9:03:9C:2A:CF:45:87:55:41:7A:72:02
//SHA-256: 0C:26:21:24:7B:90:D5:4F:1B:58:C3:D4:7B:18:75:33:0A:AD:8B:D1:13:2C:24:E0:C2:1B:14:C1:DD:21:83:06