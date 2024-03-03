package com.imranmelikov.simpleinstagramclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    private lateinit var postarraylist:ArrayList<Post>
    private lateinit var postadapter:AdapterRV_notification

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

        auth=Firebase.auth
        db=Firebase.firestore
        getdata()
        postarraylist= ArrayList()
        postadapter= AdapterRV_notification(postarraylist)
        binding.recyclerviewNotifications.layoutManager=LinearLayoutManager(context)
        binding.recyclerviewNotifications.adapter=postadapter

        return view
    }

    fun getdata(){
        db.collection("Posts").addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context,error.localizedMessage,Toast.LENGTH_SHORT).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        postarraylist.clear()
                        val documents=value.documents
                        for(document in documents){
                            val comment=document.get("comment")
                            val downloadimage=document.get("downloadimage")
                            val username=document.get("username")
                            val profilename=document.get("profilename")
                            if(profilename!=null){
                                val post=Post(profilename.toString(),comment.toString(),downloadimage.toString())
                                postarraylist.add(post)
                            }else{
                                val post=Post(username.toString(),comment.toString(),downloadimage.toString())
                                postarraylist.add(post)
                            }

                        }
                        postadapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}