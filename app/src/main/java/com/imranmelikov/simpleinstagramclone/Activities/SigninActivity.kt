package com.imranmelikov.simpleinstagramclone.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySigninBinding

class SigninActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySigninBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySigninBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth=Firebase.auth
        if(auth.currentUser!=null){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.createaccount.setOnClickListener {
            var intent=Intent(this,SignupActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun Loginclick(view: View){
        var email=binding.editTextTextEmailAddress.text.toString()
        var password=binding.editTextTextPassword.text.toString()
        if(email.equals("")||password.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_SHORT).show()
        }else{
        auth.signInWithEmailAndPassword(email, password).addOnFailureListener {
            Toast.makeText(this,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }.addOnSuccessListener {
            var intent=Intent(this@SigninActivity,MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        }
    }
}