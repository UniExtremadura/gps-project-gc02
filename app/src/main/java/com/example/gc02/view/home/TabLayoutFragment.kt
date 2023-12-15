package com.example.gc02.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.setContentView
import androidx.fragment.app.Fragment
import com.example.gc02.databinding.ActivityHomeBinding
import com.example.gc02.databinding.FragmentListaArticulosBinding
import com.example.gc02.databinding.FragmentTabsLayoutBinding
import com.example.gc02.model.User
import com.google.android.material.tabs.TabLayoutMediator

class TabLayoutFragment : Fragment(){

    private lateinit var binding: FragmentTabsLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTabsLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

        val pagerAdapter = PagerAdapter(requireActivity())
        binding.viewPager.adapter = pagerAdapter

        try {
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> "Productos"
                    1 -> "Favoritos"
                    2 -> "Comentarios"
                    else -> null
                }
            }.attach()
        }catch (e: Exception) {
            e.printStackTrace()
            println("Error: ${e.message}")
            println("Causa raíz: ${e.cause}")
        }
    }


}