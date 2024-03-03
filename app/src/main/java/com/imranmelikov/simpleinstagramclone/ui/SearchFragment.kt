package com.imranmelikov.simpleinstagramclone.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Activities.SearchActivity
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_notification
import com.imranmelikov.simpleinstagramclone.Adapters.AdapterRV_search
import com.imranmelikov.simpleinstagramclone.Models.Image
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.databinding.FragmentNotificationsBinding
import com.imranmelikov.simpleinstagramclone.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var imagearraylist:ArrayList<Image>
    private lateinit var imageadapter:AdapterRV_search

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentSearchBinding.inflate(inflater,container,false)
        val view=binding.root
        binding.searchBtn.setOnClickListener {
            var intent= Intent(this.context, SearchActivity::class.java)
            startActivity(intent)
        }
        auth= Firebase.auth
        db= Firebase.firestore
        getdata()
        imagearraylist= ArrayList()
        imageadapter= AdapterRV_search(imagearraylist)
        binding.recyclerviewSearch.layoutManager= GridLayoutManager(context,3)
        binding.recyclerviewSearch.adapter=imageadapter
        return view
    }

    fun getdata(){
        db.collection("Posts").addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(context,error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        imagearraylist.clear()
                        val documents=value.documents
                        for(document in documents){
                            val downloadimage=document.get("downloadimage")
                                val image=Image(downloadimage.toString())
                                imagearraylist.add(image)
                        }
                        imageadapter!!.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}