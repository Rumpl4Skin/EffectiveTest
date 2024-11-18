package com.example.effectivetest.presentation.screens.courseInfoScreen

import SharedViewModel

import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.effectivetest.presentation.glide.SvgSoftwareLayerSetter
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentCourseInfoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CourseInfoFragment : Fragment() {

    private var _binding: FragmentCourseInfoBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: CourseInfoFragmentViewModel by viewModels()
    private lateinit var requestBuilder: RequestBuilder<PictureDrawable>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourseInfoBinding.inflate(inflater, container, false)

        //получение выбранного course из предыдущего экрана
        sharedViewModel.selectedCourse.observe(viewLifecycleOwner) { course ->
            viewModel.setCourse(course = course)

            //png support
            val imageView: ImageView = binding.courseImg
            Glide.with(this).load(viewModel.uiState.value.course.cover)
                .placeholder(R.drawable.search_background).error(R.drawable.ic_search)
                .into(imageView)

            //svg support
            requestBuilder =
                Glide.with(this)
                    .`as`(PictureDrawable::class.java)
                    .placeholder(R.drawable.search_background)
                    .error(R.drawable.ic_search)
                    .listener(SvgSoftwareLayerSetter())

            binding.lastUpdTxt.text = viewModel.uiState.value.course.updateDate
            binding.courseNameTxt.text =
                viewModel.uiState.value.course.title.ifEmpty { context?.getString(R.string.absent) }
            binding.courseAuthorTxt.text =
                viewModel.uiState.value.course.author.fullName.ifEmpty {
                    context?.getString(
                        R.string.absent
                    )
                }
            binding.ratingTxt.text =
                viewModel.uiState.value.course.rating.toString().ifEmpty {
                    context?.getString(
                        R.string.absent
                    )
                }


            binding.courseDescriptionTxt.text = Html.fromHtml(
                viewModel.uiState.value.course.description,
                Html.FROM_HTML_MODE_COMPACT
            )?.ifEmpty { context?.getString(R.string.absent) }
            binding.isFavBtn.background =
                context?.let {
                    AppCompatResources.getDrawable(
                        it,
                        if (viewModel.uiState.value.course.isFavorite) R.drawable.ic_is_fav_sel else R.drawable.ic_favorite
                    )
                }

            binding.isFavBtn.setOnClickListener {
                viewModel.uiState.value.course.copy(isFavorite = !viewModel.uiState.value.course.isFavorite)
                    .let { it1 -> viewModel.setCourse(it1) }
            }

            binding.goBackBtn.setOnClickListener {
                findNavController().popBackStack()
            }

            binding.startCourseBtn.setOnClickListener {
                if (viewModel.uiState.value.course.actualLink.isNotEmpty()) {
                    val webpage: Uri = Uri.parse(viewModel.uiState.value.course.actualLink)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(intent)
                } else Toast.makeText(context, getString(R.string.absent), Toast.LENGTH_SHORT)
                    .show()
            }

            binding.toPlatformBtn.setOnClickListener {
                if (viewModel.uiState.value.course.actualLink.isNotEmpty()) {
                    val webpage: Uri = Uri.parse(viewModel.uiState.value.course.actualLink)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(intent)
                } else Toast.makeText(context, getString(R.string.absent), Toast.LENGTH_SHORT)
                    .show()
            }

            // Установить имя перехода
            ViewCompat.setTransitionName(imageView, "courseImageTransition")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {

            viewModel.uiState.collect {
                val url = viewModel.uiState.value.course.author.photo
                if (it.course.author.id.toInt() != 0 && url.isNotEmpty()) {
                    if (url[url.length - 2] == 'v' ) {
                        requestBuilder.load(viewModel.uiState.value.course.author.photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .into(binding.authorImg)

                    } else {

                        Glide.with(requireContext())
                            .load(viewModel.uiState.value.course.author.photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .placeholder(R.drawable.search_background)
                            .error(R.drawable.ic_search)
                            .into(binding.authorImg)
                    }
                }

                binding.courseAuthorTxt.text = it.course.author.fullName
            }
        }
    }

}