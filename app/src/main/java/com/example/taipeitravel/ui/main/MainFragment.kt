package com.example.taipeitravel.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.taipeitravel.R
import com.example.taipeitravel.data.model.Language
import com.example.taipeitravel.data.model.News
import com.example.taipeitravel.databinding.DialogLanguageBinding
import com.example.taipeitravel.databinding.FragmentMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import timber.log.Timber

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MainViewModel> { MainViewModel.Factory }

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
        Timber.d("onViewCreated")
        initView()
        setEventListener()
        observeViewModel()
    }

    private fun setEventListener() = binding.run {
        toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.lang -> {
                    showLanguageDialog()
                    true
                }

                else -> false
            }
        }

        news1.root.setOnClickListener {
            openNewsOnWeb(viewModel.uiState.value.news1!!)
        }
        news2.root.setOnClickListener {
            openNewsOnWeb(viewModel.uiState.value.news2!!)
        }
        news3.root.setOnClickListener {
            openNewsOnWeb(viewModel.uiState.value.news3!!)
        }

        (rvAttractions.adapter as AttractionAdapter).onItemClick = { item ->
            findNavController().navigate(
                MainFragmentDirections.actionMainFragmentToAttractionFragment(
                    title = item.name,
                    id = item.id
                )
            )
        }
    }

    private fun showLanguageDialog() {
        val dialogBinding = DialogLanguageBinding.inflate(layoutInflater)

        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.select_language))
            .setView(dialogBinding.root)
            .show()

        dialogBinding.rvLanguage.adapter = LanguageAdapter(Language.getCurrentLanguage()) {
            changeLanguage(it)
            dialog.dismiss()
        }.apply { submitList(Language.supportedList) }
    }

    private fun changeLanguage(language: Language) {
        val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(language.tag)
        AppCompatDelegate.setApplicationLocales(appLocale)
    }

    private fun openNewsOnWeb(news: News) {
        findNavController().navigate(
            MainFragmentDirections.actionMainFragmentToWebFragment(
                title = getString(R.string.news),
                url = news.url,
            )
        )
    }

    private fun observeViewModel() {
        viewModel.getData(Language.getCurrentLanguage())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateView(it)
                }
            }
        }
    }

    private fun updateView(uiState: MainViewModel.UiState) = binding.run {
        if (uiState.news1 == null) {
            news1.root.isVisible = false
        } else {
            news1.root.isVisible = true
            news1.title.text = uiState.news1.title
            news1.content.text = uiState.news1.content
        }
        if (uiState.news2 == null) {
            news2.root.isVisible = false
        } else {
            news2.root.isVisible = true
            news2.title.text = uiState.news2.title
            news2.content.text = uiState.news2.content
        }
        if (uiState.news3 == null) {
            news3.root.isVisible = false
        } else {
            news3.root.isVisible = true
            news3.title.text = uiState.news3.title
            news3.content.text = uiState.news3.content
        }
        (rvAttractions.adapter as AttractionAdapter).submitList(uiState.attractions)
    }

    private fun initView() = binding.run {
        toolbar.title = getString(R.string.travel_taipei)
        rvAttractions.adapter = AttractionAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}