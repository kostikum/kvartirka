package com.kostikum.kvartirka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.kostikum.kvartirka.AndroidApplication
import com.kostikum.kvartirka.adapters.ViewPagerAdapter
import com.kostikum.kvartirka.databinding.FragmentPhotosBinding
import javax.inject.Inject

class PhotosFragment : Fragment() {
    private val args: PhotosFragmentArgs by navArgs()

    @Inject
    lateinit var viewPagerAdapter: ViewPagerAdapter
    private var binding: FragmentPhotosBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentPhotosBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity?.application as AndroidApplication).appComponent.inject(this)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            flat = args.flat

            viewPagerAdapter.setPhotos(args.flat.photos)
            viewPager.adapter = viewPagerAdapter
            viewPager.invalidate()
        }
    }
}