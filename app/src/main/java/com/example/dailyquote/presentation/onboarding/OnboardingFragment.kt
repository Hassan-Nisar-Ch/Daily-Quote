package com.example.dailyquote.presentation.onboarding

import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dailyquote.R
import com.example.dailyquote.databinding.FragmentOnboardingBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardingFragment : BaseFragment<FragmentOnboardingBinding, OnboardingViewModel>(
    FragmentOnboardingBinding::inflate
) {

    override val viewModel: OnboardingViewModel by viewModels()

    override fun setUpViews() {
        binding.apply {
            btnGetStarted.setOnClickListener {
                viewModel.onGetStartedClicked()
            }

            ivOnboarding.startAnimation(
                AnimationUtils.loadAnimation(
                    requireContext(),
                    R.anim.continuous_zoom_out_zoom_in
                )
            )
        }
    }

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.event.collect { event ->
                when (event) {
                    OnboardingViewModel.OnboardingEvent.NavigateToHome -> {
                        findNavController().navigate(R.id.onboardingFragment_to_homeFragment)
                    }
                }
            }
        }
    }
}