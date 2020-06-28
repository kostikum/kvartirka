package com.kostikum.kvartirka.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kostikum.kvartirka.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private val args: DetailsFragmentArgs by navArgs()

    private var binding: FragmentDetailsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return FragmentDetailsBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            flat = args.flat
            detailsPhotoImageView.setOnClickListener {
                val direction = DetailsFragmentDirections
                    .actionDetailsFragmentToPhotosFragment(args.flat)
                findNavController().navigate(direction)
            }
        }
    }
}
