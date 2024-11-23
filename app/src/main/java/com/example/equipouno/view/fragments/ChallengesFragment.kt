package com.example.equipouno.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentChallengesBinding
import com.example.equipouno.model.Challenge
import com.example.equipouno.view.adapter.ChallengeAdapter
import com.example.equipouno.view.dialogue.CustomDialog
import com.example.equipouno.viewmodel.ChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding
    private val challengeViewModel: ChallengeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChallengesBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        controlator()
        observerViewModel()
    }

    private fun controlator() {
        binding.floatingButtonAddChallenge.setOnClickListener {
            CustomDialog.showCustomDialog(
                context = requireContext(),
                title = "Agregar Reto",
                hint = "Escriba el reto",
                positiveButtonText = "Guardar",
                negativeButtonText = "Cancelar",
                onPositiveClick = { description ->
                    val newChallenge = Challenge(description = description)
                    challengeViewModel.saveChallenge(newChallenge)
                    observerListChallenge()
                },
                onNegativeClick = {}
            )
        }
    }

    private fun observerViewModel() {
        observerListChallenge()
        observerProgress()
    }

    private fun observerListChallenge() {
        challengeViewModel.getListChallenge()
        challengeViewModel.listChallenge.observe(viewLifecycleOwner) { listChallenge ->
            val recycler = binding.recyclerView
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = ChallengeAdapter(listChallenge, findNavController(), challengeViewModel)
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun observerProgress(){
        challengeViewModel.progressState.observe(viewLifecycleOwner){ status ->
            binding.progressBar.isVisible = status
        }
    }

    private fun setupToolbar() {
        val toolbar = binding.challengeToolbar.toolbarChallenges
        // Establece el Toolbar como la ActionBar para la actividad actual
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Esto asegura que el Toolbar no muestre el título por defecto
        (activity as AppCompatActivity).supportActionBar?.title = null

        // Esto asegura de que el TextView en el Toolbar tenga el texto correcto
        //binding.toolbarTitle.text = getString(R.string.challenges_title)

        // Configura navegación al HomeFragment
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Esto asegura que se establece correctamente el boton de para vovler
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
    }
}