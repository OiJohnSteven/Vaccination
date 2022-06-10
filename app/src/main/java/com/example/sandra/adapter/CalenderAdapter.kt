package com.example.sandra.adapter

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.sandra.R
import com.example.sandra.Vaccine
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.*
import java.util.*

class CalenderAdapter(c:Context, v:Vaccine): RecyclerView.Adapter<CalenderAdapter.CalenderVH>() {

    val con = c
    val vaccine = v
    var list = arrayListOf<String>(vaccine.polio1, vaccine.polio2, vaccine.polio3, vaccine.measles)
    val l = hashMapOf<String, String>(Pair("Polio 1", list[0]), Pair("Polio 2", list[1]), Pair("Polio 3", list[2]), Pair("Measles", list[3]))

    class CalenderVH(view: View): RecyclerView.ViewHolder(view){
        val d = view.findViewById<CalendarView>(R.id.calendarView)
        val card = view.findViewById<CardView>(R.id.card)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalenderVH {
        val k = LayoutInflater.from(con).inflate(R.layout.calender_list, null)
        return CalenderVH(k)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalenderVH, position: Int) {
        val givenDateString = l.values.elementAt(position)
        val sdf = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy")
        val mDate = sdf.parse(givenDateString)
        val timeInMilliseconds = mDate.time

        holder.d.setDate(timeInMilliseconds)

        holder.itemView.setOnClickListener {
            var kList:Array<String> ?= null
            l.keys.forEach {
                kList = arrayOf(it)
            }
            AlertDialog.Builder(con).setTitle("${kList!![position]} Vaccination").setPositiveButton("OK", object : DialogInterface.OnClickListener{
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0!!.dismiss()
                }
            }).create().show()
        }

    }

    override fun getItemCount(): Int {
        return l.size
    }
}