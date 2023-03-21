package com.imranmelikov.simpleinstagramclone.Activities

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Remove
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivityProfilBinding

class ProfilActivity : AppCompatActivity() {
    private lateinit var binding:ActivityProfilBinding
    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityProfilBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)

        auth=Firebase.auth

        binding.closeProfileBtn.setOnClickListener {
            var intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

        logoutbtn(view)
        removebtn(view)
    }
    private fun logoutbtn(view: View){
        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            var intent=Intent(this@ProfilActivity,SigninActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    private fun removebtn(view:View){
        binding.deleteAccountBtn.setOnClickListener {
                val user = Firebase.auth.currentUser!!
                user.delete()
            auth.signOut()
            var intent=Intent(this@ProfilActivity,SigninActivity::class.java)
            startActivity(intent)
                finish()
            }
    }
}