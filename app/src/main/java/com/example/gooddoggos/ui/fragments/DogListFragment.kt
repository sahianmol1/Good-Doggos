package com.example.gooddoggos.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.gooddoggos.R
import com.example.gooddoggos.databinding.FragmentDogListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DogListFragment: Fragment(R.layout.fragment_dog_list) {

    private val viewModel: DogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentDogListBinding.bind(view)

        val adapter = DogAdapter()

        binding.apply {
            recyclerView.setHasFixedSize(true)
            recyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                    header = LoadStateAdapter { adapter.retry() },
                    footer = LoadStateAdapter { adapter.retry() }
            )
        }

        viewModel.getAllDogs().observe(viewLifecycleOwner, Observer { dogs ->
            adapter.submitData(viewLifecycleOwner.lifecycle, dogs)
        })
    }
}