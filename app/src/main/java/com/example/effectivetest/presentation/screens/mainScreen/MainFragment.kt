package com.example.effectivetest.presentation.screens.mainScreen

import SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentMainBinding
import com.example.effectivetest.presentation.CourseAdapter
import com.example.effectivetest.presentation.interfaces.CourseItemClickListener
import com.example.effetivetest.domain.model.Course
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), CourseItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var filterPanel: LinearLayout
    private lateinit var courseAdapter: CourseAdapter


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
        filterPanel = binding.filterPanel

        courseAdapter = CourseAdapter(
            onFavClick = this,
            onDetailCourseClick = this,
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = courseAdapter

        lifecycleScope.launch {
            viewModel.uiState.collect { uiState ->
                filterPanel.visibility =
                    if (uiState.filterPanelVisibility) View.VISIBLE else View.GONE
                courseAdapter.addItems(uiState.newCourses, uiState.newItemLoad)
            }
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == courseAdapter.itemCount - 1) {
                    viewModel.getCoursesList()
                }
            }
        })

        filterButton.setOnClickListener {
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

    override fun onCourseFavItemClicked(course: Course) {
        TODO("Not yet implemented")
    }

    override fun onCourseDetailItemClicked(course: Course, image: ImageView) {
        sharedViewModel.selectCourse(course = course)
        // Установить имя перехода
        ViewCompat.setTransitionName(image, "courseImage")
        val action =  MainFragmentDirections.actionCoursesFragmentToCourseDetailFragment()
        val extras = FragmentNavigatorExtras(image to "courseImage")
        findNavController().navigate(
            action,
            extras
        )
    }
}