package com.example.gc02.view.home

// PagerAdapter.kt
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 3 // Número total de pestañas

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MisProductosFragment()
            1 -> MisFavoritosFragment()
            2 -> ConsultarPerfilFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
}
