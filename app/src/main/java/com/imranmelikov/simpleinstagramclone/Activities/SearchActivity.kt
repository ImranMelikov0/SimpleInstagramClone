package com.imranmelikov.simpleinstagramclone.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_search_activity
import com.imranmelikov.simpleinstagramclone.Models.User
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySearchBinding
import java.util.*
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private lateinit var userarraylist:ArrayList<User>
    private lateinit var searchadapter:AdapterRV_search_activity
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySearchBinding.inflate(layoutInflater)
        val view=binding.root
        setContentView(view)
        binding.searchBtn.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterlist(newText)
                return true
            }

        })
        auth= Firebase.auth
        db= Firebase.firestore
        getdata()

        userarraylist= ArrayList()
        searchadapter= AdapterRV_search_activity(userarraylist)
        binding.recyclerviewSearchActivity.layoutManager=LinearLayoutManager(this)
        binding.recyclerviewSearchActivity.adapter=searchadapter

    }
    fun getdata(){
        var email=auth.currentUser!!.email
        db.collection(email.toString()).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(this,error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documnets=value.documents
                        for(document1 in documnets){
                            var username1=document1.get("username")
                            db.collection("User").addSnapshotListener { value, error ->
                                if(error!=null){
                                    Toast.makeText(this,error.localizedMessage, Toast.LENGTH_SHORT).show()
                                }else{
                                    if(value!=null){
                                        if(!value.isEmpty){
                                            userarraylist.clear()
                                            val documnets=value.documents
                                            for(document2 in documnets){
                                                var image = document2.get("downloadurl")
                                                var profilename = document2.get("profilename")
                                                var profile="https://firebasestorage.googleapis.com/v0/b/simpleinstagramclone-7bb76.appspot.com/o/Images%2F2d89c7f9-7d9f-4311-be43-09f9c6e840a3.jpg?alt=media&token=efbc8a1a-10df-4733-9b0f-43bfa2736881"
                                                if(profilename!=null){
                                                    val user=User(profilename.toString(),image.toString())
                                                    userarraylist.add(user)
                                                }else{
                                                    val user=User(username1.toString(),profile.toString())
                                                    userarraylist.add(user)
                                                }
                                            }
                                            searchadapter!!.notifyDataSetChanged()
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun filterlist(text: String?) {
        var filteredlist:ArrayList<User>
        filteredlist= ArrayList()
        for(item in userarraylist){
            if(item.username!!.toLowerCase().contains(text!!.toLowerCase()))
                filteredlist.add(item)
            if(filteredlist.isEmpty()){
            }else{
                searchadapter.filterList(filteredlist)
            }

        }
    }
}