package com.android.example.tanmen.Controller.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.example.tanmen.Controller.Fragment.SearchBottomSheetDialogFragment
import com.android.example.tanmen.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//
//        supportFragmentManager.beginTransaction().apply {
//            add(R.id.fragment, MainFragment())
//            commit()
//        }

        binding.fab.setOnClickListener {
            val dialog = SearchBottomSheetDialogFragment()
            dialog.show(supportFragmentManager, dialog.tag)
        }
    }
}