package com.wkwkman.binarandroidchapter5challenge.ui.resultdialog

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.FragmentResultMenuDialogBinding
import com.wkwkman.binarandroidchapter5challenge.enum.GameResult

/**
 * A simple [Fragment] subclass.
 * Use the [ResultMenuDialog.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultMenuDialog : DialogFragment() {
    private lateinit var binding: FragmentResultMenuDialogBinding
    private lateinit var result: String
    private lateinit var name: String
    private var listener: OnMenuSelectedListener? = null
    
    fun setOnMenuSelectedListener(desiredListener: OnMenuSelectedListener) {
        this.listener = desiredListener
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentResultMenuDialogBinding.inflate(inflater, container, false)
        result = arguments?.getString(ARG_DIALOG_RESULT) ?: ""
        Log.d(ResultMenuDialog::class.java.simpleName, "game result: $result")

        if (result == GameResult.DRAW.toString())
            binding.tvResultOutcome.text = getString(R.string.text_outcome_draw)
        else {
            name = arguments?.getString(ARG_DIALOG_NAME) ?: ""
            binding.tvResultOutcome.text = getString(R.string.placeholder_outcome_win, name)
        }
        
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnRestart.setOnClickListener {
            listener?.onRestartClicked(this)
        }
        binding.btnReturnMenu.setOnClickListener {
            listener?.onReturnToMenuClicked(this)
        }
    }
    
    companion object {
        /*private const val EXTRAS_GAME_RESULT = "EXTRAS_GAME_RESULT"
        private const val EXTRAS_NAME_PLAYER_ONE = "EXTRAS_NAME_PLAYER_ONE"
        private const val EXTRAS_NAME_PLAYER_TWO = "EXTRAS_NAME_PLAYER_TWO"
        private const val EXTRAS_NAME_BOT = "EXTRAS_NAME_BOT"
        
        fun runActivity(context: Context, endResult: String) {
            
        }*/
        private const val ARG_DIALOG_RESULT = "ARG_DIALOG_RESULT"
        private const val ARG_DIALOG_NAME = "ARG_DIALOG_NAME"
        
        fun newInstance(
            endResult: String = "other",
            currentName: String? = ""
        ) = ResultMenuDialog().apply {
            arguments = Bundle(2).apply {
                putString(ARG_DIALOG_RESULT, endResult)
                putString(ARG_DIALOG_NAME, currentName)
            }
        }
    }
}

interface OnMenuSelectedListener {
    fun onRestartClicked(dialog: DialogFragment)
    fun onReturnToMenuClicked(dialog: DialogFragment)
}