package com.example.chattest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chattest.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatActivity : AppCompatActivity() {
    val binding by lazy { ActivityChatBinding.inflate(layoutInflater) }
    lateinit var receiverName: String
    lateinit var receiverUid: String
    lateinit var firebaseAuth: FirebaseAuth // 인증 객체
    lateinit var databaseReference: DatabaseReference // DB 객체

    lateinit var receiverRoom : String // 받는 대화방
    lateinit var senderRoom : String // 보낸 대화방
    lateinit var messageData: ArrayList<MessageData>

    lateinit var messageAdapter : MessageAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        // 초기화
        messageData = ArrayList()

        messageAdapter = MessageAdapter(this,messageData)
        // RecyclerView
        binding.ChatRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.ChatRecyclerView.adapter = messageAdapter

            //넘어온 데이터 변수에 담기
        receiverName = intent.getStringExtra("name").toString()
        receiverUid = intent.getStringExtra("uId").toString()
        firebaseAuth = FirebaseAuth.getInstance()
        databaseReference = FirebaseDatabase.getInstance().reference

        //  접속자 Uid
        val senderUid = firebaseAuth.currentUser?.uid
        // 보낸이방
        senderRoom = receiverUid + senderUid
        // 받는이방
        receiverRoom = senderUid + receiverUid
        // 액션바에 상대방 이름 보여주기
        supportActionBar?.title = receiverName
        // 메세지 전송 버튼 이벤트
        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            val messageObject = MessageData(message,senderUid)
            // 데이터 저장
            databaseReference.child("chats").child(senderRoom).child("messages").push()
                .setValue(messageObject).addOnSuccessListener {
                    // 저장 성공하면
                    databaseReference.child("chats").child(receiverRoom).child("messages").push()
                        .setValue(messageObject)
                }
            //입력값 초기화
            binding.etMessage.setText("")
        }
        // 메세지 가져오기
        databaseReference.child("chats").child(senderRoom).child("messages")
            .addValueEventListener(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageData.clear()

                    for (postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(MessageData::class.java)
                        messageData.add(message!!)
                    }
                    // 적용
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
    }

}