package com.wkwkman.binarandroidchapter5challenge.ui.menu

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.ActivityMenuBinding
import com.wkwkman.binarandroidchapter5challenge.ui.game.GameActivity
import com.wkwkman.binarandroidchapter5challenge.ui.landing.form.FormNameFragment

class MenuActivity : AppCompatActivity() {
    private val binding: ActivityMenuBinding by lazy {
        ActivityMenuBinding.inflate(layoutInflater)
    }
    
    val name: String? by lazy {
        intent.getStringExtra(EXTRAS_NAME)
    }
    
    private fun showWelcomingPrompt() {
        val playerGreetMessage = "Selamat datang, $name"
        Snackbar.make(binding.root, playerGreetMessage, Snackbar.LENGTH_SHORT).show()
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        applyNameOnOptions()
        showWelcomingPrompt()
        setMenuClickListeners()
    }
    
    private fun applyNameOnOptions() {
        binding.tvMenuPvp.text = getString(R.string.placeholder_menu_pvp, name)
        binding.tvMenuPvc.text = getString(R.string.placeholder_menu_pvc, name)
    }
    
    private fun setMenuClickListeners() {
        binding.llMenuPvp.setOnClickListener {
            GameActivity.runActivity(this, true, name)
        }
        binding.llMenuPvc.setOnClickListener {
            GameActivity.runActivity(this, false, name)
        }
    }
    
    companion object {
        private const val EXTRAS_NAME = "EXTRAS_NAME"
        
        fun runActivity(context: Context, name: String) {
            context.startActivity(Intent(context, MenuActivity::class.java).apply {
                putExtra(EXTRAS_NAME, name)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            })
        }
    }
}