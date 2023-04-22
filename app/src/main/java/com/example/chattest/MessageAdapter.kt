package com.example.chattest

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattest.databinding.ReceiveBinding
import com.example.chattest.databinding.SendBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class MessageAdapter(val context: Context, val messageData: ArrayList<MessageData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val recevie = 1 // 받는 타입
    val send = 2 // 보내는 타입

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if(viewType == 1) { // 받는 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.receive,parent,false)
            ReceiveViewHolder(view)
        } else { // 보내는 화면
            val view: View = LayoutInflater.from(context).inflate(R.layout.send,parent,false)
            SendViewHolder(view)
        }
    }

    override fun getItemCount(): Int {
        return messageData.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //현재 메세지
        val currentMessage = messageData[position]
        // 보내는 데이터
        if (holder.javaClass == SendViewHolder::class.java){
            val viewHolder = holder as SendViewHolder
            viewHolder.sendMessage.text = currentMessage.message
        } else { // 받는 데이터
            val viewHolder = holder as ReceiveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        // 메세지값
        val currentMessage = messageData[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.sendId)){
            send
        } else {
            recevie
        }
    }

    // 보낸 쪽
    inner class SendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sendMessage: TextView = itemView.findViewById(R.id.sendMessageText)
    }

    // 받는 쪽
    inner class ReceiveViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val receiveMessage: TextView = itemView.findViewById(R.id.receiveMessageText)
    }
}