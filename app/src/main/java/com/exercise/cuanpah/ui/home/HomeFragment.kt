package com.exercise.cuanpah.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.exercise.cuanpah.camera.CameraActivity
import com.exercise.cuanpah.data.UserPreference
import com.exercise.cuanpah.databinding.FragmentHomeBinding
import com.exercise.cuanpah.ui.ViewModelFactory
import com.exercise.cuanpah.ui.maps.MapsActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupAction()

        binding.panggilKurirButton.setOnClickListener {
            startActivity(Intent(context,MapsActivity::class.java))
        }

        binding.pindaiSampahButton.setOnClickListener {
            startActivity(Intent(context, CameraActivity::class.java))
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @SuppressLint("SetTextI18n")
    private fun setupAction() {
        homeViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), "")
        )[HomeViewModel::class.java]

        homeViewModel.getUser().observe(requireActivity()) {
            binding.greetings.text = "Hello, ${it.name}"
        }

    }
}