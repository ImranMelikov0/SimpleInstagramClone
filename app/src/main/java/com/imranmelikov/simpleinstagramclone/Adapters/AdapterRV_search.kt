package com.imranmelikov.simpleinstagramclone.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.imranmelikov.simpleinstagramclone.Models.Image
import com.imranmelikov.simpleinstagramclone.databinding.RecyclerviewSearchBinding

class AdapterRV_search(var imagearraylist:ArrayList<Image>):RecyclerView.Adapter<AdapterRV_search.AdapterRV_Viewholder>() {
    class AdapterRV_Viewholder(var binding:RecyclerviewSearchBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterRV_Viewholder {
       var binding=RecyclerviewSearchBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AdapterRV_Viewholder(binding)
    }

    override fun getItemCount(): Int {
       return imagearraylist.size
    }

    override fun onBindViewHolder(holder: AdapterRV_Viewholder, position: Int) {
        var image=imagearraylist.get(position)
        Glide.with(holder.itemView.context)
            .load(image.image)
            .into(holder.binding.imageView2)
    }
}