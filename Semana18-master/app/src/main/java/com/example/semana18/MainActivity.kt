package com.example.semana18

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // Llamamos al adaptador
    private val adapter by lazy { ViewPagerAdapter(this)}

    // usamos el adaptador para poder usar el ViewPager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pager.adapter = adapter
    }
}