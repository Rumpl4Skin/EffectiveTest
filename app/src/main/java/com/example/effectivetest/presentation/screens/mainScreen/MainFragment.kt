package com.example.effectivetest.presentation.screens.mainScreen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModels()

    private lateinit var filterPanel: LinearLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filterButton = binding.filterButton
        val text = binding.text
        filterPanel = binding.filterPanel

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                text.text = uiState.courses.firstOrNull()?.title ?: "пусто"
                filterPanel.visibility =
                    if (uiState.filterPanelVisibility) View.VISIBLE else View.GONE

            }
        }

        filterButton.setOnClickListener {
            viewModel.getCoursesList()
            if (viewModel.uiState.value.filterPanelVisibility) {
                slideUp()
            } else {
                slideDown()
            }
            viewModel.changeFilterPanelVisibility()
        }

    }

    private fun slideDown() {
        filterPanel.visibility = View.INVISIBLE
        val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        filterPanel.startAnimation(slideDown)
    }

    private fun slideUp() {
        val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        filterPanel.startAnimation(slideUp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}