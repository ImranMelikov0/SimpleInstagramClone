package com.imranmelikov.simpleinstagramclone.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private lateinit var binding: FragmentNotificationsBinding

//    private lateinit var db:FirebaseFirestore
//    private lateinit var postarraylist:ArrayList<Post>
//    private lateinit var postadapter:AdapterRV_notification

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val view = binding.root

//        db=Firebase.firestore
//        getdata()
//        postarraylist= ArrayList()
//        postadapter= AdapterRV_notification(postarraylist)
//        binding.recyclerviewNotifications.adapter=postadapter
//        binding.recyclerviewNotifications.layoutManager=LinearLayoutManager(activity)

        return view
    }

//    fun getdata(){
//        db.collection("Post").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->
//            if(error!=null){
//                Toast.makeText(context,error.localizedMessage,Toast.LENGTH_SHORT).show()
//            }else{
//                if(value!=null){
//                    if(!value.isEmpty){
//                        postarraylist.clear()
//                        val documents=value.documents
//                        for(document in documents){
//                            val comment=document.get("comment") as String
//                            val username=document.get("email") as String
//                            val downloadurl=document.get("downloadurl") as String
//                            val post=Post(username,comment,downloadurl)
//                            postarraylist.add(post)
//                        }
//                        postadapter!!.notifyDataSetChanged()
//                    }
//                }
//            }
//        }
//    }
}