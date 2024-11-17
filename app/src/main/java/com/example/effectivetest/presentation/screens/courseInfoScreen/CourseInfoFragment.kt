package com.example.effectivetest.presentation.screens.courseInfoScreen

import SharedViewModel
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentCourseInfoBinding
import com.example.effectivetest.databinding.FragmentMainBinding
import com.example.effetivetest.domain.model.Course

class CourseInfoFragment : Fragment() {

    private var _binding: FragmentCourseInfoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourseInfoBinding.inflate(inflater, container, false)

        val course = sharedViewModel.selectedCourse.observe(viewLifecycleOwner) { course ->
            val imageView: ImageView = binding.courseImg
            Glide.with(this).load(course.cover)
                .placeholder(R.drawable.search_background).error(R.drawable.ic_search)
                .into(imageView)

            binding.courseNameTxt.text = course.title
            binding.courseAuthorTxt.text = course.authors?.first().toString()
            binding.courseDescriptionTxt.text = course.summary
            /*binding.isFavBtn.setImageDrawable(
                context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        if (course.isFavorite) R.drawable.ic_is_fav_sel else R.drawable.ic_favorite
                    )
                }
            )*/

            binding.isFavBtn.setOnClickListener {

            }

            binding.goBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            // Установить имя перехода
            ViewCompat.setTransitionName(imageView, "courseImageTransition")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}