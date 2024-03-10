package com.example.taipeitravel.ui.attraction

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.taipeitravel.R
import com.example.taipeitravel.databinding.FragmentAttractionBinding
import kotlinx.coroutines.launch


class AttractionFragment : Fragment() {

    private var _binding: FragmentAttractionBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AttractionViewModel> { AttractionViewModel.Factory }
    private val args by navArgs<AttractionFragmentArgs>()
    private var imageCount: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAttractionBinding.inflate(inflater, container, false)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        setEventListener()
        observeViewModel()
    }

    private fun setEventListener() = binding.run {
        vpImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            @SuppressLint("SetTextI18n")
            override fun onPageSelected(position: Int) {
                tvImageProgress.text = "${position + 1} / $imageCount"
            }
        })
        tvUrl.setOnClickListener {
            findNavController().navigate(
                AttractionFragmentDirections.actionAttractionFragmentToWebFragment(
                    title = args.title,
                    url = viewModel.uiState.value.officialSite
                )
            )
        }
    }

    private fun initView() = binding.run {
        viewModel.getAttraction(args.id)
        toolbar.title = args.title
        toolbar.setupWithNavController(findNavController())
        vpImage.adapter = ImageSliderAdapter()
        vpImage.offscreenPageLimit = 3

    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateView(it)
                }
            }
        }
    }

    private fun updateView(uiState: AttractionViewModel.UiState) = binding.run {
        (vpImage.adapter as ImageSliderAdapter).submitList(uiState.images)
        imageCount = uiState.images.size
        tvOpenTime.text =
            StringBuilder(getString(R.string.open_Time)).append(": ").append(uiState.openTime)
        tvAddress.text =
            StringBuilder(getString(R.string.address)).append(": ").append(uiState.address)
        tvTel.text = StringBuilder(getString(R.string.tel)).append(": ").append(uiState.tel)
        tvUrl.text =
            StringBuilder(getString(R.string.url)).append(": ").append(uiState.officialSite)
        tvIntroduction.text = uiState.introduction
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}