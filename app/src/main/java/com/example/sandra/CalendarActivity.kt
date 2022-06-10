package com.example.sandra

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandra.adapter.CalenderAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2)

        progressBar2.visibility = View.VISIBLE

        val childId = intent.getStringExtra("childId")

        val chat = findViewById<ImageView>(R.id.chat)
        chat.setOnClickListener{
            startActivity(Intent(this, ChatActivity::class.java))
        }

        val calender = findViewById<RecyclerView>(R.id.calender)
        FirebaseDatabase.getInstance().getReference("/vaccine").child(childId!!).addValueEventListener(
            object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    progressBar2.visibility = View.GONE
                    for (i in snapshot.children){
                        val k = i.getValue(Vaccine::class.java)
                        val a = CalenderAdapter(this@CalendarActivity, k!!)
                        calender.adapter = a
                        calender.layoutManager  = LinearLayoutManager(this@CalendarActivity)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            }
        )
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.settings, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.log_child -> {
                startActivity(Intent(this, Children::class.java))
                finish()
            }
            R.id.log_out -> {
                startActivity(Intent(this, LogIn::class.java))
                finish()

            }
        }
        return super.onOptionsItemSelected(item)
    }
}