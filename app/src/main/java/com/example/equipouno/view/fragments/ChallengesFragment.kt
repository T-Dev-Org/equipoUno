package com.example.equipouno.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentChallengesBinding

class ChallengesFragment : Fragment() {

    private lateinit var binding: FragmentChallengesBinding

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
    }

    private fun setupToolbar() {
        val toolbar = binding.challengeToolbar.toolbarChallenges
        // Establece el Toolbar como la ActionBar para la actividad actual
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Esto asegura que el Toolbar no muestre el título por defecto
        (activity as AppCompatActivity).supportActionBar?.title = null

        // Esto asegura de que el TextView en el Toolbar tenga el texto correcto
        binding.toolbarTitle.text = getString(R.string.challenges_title)

        // Configura navegación al HomeFragment
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
            unmuteAudio()
        }

        // Esto asegura que se establece correctamente el boton de para vovler
        toolbar.navigationIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_back)
    }

    private fun unmuteAudio() {
        // TODO: Añadir la lógica para desactivar el silencio
        Toast.makeText(context, "TODO: Desilenciar audio", Toast.LENGTH_SHORT).show()
    }
}