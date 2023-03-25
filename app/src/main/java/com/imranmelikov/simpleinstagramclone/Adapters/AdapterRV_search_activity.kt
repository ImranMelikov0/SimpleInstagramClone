package com.imranmelikov.simpleinstagramclone.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.simpleinstagramclone.Models.Post
import com.imranmelikov.simpleinstagramclone.Models.User
import com.imranmelikov.simpleinstagramclone.databinding.ActivitySearchBinding
import com.imranmelikov.simpleinstagramclone.databinding.RecyclerviewSearchActivityBinding

class AdapterRV_search_activity(var mUser:ArrayList<User>, private var isFragment:Boolean=false)
    : RecyclerView.Adapter<AdapterRV_search_activity.AdapterRV_search_activityViewholder>() {
    class AdapterRV_search_activityViewholder(var binding:RecyclerviewSearchActivityBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterRV_search_activityViewholder {
        var binding=RecyclerviewSearchActivityBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterRV_search_activityViewholder(binding)
    }

    override fun getItemCount(): Int {
        return mUser.size
    }

    override fun onBindViewHolder(holder: AdapterRV_search_activityViewholder, position: Int) {
        var user=mUser.get(position)
        holder.binding.textSearchRecyclerview.text=user.username
        Glide.with(holder.itemView.context)
            .load(user.profile)
            .into(holder.binding.profileImageSearchRecyclerview)
    }
}