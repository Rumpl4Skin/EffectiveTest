package com.example.effectivetest.presentation.recycler.course

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.effetivetest.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager

class CourseAdapter(onFavClick: CourseItemClickListener, onDetailCourseClick: CourseItemClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val delegatesManager = AdapterDelegatesManager<List<DisplayableItem>>()
    private var items: List<DisplayableItem> = emptyList()

    init {
        delegatesManager.addDelegate(
            CourseItemDelegate(
                onDetailCourseClick = onDetailCourseClick,
                onFavClick = onFavClick
            )
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(items, position, holder)
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(items, position)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun addItems(newItems: List<Course>, countNewItems: Int) {
        items = items + newItems.map { CourseDisplayableItem(it) }
        notifyItemRangeChanged(itemCount, countNewItems)

    }
}