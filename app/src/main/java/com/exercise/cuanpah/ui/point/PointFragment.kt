package com.exercise.cuanpah.ui.point

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.exercise.cuanpah.data.UserPreference
import com.exercise.cuanpah.databinding.FragmentPointBinding
import com.exercise.cuanpah.ui.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PointFragment : Fragment() {

    private lateinit var pointViewModel: PointViewModel
    private var _binding: FragmentPointBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPointBinding.inflate(inflater, container, false)

        setupAction()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        setupAction()
    }

    private fun setupAction() {
        pointViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore), "")
        )[PointViewModel::class.java]

        pointViewModel.getUser().observe(requireActivity()) {
            pointViewModel.getPoint(it.id)

            binding.point.text = it.point.toString()
        }
    }
}