package com.wkwkman.binarandroidchapter5challenge.ui.landing.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.FragmentFormNameBinding

/**
 * Use the [FormNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormNameFragment: Fragment() {
    private lateinit var binding: FragmentFormNameBinding
    lateinit var name: String
    private var listener: OnNameSubmittedListener? = null
    
    fun setNameSubmittedListener(desiredListener: OnNameSubmittedListener) {
        this.listener = desiredListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFormNameBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.tvFormName.isVisible = false
        setButtonListener()
    }
    
    private fun setButtonListener() {
        binding.btnSetName.setOnClickListener {
            name = binding.etSetName.text.toString().trim()
            listener?.onNameSubmitted(name)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormNameFragment()
    }
}

interface OnNameSubmittedListener {
    fun onNameSubmitted(name: String)
}