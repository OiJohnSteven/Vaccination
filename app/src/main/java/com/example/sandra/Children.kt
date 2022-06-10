package com.example.sandra

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sandra.adapter.ChildAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Children : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_children)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.visibility = View.VISIBLE

        val sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        val id = sharedpreferences!!.getString("id", "")

val child = findViewById<RecyclerView>(R.id.child)
        val list = ArrayList<Child>()
        val ref =
            FirebaseDatabase.getInstance().getReference("/child").child(id!!).addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {

                        progressBar.visibility = View.GONE

                        for (i in snapshot.children) {
                            val data = i.getValue(Child::class.java)
                            list.add(data!!)
                        }
                        list.add(Child("+", "+", "None"))

                        child.adapter = ChildAdapter(this@Children, list)
                        child.layoutManager = GridLayoutManager(this@Children, 2)

                    }

                    override fun onCancelled(error: DatabaseError) {

                    }
                })
    }
}