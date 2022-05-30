package com.example.semana18

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter (fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2
    // en el adaptador elegimos que Fragment se utilizara dependiendo de la posicion del adaptador.
    override fun createFragment(position: Int): Fragment {
        when(position){
            1 -> return DevFragment()
            2 -> return MapFragment()
        }
        return MapFragment()
    }
}