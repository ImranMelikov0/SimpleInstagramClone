package com.imranmelikov.simpleinstagramclone.Models

import android.net.Uri

class User (var uid:String?,var password:String?,var username:String?,var bio:String?,var profile:String?){

    fun getusername(): String? {
        return username
    }
    fun setusername(username: String?){
        this.username=username
    }
    fun getuid(): String? {
        return uid
    }
    fun setuid(uid: String?){
        this.uid=uid
    }
    fun getpassword(): String? {
        return password
    }
    fun setpassword(password: String?){
        this.password=password
    }
    fun getbio(): String? {
        return bio
    }
    fun setbio(bio: String?){
        this.bio=bio
    }
    fun getprofile(): String? {
        return profile
    }
    fun setprofile(profile: String?){
        this.profile=profile
    }

}