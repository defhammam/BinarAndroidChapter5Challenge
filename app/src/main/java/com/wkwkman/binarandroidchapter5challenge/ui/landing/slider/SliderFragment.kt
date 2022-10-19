package com.wkwkman.binarandroidchapter5challenge.ui.landing.slider

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.wkwkman.binarandroidchapter5challenge.databinding.FragmentSliderBinding
import com.wkwkman.binarandroidchapter5challenge.model.SliderData

/**
 * Use the [SliderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SliderFragment: Fragment() {
    private lateinit var binding: FragmentSliderBinding
    private var sliderData: SliderData? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            sliderData = it.getParcelable(ARG_SLIDER_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSliderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindSliderData()
    }
    
    private fun bindSliderData() {
        with (binding) {
            tvLanding.text = sliderData?.desc.orEmpty()
            ivLanding.load(sliderData?.imgSlider) { crossfade(true) }
        }
    }

    companion object {
        private const val ARG_SLIDER_DATA = "ARG_SLIDER_DATA"
        
        @JvmStatic
        fun newInstance(sliderData: SliderData) =
            SliderFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_SLIDER_DATA, sliderData)
                }
            }
    }
}