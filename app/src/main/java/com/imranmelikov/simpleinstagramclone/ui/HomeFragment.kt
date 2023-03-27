package com.imranmelikov.simpleinstagramclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_home
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Models.Post
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
        db.collection("Posts").addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context,error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        homefragmentarraylist.clear()
                        val documents=value.documents
                        for(document in documents){
                            val downloadimage=document.get("downloadimage")
                            val username=document.get("username")
                            val profilename=document.get("profilename")
                            if(profilename!=null){
                                val homefragment=com.imranmelikov.simpleinstagramclone.Models.HomeFragment(profilename.toString(),downloadimage.toString())
                                homefragmentarraylist.add(homefragment)
                            }else{
                                val homefragment=com.imranmelikov.simpleinstagramclone.Models.HomeFragment(username.toString(),downloadimage.toString())
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