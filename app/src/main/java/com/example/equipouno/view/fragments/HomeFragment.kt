package com.example.equipouno.view.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.VideoView
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentHomeBinding
import retrofit2.http.Url

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
        toolbarOptions()

        if (!isMuted) {
            playSoundtrack()
        }

        pressButton()
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

    private fun pressButton()
    {
        //binding.orangeButton.setOnClickListener()
        {
            try {
                if (!isMuted) {
                    pauseSoundtrack()
                }

                binding.tvCountdown.visibility = View.VISIBLE
                countdownTime()

            } catch (e: Exception)
            {
                e.printStackTrace()
                println("Para")
            }
        }
    }

    private fun countdownTime() {
        // Obtiene el valor del TextView y lo convierte a milisegundos
        val countdown = binding.tvCountdown.text.toString().toInt() * 1000L

        // Crea un temporizador que cuenta hacia atrás desde el valor inicial hasta 0
        object : CountDownTimer(countdown, 1000) { // 1000 ms = 1 segundo
            override fun onTick(millisUntilFinished: Long) {
                // Actualiza el texto del TextView con el tiempo restante en segundos
                binding.tvCountdown.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                // Cuando el contador llega a 0, establece el texto en "0" o realiza otra acción
                binding.tvCountdown.text = "0"
            }
        }.start() // Inicia el contador
    }


    private  fun toolbarOptions(){
        val calificacion = view?.findViewById<ImageView>(R.id.calificacion)
        val volumen = view?.findViewById<ImageView>(R.id.volumen)
        val informacion = view?.findViewById<ImageView>(R.id.instrucciones)
        val agregar = view?.findViewById<ImageView>(R.id.agregar)
        val compartir = view?.findViewById<ImageView>(R.id.compartir)

        calificacion?.setOnClickListener{
            rateApp()
        }

        volumen?.setOnClickListener{
            setMusic(volumen)
        }

        informacion?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
        }

        agregar?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_challengesFragment)
        }
    }

    private fun rateApp(){
        val calificar = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"))
        startActivity(calificar)
    }

    private fun setMusic(icono : ImageView){
        isMuted = !isMuted
        if(isMuted){
            pauseSoundtrack()
            icono.setImageResource(R.drawable.ic_volume_off)
        }else{
            resumeSoundtrack()
            icono.setImageResource(R.drawable.ic_volume_up)
        }
    }

}