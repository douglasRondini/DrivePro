package com.douglasrondini.drive_20_android.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.douglasrondini.drive_20_android.R
import com.douglasrondini.drive_20_android.databinding.FragmentSplashBinding

import com.douglasrondini.drive_20_android.data.local.PreferenceManager
import org.koin.android.ext.android.inject

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val preferenceManager: PreferenceManager by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDelay()
    }

    private fun initDelay() {
        val progressBarHorizontal = binding.progressBar
        val totalProgressTime = 3000
        val progressBarMax = 100

        progressBarHorizontal.max = progressBarMax
        progressBarHorizontal.visibility = View.VISIBLE

        val animator = ValueAnimator.ofInt(0, progressBarMax)
        animator.duration = totalProgressTime.toLong()

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressBarHorizontal.progress = progress
        }
        animator.start()

        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                checkLoginStatus()
            }
        })
    }

    private fun checkLoginStatus() {
        val token = preferenceManager.getUserToken()
        val role = preferenceManager.getUserRole()

        if (token != null && role != null) {
            when (role.lowercase()) {
                "aluno" -> findNavController().navigate(R.id.action_splashFragment_to_bottomNavHomeActivity)
                "instrutor" -> findNavController().navigate(R.id.action_splashFragment_to_bottomNavInstrutorActivity)
                else -> findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
            }
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        }
    }
}