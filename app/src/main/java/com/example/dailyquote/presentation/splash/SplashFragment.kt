package com.example.dailyquote.presentation.splash

import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dailyquote.R
import com.example.dailyquote.databinding.FragmentSplashBinding
import com.example.dailyquote.presentation.base.BaseFragment
import com.example.dailyquote.util.launchAndRepeatWithViewLifecycle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel>(
    FragmentSplashBinding::inflate
) {

    override val viewModel: SplashViewModel by viewModels()

    override fun observeData() {
        launchAndRepeatWithViewLifecycle {
            viewModel.progress.collect { progress ->
                binding.animationView.progress = progress / 100f
            }
        }

        launchAndRepeatWithViewLifecycle {
            viewModel.event.collect { event ->
                when (event) {
                    SplashViewModel.SplashEvent.NavigateToOnboarding -> {
                        findNavController().navigate(R.id.splashFragment_to_onboardingFragment)
                    }
                }
            }
        }
    }
}
