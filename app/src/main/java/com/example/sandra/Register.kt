package com.example.sandra

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.database.FirebaseDatabase
import java.util.*
//import java.util.*

class Register : AppCompatActivity() {

    lateinit var name:EditText
    lateinit var date: TextView
    lateinit var text1:String
    lateinit var text2:String


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        name = findViewById<EditText>(R.id.nameId)
       

        date = findViewById<TextView>(R.id.dateId)
       //text2 = date.text.toString()

        date.setOnClickListener {
            val x = MaterialDatePicker.Builder.datePicker()
            val y = x.setTitleText("Pick Polio Vaccination Date")
            val z = x.build()
                val s = z.show(supportFragmentManager, "ANYTHING")
            z.addOnPositiveButtonClickListener {
                date.setText(z.headerText)

                text2 = z.headerText.toString()
////                val s = LocalDate.parse("29-04-2022" , DateTimeFormatter.ofPattern("dd-MM-yyyy"))
////                val k = s.atStartOfDay(ZoneId.systemDefault()).toInstant().epochSecond
//
//                var dt = Date()
//                val c = Calendar.getInstance(Locale.ENGLISH)
//                c.time = Date(z.headerText)
//                c.add(Calendar.DATE, 1)
//                //dt = c.getTime()



            }


        }

        val button2 = findViewById<Button>(R.id.btnNext)
        button2.setOnClickListener {
text1 = name.text.toString()
            saveChild()
        }

    }

    private fun saveChild (){

        val sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        val id = sharedpreferences.getString("id","")

        val ref = FirebaseDatabase.getInstance().getReference("/child").child(id!!)

        var childId = ref.push().key
        val child = Child(childId.toString(),text1,text2)

        ref.child(childId.toString()).setValue(child).addOnCompleteListener(){
           val intent = Intent(this, Children::class.java)
            intent.putExtra("parentId", id)
            startActivity(intent)
        }

        val polio1 = getPolio1(text2)
        val polio2 = getPolio2(text2)
        val polio3 = getPolio3(text2)
        val measles = measlesVaccine(text2)

        val vacRef = FirebaseDatabase.getInstance().getReference("/vaccine").child(childId!!)
       // childId  = ref.push().key
        val Vaccine = Vaccine(childId,polio1,polio2,polio3,measles)
        vacRef.push().setValue(Vaccine).addOnCompleteListener(){
            Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_LONG).show()
            val intent = Intent(this@Register, Children::class.java)
            intent.putExtra("parentId", childId.toString())
            startActivity(intent)
        }


    }

    private fun getPolio1 (p1:String): String{
        var dt = Date()

        val c = java.util.Calendar.getInstance()
        c.time = Date(p1)
        c.add(Calendar.DATE, 42)
        dt = c.time
        return dt.toString()
    }

    private fun getPolio2 (p2:String):String{
        var dt = Date()
        val c = Calendar.getInstance(Locale.ENGLISH)
        c.time = Date(p2)
        c.add(Calendar.DATE, 70)
        dt = c.time
        return dt.toString()
    }

    private fun getPolio3 (p3:String):String{
        var dt = Date()
        val c = Calendar.getInstance(Locale.ENGLISH)
        c.time = Date(p3)
        c.add(Calendar.DATE, 98)
        dt = c.time
        return dt.toString()
    }

    private fun measlesVaccine(m1:String):String{
        var dt = Date()
        val c = Calendar.getInstance(Locale.ENGLISH)
        c.time = Date(m1)
        c.add(Calendar.DATE, 252)
        dt = c.time
        return dt.toString()
    }

}

