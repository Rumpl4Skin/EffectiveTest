package com.example.effectivetest.presentation.screens.mainScreen

import SharedViewModel
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentMainBinding
import com.example.effectivetest.presentation.recycler.course.CourseAdapter
import com.example.effectivetest.presentation.recycler.course.CourseItemClickListener
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

    override fun onResume() {
        super.onResume()
        //viewModel.startPagePos()
        courseAdapter.clearItems()
        courseAdapter.addItems(
            viewModel.uiState.value.courses,
            viewModel.uiState.value.courses.size
        )
        //viewModel.updCoursesWithoutRepetitions()
    }

    private fun slideDown() {
        val slideDown = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        slideDown.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) { // Действия в начале анимации (если необходимы)
            }

            override fun onAnimationEnd(animation: Animation?) {
                // Действия после завершения анимации
                filterPanel.visibility = View.VISIBLE
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // Действия при повторении анимации (если необходимы)
            }
        })
        filterPanel.startAnimation(slideDown)
    }

    private fun slideUp() {
        val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up)
        slideUp.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(p0: Animation?) {
                //filterPanel.visibility = View.VISIBLE
                filterPanel.visibility = View.GONE
            }

            override fun onAnimationEnd(p0: Animation?) {

            }

            override fun onAnimationRepeat(p0: Animation?) {
            }

        })
        filterPanel.startAnimation(slideUp)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCourseFavItemClicked(course: Course) {

    }

    override fun onCourseDetailItemClicked(course: Course, image: ImageView) {
        sharedViewModel.selectCourse(course = course)
        // Установить имя перехода
        ViewCompat.setTransitionName(image, "courseImage")
        val action = MainFragmentDirections.actionCoursesFragmentToCourseDetailFragment()
        val extras = FragmentNavigatorExtras(image to "courseImage")
        findNavController().navigate(
            action,
            extras
        )
    }
}