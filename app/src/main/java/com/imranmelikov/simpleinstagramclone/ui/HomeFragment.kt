package com.imranmelikov.simpleinstagramclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_home
import com.imranmelikov.simpleinstagramclone.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var homefragmentarraylist:ArrayList<com.imranmelikov.simpleinstagramclone.Models.HomeFragment>
    private lateinit var homefragmentadapter:AdapterRV_home


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        auth= Firebase.auth
        db= Firebase.firestore
        getdata()
       homefragmentarraylist= ArrayList()
        homefragmentadapter= AdapterRV_home(homefragmentarraylist)
        binding.recyclerviewHome.layoutManager= GridLayoutManager(context,100)
        binding.recyclerviewHome.adapter=homefragmentadapter

        return view
    }
    fun getdata(){
    var email=auth.currentUser!!.email
    db.collection(email.toString()).addSnapshotListener { value, error ->
        if(error!=null){
            Toast.makeText(this.context,error.localizedMessage, Toast.LENGTH_SHORT).show()
        }else{
            if(value!=null){
                if(!value.isEmpty){
                    val documnets=value.documents
                    for(document1 in documnets){
                        var username1=document1.get("username")
                        db.collection("User").addSnapshotListener { value, error ->
                            if(error!=null){
                                Toast.makeText(this.context,error.localizedMessage, Toast.LENGTH_SHORT).show()
                            }else{
                                if(value!=null){
                                    if(!value.isEmpty){
                                        homefragmentarraylist.clear()
                                        val documnets=value.documents
                                        for(document2 in documnets){
                                            var image = document2.get("downloadurl")
                                            var profilename = document2.get("profilename")
                                            var profile="https://firebasestorage.googleapis.com/v0/b/simpleinstagramclone-7bb76.appspot.com/o/Images%2F2d89c7f9-7d9f-4311-be43-09f9c6e840a3.jpg?alt=media&token=efbc8a1a-10df-4733-9b0f-43bfa2736881"
                                            if(profilename!=null){
                                val homefragment=com.imranmelikov.simpleinstagramclone.Models.HomeFragment(profilename.toString(),image.toString())
                                homefragmentarraylist.add(homefragment)
                            }else{
                                val homefragment=com.imranmelikov.simpleinstagramclone.Models.HomeFragment(username1.toString(),profile)
                                homefragmentarraylist.add(homefragment)
                            }
                                        }
                                        homefragmentadapter!!.notifyDataSetChanged()
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
}