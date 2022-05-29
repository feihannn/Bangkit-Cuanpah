package com.exercise.cuanpah

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.exercise.cuanpah.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toLoginRegister.setOnClickListener {
            val i= Intent(this,LoginActivity::class.java)
            startActivity(i)
        }

        binding.buttonRegister.setOnClickListener {
            Toast.makeText(this,"Register Berhasil",Toast.LENGTH_SHORT).show()
            val i= Intent(this,LoginActivity::class.java)
            startActivity(i)
        }
    }
}