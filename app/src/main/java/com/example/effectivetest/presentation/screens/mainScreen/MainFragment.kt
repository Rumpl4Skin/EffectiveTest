package com.example.effectivetest.presentation.screens.mainScreen

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat.getColor
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), CourseItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainFragmentViewModel by viewModels()

    private lateinit var filterPanel: LinearLayout
    private lateinit var courseAdapter: CourseAdapter
    private lateinit var snackbarToOffline: Snackbar
    private lateinit var snackbarToOnline: Snackbar


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
        var canShowSnackbar = false

        courseAdapter = CourseAdapter(
            onFavClick = this,
            onDetailCourseClick = this,
        )
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = courseAdapter

        val rootView: View = requireActivity().findViewById(android.R.id.content)

        snackbarToOffline = Snackbar.make(
            rootView,
            "Интернет соединение потеряно, хотите перейти в оффлайн режим или подождать восстановления связи?",
            Snackbar.LENGTH_LONG
        )
            .setAction("Оффлайн режим") {
                viewModel.goToOfflineMode(goOffline = true)
            }
            .setActionTextColor(
                getColor(
                    requireContext(),
                    R.color.green
                )
            ) // установка цвета текста кнопки

        snackbarToOnline = Snackbar.make(
            rootView,
            "Интернет соединение восстановлено, перейти в онлайн режим?",
            Snackbar.LENGTH_LONG
        )
            .setAction("Онлайн режим") {
                viewModel.goToOfflineMode(goOffline = false)
            }

            .setActionTextColor(
                getColor(
                    requireContext(),
                    R.color.green
                )
            ) // установка цвета текста кнопки
        lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_START)
            fun onStarted() {
                canShowSnackbar = true
            }
        })

        lifecycleScope.launch()
        {
            //проверка доступности интернета
            startInternetCheck(
                requireContext(),
                onStatusChange = viewModel::onNetworkStatusChange
            )
            viewModel.uiState.collect { uiState ->
                filterPanel.visibility =
                    if (uiState.filterPanelVisibility) View.VISIBLE else View.GONE

                courseAdapter.addItems(uiState.newCourses, uiState.newItemLoad)


                if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED))
                //отображаем, если интернета нет
                    if (!uiState.internetConnectionEnabled)
                        snackbarToOffline.show()
                    else {
                        snackbarToOnline.show()
                    }
            }
        }

        binding.recyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                    if (layoutManager.findLastCompletelyVisibleItemPosition() == courseAdapter.itemCount - 1) {
                        viewModel.getCoursesList()
                    }
                }
            })

        filterButton.setOnClickListener()
        {
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
        viewModel.checkFavStatus()
        courseAdapter.notifyItemChanged(viewModel.uiState.value.lastIdCourseDetail.toInt())
        courseAdapter.clearItems()
        courseAdapter.addItems(
            viewModel.uiState.value.courses,
            viewModel.uiState.value.courses.size
        )
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
        viewModel.setCourseFav(course = course)
        courseAdapter.notifyItemChanged(viewModel.uiState.value.courses.indexOf(course))
    }

    override fun onCourseDetailItemClicked(course: Course, image: ImageView) {
        // Установить имя перехода
        ViewCompat.setTransitionName(image, "courseImage")

        //передача id выбранного курса
        val action = MainFragmentDirections.actionCoursesFragmentToCourseDetailFragment(course.id)
        val extras = FragmentNavigatorExtras(image to "courseImage")
        findNavController().navigate(
            action,
            extras
        )
    }

    private fun startInternetCheck(
        context: Context,
        interval: Long = 5000L,
        onStatusChange: (Boolean) -> Unit
    ) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager // Функция для проверки доступности интернета

        fun isInternetAvailable(): Boolean {
            val network = connectivityManager.activeNetwork ?: return false
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
                ?: return false
            return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        // Запускаем корутину для периодической проверки

        CoroutineScope(Dispatchers.IO).launch {
            var lastStatus = isInternetAvailable()
            onStatusChange(lastStatus)
            while (isActive) {
                delay(interval)
                val currentStatus = isInternetAvailable()
                if (currentStatus != lastStatus) {
                    lastStatus = currentStatus
                    onStatusChange(currentStatus)
                }
            }
        }
    }
}