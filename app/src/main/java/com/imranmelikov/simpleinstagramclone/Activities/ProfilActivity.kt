package com.imranmelikov.simpleinstagramclone.Activities

import android.Manifest
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.model.mutation.ArrayTransformOperation.Remove
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivityProfilBinding
import java.util.*
import kotlin.collections.HashMap

class ProfilActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfilBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionlauncher: ActivityResultLauncher<String>
    private  var selectedimage: Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfilBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore

        registerlauncher()

        binding.closeProfileBtn.setOnClickListener {

           val alertDialog= AlertDialog.Builder(this)
            alertDialog.setTitle("Do you want exit from unsave?")
                alertDialog.setPositiveButton("Yes",DialogInterface.OnClickListener { dialog, which ->
                    var intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }).setNegativeButton("No",DialogInterface.OnClickListener { dialog, which ->
                    dialog.dismiss()
                })
            alertDialog.show()
        }

        logoutbtn(view)
        removebtn()

    }

    private fun logoutbtn(view: View) {
        binding.logoutBtn.setOnClickListener {
            auth.signOut()
            var intent = Intent(this@ProfilActivity, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun removebtn() {
        binding.deleteAccountBtn.setOnClickListener {
            val user = auth.currentUser!!
            user.delete()
            auth.signOut()
            var intent = Intent(this@ProfilActivity, SigninActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun selectimage(view: View){
        binding.profileImageViewProfile.setOnClickListener {
            if(ContextCompat.checkSelfPermission(it.context,
                    Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(it.context as Activity,
                        Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(it,"Permission needed", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            permissionlauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        })
                }else{
                    permissionlauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                }
            }else{
                var intenttogallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intenttogallery)
            }
        }
    }
    fun registerlauncher(){
        activityResultLauncher=registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result->
                if(result.resultCode== RESULT_OK){
                    var intentfromresult=result.data
                    if(intentfromresult!=null){
                        selectedimage=intentfromresult.data
                        selectedimage.let {
                            binding.profileImageViewProfile.setImageURI(it)
                        }
                    }
                }
            })
        permissionlauncher=registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {
                if(it){
                    var intenttogallery=Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intenttogallery)
                }else{
                    Toast.makeText(this,"Permission needed",Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun Save(view:View){
        if(binding.BioProfile.text.toString().equals("")||binding.usernameProfile.text.toString().equals("")){
            Toast.makeText(this,"Write name,bio and select image",Toast.LENGTH_SHORT).show()
        }else{
            val uuid= UUID.randomUUID()
            val imagename="$uuid.jpg"
            val storage=Firebase.storage
            var reference=storage.reference
            var imagereference=reference.child("Images").child(imagename)
            if(selectedimage!=null){
                imagereference.putFile(selectedimage!!).addOnSuccessListener {
                    var uploadreference=storage.reference.child("Images").child(imagename)
                    uploadreference.downloadUrl.addOnSuccessListener {
                        var downloadurl=it.toString()
                        if(auth.currentUser!=null){
                            var posthasmap = HashMap<String, Any>()
                            posthasmap.put("downloadurl", downloadurl)
                            posthasmap.put("profilename", binding.usernameProfile.text.toString())
                            posthasmap.put("profilebio", binding.BioProfile.text.toString())

                            db.collection(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
                                if(error!=null){
                                    Toast.makeText(this,error.localizedMessage,Toast.LENGTH_SHORT).show()
                                }else{
                                    if(value!=null){
                                        if(!value.isEmpty){
                                            var documents=value.documents
                                            for(document in documents){
                                                var username1=document.get("username")
//                                                db.collection(username1.toString()).add(posthasmap).addOnSuccessListener {
//                                                    finish()
//                                                }.addOnFailureListener {
//                                                    Toast.makeText(this@ProfilActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
//                                                }
                                                db.collection("User").add(posthasmap).addOnSuccessListener {
                                                    finish()
                                                }.addOnFailureListener {
                                                    Toast.makeText(this@ProfilActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }.addOnFailureListener {
                        Toast.makeText(this@ProfilActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Select image",Toast.LENGTH_SHORT).show()
            }
        }
        if(binding.BioProfile.text.toString().equals("")||binding.usernameProfile.text.toString().equals("")){
            Toast.makeText(this,"Write name,bio and select image",Toast.LENGTH_SHORT).show()
        }else{
            val uuid= UUID.randomUUID()
            val imagename="$uuid.jpg"
            val storage=Firebase.storage
            var reference=storage.reference
            var imagereference=reference.child("Images").child(imagename)
            if(selectedimage!=null){
                imagereference.putFile(selectedimage!!).addOnSuccessListener {
                    var uploadreference=storage.reference.child("Images").child(imagename)
                    uploadreference.downloadUrl.addOnSuccessListener {
                        var downloadurl=it.toString()
                        if(auth.currentUser!=null){
                            var posthasmap = HashMap<String, Any>()
                            posthasmap.put("downloadurl", downloadurl)
                            posthasmap.put("profilename", binding.usernameProfile.text.toString())
                            posthasmap.put("profilebio", binding.BioProfile.text.toString())

                            db.collection(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
                                if(error!=null){
                                    Toast.makeText(this,error.localizedMessage,Toast.LENGTH_SHORT).show()
                                }else{
                                    if(value!=null){
                                        if(!value.isEmpty){
                                            var documents=value.documents
                                            for(document in documents){
                                                var username1=document.get("username")
                                                db.collection(username1.toString()).add(posthasmap).addOnSuccessListener {
                                                    finish()
                                                }.addOnFailureListener {
                                                    Toast.makeText(this@ProfilActivity, it.localizedMessage, Toast.LENGTH_SHORT).show()
                                                }

                                            }
                                        }
                                    }
                                }
                            }


                        }
                    }.addOnFailureListener {
                        Toast.makeText(this@ProfilActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(this,"Select image",Toast.LENGTH_SHORT).show()
            }
        }
    }
}
