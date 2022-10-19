package com.wkwkman.binarandroidchapter5challenge.ui.landing

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.viewpager2.widget.ViewPager2
import com.wkwkman.binarandroidchapter5challenge.R
import com.wkwkman.binarandroidchapter5challenge.databinding.ActivityLandingBinding
import com.wkwkman.binarandroidchapter5challenge.model.SliderData
import com.wkwkman.binarandroidchapter5challenge.ui.landing.form.FormNameFragment
import com.wkwkman.binarandroidchapter5challenge.ui.landing.form.OnNameSubmittedListener
import com.wkwkman.binarandroidchapter5challenge.ui.landing.slider.SliderFragment
import com.wkwkman.binarandroidchapter5challenge.ui.menu.MenuActivity
import com.wkwkman.binarandroidchapter5challenge.utils.ViewPagerAdapter
import com.wkwkman.binarandroidchapter5challenge.utils.getNextIndex
import com.wkwkman.binarandroidchapter5challenge.utils.getPreviousIndex

class LandingActivity: AppCompatActivity(), OnNameSubmittedListener {
    private val binding: ActivityLandingBinding by lazy {
        ActivityLandingBinding.inflate(layoutInflater)
    }
    private val pagerAdapter: ViewPagerAdapter by lazy {
        ViewPagerAdapter(supportFragmentManager, lifecycle)
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.hide()
        initFragmentViewPager()
        setNavigatorListeners()
    }
    
    private fun initFragmentViewPager() {
        initAdapter()
        setupViewPager()
    }
    
    private fun initAdapter() {
        pagerAdapter.apply {
            addFragment(
                SliderFragment.newInstance(SliderData(
                desc = getString(R.string.text_landing_first),
                imgSlider = R.drawable.ic_landing_page1
            )))
            addFragment(
                SliderFragment.newInstance(SliderData(
                desc = getString(R.string.text_landing_second),
                imgSlider = R.drawable.ic_landing_page2
            )))
            addFragment(FormNameFragment.newInstance().apply {
                setNameSubmittedListener(this@LandingActivity)
            })
        }
    }
    
    private fun setupViewPager() {
        binding.vpLanding.apply {
            adapter = pagerAdapter
            registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when {
                        position == 0 -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isInvisible = true
                            binding.tvPrevious.isEnabled = false
                        }
                        position < pagerAdapter.getMaxIndex() -> {
                            binding.tvNext.isInvisible = false
                            binding.tvNext.isEnabled = true
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled = true
                        }
                        position == pagerAdapter.getMaxIndex() -> {
                            binding.tvNext.isInvisible = true
                            binding.tvNext.isEnabled = false
                            binding.tvPrevious.isInvisible = false
                            binding.tvPrevious.isEnabled = true
                        }
                    }
                }
            })
        }
        binding.dotsIndicator.attachTo(binding.vpLanding)
    }
    
    private fun setNavigatorListeners() {
        binding.tvNext.setOnClickListener {
            navigateToNextFragment()
        }
        binding.tvPrevious.setOnClickListener {
            navigateToPreviousFragment()
        }
    }
    
    private fun navigateToNextFragment() {
        val nextIndex = binding.vpLanding.getNextIndex()
        if (nextIndex != -1)
            binding.vpLanding.setCurrentItem(nextIndex, true)
    }
    
    private fun navigateToPreviousFragment() {
        val previousIndex = binding.vpLanding.getPreviousIndex()
        if (previousIndex != -1)
            binding.vpLanding.setCurrentItem(previousIndex, true)
    }

    override fun onNameSubmitted(name: String) {
        Log.d(LandingActivity::class.java.simpleName, "onNameSubmitted: $name")
        MenuActivity.runActivity(this@LandingActivity, name)
    }
}