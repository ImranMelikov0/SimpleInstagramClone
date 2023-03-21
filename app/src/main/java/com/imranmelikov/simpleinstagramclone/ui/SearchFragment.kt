package com.imranmelikov.simpleinstagramclone.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imranmelikov.simpleinstagramclone.Activities.SearchActivity
import com.imranmelikov.simpleinstagramclone.databinding.FragmentSearchBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding

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
        return view
    }


}