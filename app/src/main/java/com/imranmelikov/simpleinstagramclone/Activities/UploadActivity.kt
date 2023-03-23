package com.imranmelikov.simpleinstagramclone.Activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
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
import com.imranmelikov.simpleinstagramclone.R
import com.imranmelikov.simpleinstagramclone.databinding.ActivityUploadBinding

class UploadActivity : AppCompatActivity() {
    private lateinit var binding:ActivityUploadBinding
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionlauncher: ActivityResultLauncher<String>
    private  var selectedimage:Uri?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityUploadBinding.inflate(layoutInflater)
       val view= binding.root
        setContentView(view)

        registerlauncher()
    }

    fun selectimage(view: View){
        binding.uploadimage.setOnClickListener {
            if(ContextCompat.checkSelfPermission(it.context,
                    Manifest.permission.READ_MEDIA_IMAGES)!= PackageManager.PERMISSION_GRANTED){
                if(ActivityCompat.shouldShowRequestPermissionRationale(it.context as Activity,
                        Manifest.permission.READ_MEDIA_IMAGES)){
                    Snackbar.make(it,"Permission need", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission",
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
}