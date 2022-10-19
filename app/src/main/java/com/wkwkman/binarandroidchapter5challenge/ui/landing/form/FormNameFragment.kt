package com.wkwkman.binarandroidchapter5challenge.ui.form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.FragmentFormNameBinding

/**
 * Use the [FormNameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FormNameFragment : Fragment() {
    private lateinit var binding: FragmentFormNameBinding
    private var listener: OnNameSubmittedListener? = null

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
        setButtonListener()
    }
    
    private fun setButtonListener() {
        binding.btnSetName.setOnClickListener {
            val name = binding.etSetName.text.toString().trim()
            listener?.onNameSubmitted(name)
            Toast.makeText(requireContext(), "Welcome, $name", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = FormNameFragment() /* todo: navigate to menu page */
    }
}

interface OnNameSubmittedListener {
    fun onNameSubmitted(name: String)
}