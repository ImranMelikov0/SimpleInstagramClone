package com.imranmelikov.simpleinstagramclone.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    private lateinit var auth:FirebaseAuth
    private lateinit var storage:FirebaseStorage
    private lateinit var db:FirebaseFirestore
    private lateinit var binding:ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySignupBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        binding.alreadyaccount.setOnClickListener {
            var intent= Intent(this,SigninActivity::class.java)
            startActivity(intent)
            finish()
        }

        auth= Firebase.auth
        storage=Firebase.storage
        db=Firebase.firestore

        if(auth.currentUser!=null){
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }



    }
    fun Signupclick(view: View){
        var email=binding.editTextTextEmailAddress.text.toString()
        var password=binding.editTextTextPassword.text.toString()
        var username=binding.editTextTextPersonName2.text.toString()
        if(email.equals("")||password.equals("")||username.equals("")){
            Toast.makeText(this,"Write username,email and password",Toast.LENGTH_SHORT).show()
        }else {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                saveinfo(email,password,username)
                var intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this@SignupActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveinfo(email: String, password: String, username: String) {
        var currentuserid=auth.currentUser!!.uid
        storage.reference.child("User")

        var usermap=HashMap<String,Any>()
        usermap["uid"]=currentuserid
        usermap["email"]=email
        usermap["password"]=password
        usermap["username"]=username
        usermap["bio"]="I am using SimpleInstagramClone"
        usermap["profile"]= R.drawable.profile
        db.collection(currentuserid).add(usermap).addOnSuccessListener {
            finish()
        }.addOnFailureListener {
            Toast.makeText(this@SignupActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
        }
    }
}



