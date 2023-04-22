package com.example.chattest

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chattest.databinding.UserLayoutBinding

class UserAdapter(val context: Context, val userData: ArrayList<UserData>) :
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    // 화면 설정
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userData.size
    }

    // 데이터 설정정
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val binding = holder.binding
        // 데이터 담기
        val currentUser = userData[position]
        // 화면에 데이터 보여주기
        holder.nameText.text = currentUser.name
        // 아이템 클릭 이벤트
        binding.root.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            // 넘길 데이터
            intent.putExtra("name", currentUser.name)
            intent.putExtra("uId", currentUser.uId)
            context.startActivity(intent)
        }

    }

    class UserViewHolder(val binding: UserLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val nameText: TextView = itemView.findViewById(R.id.tvName)
    }
}