package com.example.dailyquote.util

import android.os.Build
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

inline fun Fragment.launchAndRepeatWithViewLifecycle(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    crossinline block: suspend CoroutineScope.() -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.repeatOnLifecycle(state) {
            block()
        }
    }
}

@Suppress("DEPRECATION")
fun Fragment.setColoredStatusBar(color: Int) {
    val window = activity?.window ?: return
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        window.apply {
            isNavigationBarContrastEnforced = false
            // Disable contrast enforcement if needed
            insetsController?.setSystemBarsAppearance(
                0, // No special appearance
                WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS or
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
            )
            // Force black color for the navigation bar
            decorView.setBackgroundColor(color)
        }
    } else {
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
        window.decorView.systemUiVisibility = 0
    }
}
