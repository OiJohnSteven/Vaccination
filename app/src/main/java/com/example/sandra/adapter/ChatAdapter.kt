package com.example.sandra.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sandra.Chat
import com.example.sandra.R

class ChatAdapter(context: Context, options: ArrayList<Chat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){
    var c = context
    var y = options

    var type = 1
    var received = 2

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        var inflate: View ?= null

        if (viewType == type){
            return MessageOutVH(LayoutInflater.from(c).inflate(R.layout.to_message_layout, parent, false))
        }
       return MessageInVH(LayoutInflater.from(c).inflate(R.layout.from_message_layout, parent, false))
    }

   inner class MessageInVH( itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(position: Int){
            val n = itemView.findViewById<TextView>(R.id.message)
            n.text = y[position].message

            val d = itemView.findViewById<TextView>(R.id.time)
            d.text = y[position].time

        }
    }

   inner class MessageOutVH( itemView: View): RecyclerView.ViewHolder(itemView){
        fun onBind(position: Int){

            //val c = ChatAdapter().y!!.get(position)
            val n = itemView.findViewById<TextView>(R.id.to_message)
            n.text = y[position].message

            val d = itemView.findViewById<TextView>(R.id.to_time)
            d.text = y[position].time

        }
    }

    class ChatViewHolder( itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(context: Context, message:String, time:String, position: Int){

            val n = itemView.findViewById<TextView>(R.id.message)
            n.text = message

            val d = itemView.findViewById<TextView>(R.id.time)
            d.text = time

        }

    }

    override fun getItemCount(): Int {
        return y.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val list = y.get(position)
        if (list.status == "Sent"){
            val h: MessageOutVH?= null
            Log.d("h", h.toString())
            Log.d("message", list.message!!)
            Log.d("time", list.time!!)

             (holder as MessageOutVH).onBind(position)
        }
        else if (list.status == "Received"){
            val h: MessageInVH?= null
            Log.d("message", list.message!!)
            Log.d("time", list.time!!)
            (holder as MessageInVH).onBind(position)

        }
    }

    override fun getItemViewType(position: Int): Int {
        val z = y[position].status
        var viewType: Int = 0
        if (z == "Received"){
            viewType = 0
        }
        else{
            viewType = 1
        }
        return viewType
    }
}