package com.imranmelikov.simpleinstagramclone.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.imranmelikov.simpleinstagramclone.Activities.ProfilActivity
import com.imranmelikov.simpleinstagramclone.databinding.FragmentProfilBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class ProfilFragment : Fragment() {
    private lateinit var binding: FragmentProfilBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentProfilBinding.inflate(inflater,container,false)
        val view=binding.root
        binding.editAccountSettingBtn.setOnClickListener {
            var intent=Intent(this.context, ProfilActivity::class.java)
            startActivity(intent)
        }
        return view
    }

}