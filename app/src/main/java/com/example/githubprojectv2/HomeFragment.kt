package com.example.githubprojectv2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.githubprojectv2.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        // Add the components to be manipulated here
        binding.buttonMain.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_listFragment)
        )
        val view = binding.root
        return view
    }
}