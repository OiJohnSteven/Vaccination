package com.example.sandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class LogIn : AppCompatActivity() {
    lateinit var capture:EditText
    lateinit var text:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        var button1 = findViewById<Button>(R.id.btn1)
        button1.setOnClickListener {
            capture = findViewById<EditText>(R.id.phone_id)
            text = capture.text.toString()
            var new = Intent(this,Verification::class.java)
            new.putExtra("phone_number",text)
            startActivity(new)

        }
    }


}