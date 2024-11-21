package com.example.effectivetest.presentation.screens.courseInfoScreen

import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresExtension
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.effectivetest.presentation.glide.SvgSoftwareLayerSetter
import com.example.effectivetest.R
import com.example.effectivetest.databinding.FragmentCourseInfoBinding
import com.example.effectivetest.presentation.screens.mainScreen.MainFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CourseInfoFragment : Fragment() {

    private val args: CourseInfoFragmentArgs by navArgs()
    private var _binding: FragmentCourseInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CourseInfoFragmentViewModel by viewModels()
    private lateinit var requestBuilder: RequestBuilder<PictureDrawable>


    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCourseInfoBinding.inflate(inflater, container, false)
        viewModel.setCourse(courseId = args.id) //получение id выбранного course из предыдущего экрана

        //svg support
        requestBuilder =
            Glide.with(requireContext())
                .`as`(PictureDrawable::class.java)
                .placeholder(R.drawable.search_background)
                .error(R.drawable.ic_search)
                .listener(SvgSoftwareLayerSetter())

        lifecycleScope.launch {
            viewModel.uiState.collect {

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

                binding.isFavBtn.isChecked = viewModel.uiState.value.course.isFavorite


                binding.isFavBtn.setOnClickListener {
                    viewModel.updateCourseFav()
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

                val imageView: ImageView = binding.courseImg

                Glide.with(requireContext()).load(viewModel.uiState.value.course.cover)
                    .placeholder(R.drawable.search_background).error(R.drawable.ic_search)
                    .into(imageView)
                // Установить имя перехода
                ViewCompat.setTransitionName(imageView, "courseImageTransition")

                val url = viewModel.uiState.value.course.author.photo
                if (it.course.author.id.toInt() != 0 && url.isNotEmpty()) {
                    if (url[url.length - 2] == 'v') { //svg
                        requestBuilder.load(viewModel.uiState.value.course.author.photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .error(R.drawable.ic_search)
                            .into(binding.authorImg)
                    } else { //other img format
                        Glide.with(requireContext())
                            .load(viewModel.uiState.value.course.author.photo)
                            .apply(RequestOptions.bitmapTransform(CircleCrop()))
                            .placeholder(R.drawable.search_background)
                            .error(R.drawable.ic_search)
                            .into(binding.authorImg)
                    }
                }

                binding.courseAuthorTxt.text = it.course.author.fullName
                binding.isFavBtn.isChecked = it.course.isFavorite

            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


}