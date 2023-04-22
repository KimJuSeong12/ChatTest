package com.example.chattest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chattest.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    val binding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 인증 초기화
        firebaseAuth = FirebaseAuth.getInstance()

        // 로그인 버튼 이벤트
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            login(email,password)
        }

        // 회원가입 버튼 이벤트
        binding.btnSignUP.setOnClickListener {
            val intent: Intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
    // 로그인
    private fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // 성공 시 실행
                    val intent: Intent = Intent(this@LoginActivity,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"로그인 성공",Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    // 실패 시 실행
                    Toast.makeText(this,"로그인 실패",Toast.LENGTH_SHORT).show()
                    Log.e("Login","${task.exception}")
                }
            }
    }
}