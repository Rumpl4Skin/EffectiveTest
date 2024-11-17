package com.example.effectivetest.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.effectivetest.R
import com.example.effectivetest.presentation.interfaces.CourseItemClickListener
import com.example.effetivetest.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate

class CourseItemDelegate(
    val onFavClick: CourseItemClickListener,
    val onDetailCourseClick: CourseItemClickListener
) : AdapterDelegate<List<DisplayableItem>>() {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.course_item, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(
        items: List<DisplayableItem>,
        position: Int,
        holder: RecyclerView.ViewHolder,
        payloads: List<Any>
    ) {
        val course = items[position] as CourseDisplayableItem
        (holder as CourseViewHolder).bind(course.course, onFavClick,onDetailCourseClick)
    }

    override fun isForViewType(items: List<DisplayableItem>, position: Int): Boolean {
        return items[position] is CourseDisplayableItem
    }

    class CourseViewHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val courseImg: ImageView = itemView.findViewById(R.id.courseImg)
        private val toFavoriteBtn: ToggleButton = itemView.findViewById(R.id.toFavoriteBtn)

        private val ratingTxt: TextView = itemView.findViewById(R.id.ratingTxt)
        private val lastUpdTxt: TextView = itemView.findViewById(R.id.lastUpdTxt)
        private val nameCourseTxt: TextView = itemView.findViewById(R.id.nameCourseTxt)
        private val descriptionCourseTxt: TextView =
            itemView.findViewById(R.id.descriptionCourseTxt)
        private val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        private val toDetailCourseTxt: TextView = itemView.findViewById(R.id.toDetailCourseTxt)

        fun bind(
            course: Course,
            onFavClick: CourseItemClickListener,
            onDetailCourseClick: CourseItemClickListener
        ) {
            nameCourseTxt.text = course.title
            descriptionCourseTxt.text = course.summary
            lastUpdTxt.text = course.updateDate
            priceTxt.text = course.price ?: itemView.context.getString(R.string.is_free)
            // Glide, чтобы загрузить изображение
            Glide.with(itemView.context).load(course.cover).into(courseImg)
            toFavoriteBtn.isChecked = course.isFavorite

            toDetailCourseTxt.setOnClickListener { onFavClick.onCourseDetailItemClicked(course = course, image = courseImg) }
            toFavoriteBtn.setOnClickListener { onDetailCourseClick.onCourseFavItemClicked(course = course) }
        }
    }
}
