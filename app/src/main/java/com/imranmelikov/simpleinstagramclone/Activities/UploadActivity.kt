package com.imranmelikov.simpleinstagramclone.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivityUploadBinding
import java.util.UUID

class UploadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionlauncher: ActivityResultLauncher<String>
    private  var selectedimage:Uri?=null
    private lateinit var auth:FirebaseAuth
    private lateinit var db:FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUploadBinding.inflate(layoutInflater)
       val view= binding.root
        setContentView(view)

        auth=Firebase.auth
        db=Firebase.firestore

        registerlauncher()
    }

    fun selectimage(view: View){
        binding.uploadimage.setOnClickListener {
            if(ContextCompat.checkSelfPermission(it.context, Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(it.context as Activity,
                        Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(it,"Permission needed", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
                        View.OnClickListener {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                permissionlauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                            }
                        }).show()
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        permissionlauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                    }
                }
            }else{
                var intenttogallery= Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                activityResultLauncher.launch(intenttogallery)
            }
        }
    }
    fun registerlauncher(){
        activityResultLauncher=registerForActivityResult(ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback {result->
                if(result.resultCode== RESULT_OK){
                    var intentfromresult=result.data
                    if(intentfromresult!=null){
                        selectedimage=intentfromresult.data
                        selectedimage.let {
                            binding.uploadimage.setImageURI(it)
                        }
                    }
                }
            })
        permissionlauncher=registerForActivityResult(ActivityResultContracts.RequestPermission(),
            ActivityResultCallback {
                if(it){
                    var intenttogallery=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intenttogallery)
                }else{
                    Toast.makeText(this,"Permission needed",Toast.LENGTH_SHORT).show()
                }
            })
    }
    fun Shareclick(view:View){
        val uuid=UUID.randomUUID()
        val imagename="$uuid.jpg"
        val storage=Firebase.storage
        var reference=storage.reference
        var imagereference=reference.child("Image").child(imagename)
        if(selectedimage!=null){
            imagereference.putFile(selectedimage!!).addOnSuccessListener {
                var uploadreference=storage.reference.child("Image").child(imagename)
                uploadreference.downloadUrl.addOnSuccessListener {
                    var downloadimage=it.toString()
                    if(auth.currentUser!=null){
                        var posthasmap=HashMap<String,Any>()
                        posthasmap.put("downloadimage",downloadimage)
                        posthasmap.put("comment",binding.commenttext.text.toString())
                        db.collection(auth.currentUser!!.email.toString()).addSnapshotListener { value, error ->
                            if(error!=null){
                                Toast.makeText(this,error.localizedMessage,Toast.LENGTH_SHORT).show()
                            }else{
                                if(value!=null){
                                    if(!value.isEmpty){
                                        val documents=value.documents
                                        for(document in documents){
                                            val username=document.get("username")
                                            posthasmap.put("username",username.toString())
                                            db.collection(username.toString()).addSnapshotListener { value, error ->
                                                if(error!=null){
                                                    Toast.makeText(this,error.localizedMessage,Toast.LENGTH_SHORT).show()
                                                }else{
                                                    if(value!=null){
                                                        if(!value.isEmpty){
                                                            val documents=value.documents
                                                            for(document1 in documents){
                                                               val profilename=document1.get("profilename")
                                                                posthasmap.put("profilename",profilename.toString())
                                                                db.collection("Posts").add(posthasmap).addOnSuccessListener {
                                                                    finish()
                                                                }.addOnFailureListener {
                                                                    Toast.makeText(this@UploadActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                            db.collection("Posts").add(posthasmap).addOnSuccessListener {
                                                finish()
                                            }.addOnFailureListener {
                                                Toast.makeText(this@UploadActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            }
                        }


                    }
                }.addOnFailureListener {
                    Toast.makeText(this@UploadActivity,it.localizedMessage,Toast.LENGTH_SHORT).show()
                }
            }
        }else{
            Toast.makeText(this,"Choose image",Toast.LENGTH_SHORT).show()
        }
    }
}