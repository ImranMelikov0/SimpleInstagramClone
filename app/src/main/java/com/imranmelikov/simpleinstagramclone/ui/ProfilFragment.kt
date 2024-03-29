package com.imranmelikov.simpleinstagramclone.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.imranmelikov.simpleinstagramclone.Activities.ProfilActivity
import com.imranmelikov.simpleinstagramclone.Activities.UploadActivity
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.FragmentProfilBinding
import de.hdodenhof.circleimageview.CircleImageView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var auth:FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfilBinding.inflate(inflater,container,false)
        val view=binding.root
        db= Firebase.firestore
        auth=Firebase.auth
        binding.editAccountSettingBtn.setOnClickListener {
            var intent=Intent(this.context, ProfilActivity::class.java)
            startActivity(intent)
        }
        binding.sharetext.setOnClickListener {
            var intent=Intent(it.context,UploadActivity::class.java)
            startActivity(intent)
        }
        var email=auth.currentUser!!.email
        db.collection(email.toString()).addSnapshotListener { value, error ->
            if(error!=null){
                Toast.makeText(this.context,error.localizedMessage, Toast.LENGTH_SHORT).show()
            }else{
                if(value!=null){
                    if(!value.isEmpty){
                        val documnets=value.documents
                        for(document in documnets){
                            var username=document.get("username")
                            var bio=document.get("bio")
                            var email=document.get("email")
                            binding.profileFragmentUsername.text=username.toString()
                            binding.fullNameProfileFrag.text=email.toString()
                            binding.BioProfile.text=bio.toString()
                        }
                    }
                }
            }
        }
        db.collection(email.toString()).addSnapshotListener { value, error ->
                if(error!=null){
                    Toast.makeText(this.context,error.localizedMessage, Toast.LENGTH_SHORT).show()
                }else{
                    if(value!=null){
                        if(!value.isEmpty){
                            val documnets=value.documents
                            for(document1 in documnets){
                                var username1=document1.get("username")
                                    db.collection(username1.toString()).addSnapshotListener { value, error ->
                                        if(error!=null){
                                            Toast.makeText(this.context,error.localizedMessage, Toast.LENGTH_SHORT).show()
                                        }else{
                                            if(value!=null){
                                                if(!value.isEmpty){
                                                    val documnets=value.documents
                                                    for(document2 in documnets){

                                                        var image = document2.get("downloadurl")
                                                        Glide.with(this)
                                                            .load(image.toString())
                                                            .into(binding.profileImageSearchRecyclerview)

                                                        var profilename = document2.get("profilename")
                                                        var profilebio = document2.get("profilebio")
                                                        binding.profileFragmentUsername.text = profilename.toString()
                                                        binding.fullNameProfileFrag.text = email.toString()
                                                        binding.BioProfile.text = profilebio.toString()
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
        return view
    }
}