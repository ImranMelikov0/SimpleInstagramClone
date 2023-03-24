package com.imranmelikov.simpleinstagramclone.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.auth.User
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.databinding.RecyclerviewNotificationBinding

class AdapterRV_notification(val postarraylist:ArrayList<Post>): RecyclerView.Adapter<AdapterRV_notification.AdapterRV_Viewholder>() {
    class AdapterRV_Viewholder(val binding:RecyclerviewNotificationBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRV_Viewholder {
        val binding=RecyclerviewNotificationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterRV_Viewholder(binding)
    }

    override fun getItemCount(): Int {
        return postarraylist.size
    }

    override fun onBindViewHolder(holder: AdapterRV_Viewholder, position: Int) {
//        var post=postarraylist.get(position)
//        Glide.with(holder.itemView.context)
//            .load(post.downloadurl)
//            .into(holder.binding.notificationImageNotification)
//        holder.binding.usernameNotification.text=post.username
//        holder.binding.textNotification.text=post.comment
    }
}