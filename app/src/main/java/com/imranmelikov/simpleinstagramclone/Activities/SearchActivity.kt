package com.imranmelikov.simpleinstagramclone.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySearchBinding
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySigninBinding
import com.imranmelikov.simpleinstagramclone.databinding.FragmentNotificationsBinding

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
    }
}