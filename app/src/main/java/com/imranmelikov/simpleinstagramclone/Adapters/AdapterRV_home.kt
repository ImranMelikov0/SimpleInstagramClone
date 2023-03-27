package com.imranmelikov.simpleinstagramclone.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.simpleinstagramclone.databinding.RecyclerviewHomeBinding
import com.imranmelikov.simpleinstagramclone.ui.HomeFragment

class AdapterRV_home(var homefragmentarraylist:ArrayList<com.imranmelikov.simpleinstagramclone.Models.HomeFragment>):RecyclerView.Adapter<AdapterRV_home.AdapterRV_homeViewholder>() {
    class AdapterRV_homeViewholder(var binding:RecyclerviewHomeBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRV_homeViewholder {
        var binding=RecyclerviewHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterRV_homeViewholder(binding)
    }

    override fun getItemCount(): Int {
        return homefragmentarraylist.size
    }

    override fun onBindViewHolder(holder: AdapterRV_homeViewholder, position: Int) {
        var homefragment=homefragmentarraylist.get(position)
        holder.binding.usernameHome.text=homefragment.username
        Glide.with(holder.itemView.context)
            .load(homefragment.image)
            .into(holder.binding.profileImageSearchRecyclerview)
//        holder.binding.followbtnHome
    }
}