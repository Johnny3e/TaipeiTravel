package com.example.taipeitravel.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.taipeitravel.R
import com.example.taipeitravel.databinding.FragmentMainBinding

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() = binding.run {
        toolbar.apply {
            setupWithNavController(findNavController())
            setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.lang -> {
                        // TODO: show dialog
                        Toast.makeText(context, "lang", Toast.LENGTH_SHORT).show()
                        true
                    }
                    else -> false
                }
            }
        }
        news1.title.text = "標題標題標題標題標題標題標題標題標題"
        news1.content.text = "contentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontentcontent"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}