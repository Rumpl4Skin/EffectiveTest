package com.example.effectivetest.presentation.recycler.course

import android.widget.ImageView
import com.example.effetivetest.domain.model.Course

interface CourseItemClickListener {
    fun onCourseFavItemClicked(course: Course)
    fun onCourseDetailItemClicked(course: Course, image:ImageView)
}