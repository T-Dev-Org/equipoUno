package com.example.equipouno.view.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mediaPlayer: MediaPlayer
    private var isPaused = false
    private var isMuted = false // TODO: Util para el boton de la toolbar de silenciar y desilenciar
    private var currentPosition = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        if (!isMuted) {
            playSoundtrack()
        }
    }

    override fun onResume() {
        super.onResume()
        resumeSoundtrack()
    }

    override fun onPause() {
        super.onPause()
        pauseSoundtrack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releaseSoundtrack()
    }

    private fun initListeners(){
        addListenerBtnGoToChallenges()
        addListenerBtnGoToInstructions()
    }

    private fun pauseSoundtrack() {
        // Pausar el audio cuando el fragmento no esté visible
        if (!isPaused) {
            currentPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()  // Pausar la reproducción
            isPaused = true      // Marcar que está pausado
        }
    }

    private fun resumeSoundtrack() {
        // Reanudar la música si estaba pausada
        if (isPaused and !isMuted) {
            mediaPlayer.seekTo(currentPosition)
            mediaPlayer.start()  // Reanudar la reproducción
            isPaused = false
        }
    }

    private fun releaseSoundtrack(){
        // Liberar recursos del MediaPlayer al destruir el fragmento
        if (this::mediaPlayer.isInitialized) {
            mediaPlayer.release()
        }
    }

    private fun playSoundtrack(){
        // Se le pasa la pista de audio
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bg_soundtrack)

        // Se inicia la reproducción
        mediaPlayer.start()

        mediaPlayer.setOnCompletionListener {
            mediaPlayer.seekTo(0) // Volver al inicio de la canción
            mediaPlayer.start()   // Reproducir nuevamente
        }
    }

    private fun addListenerBtnGoToChallenges(){
        binding.btnGoToChallenges.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_challengesFragment)
        }
    }

    private fun addListenerBtnGoToInstructions(){
        binding.btnGoToInstructions.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
        }
    }
}