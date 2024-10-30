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
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.TextView
import android.widget.VideoView
import android.widget.ImageView
import androidx.databinding.adapters.ViewGroupBindingAdapter.OnAnimationEnd
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentBottleBinding
import com.example.equipouno.databinding.FragmentHomeBinding
import retrofit2.http.Url
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var bottleMediaPlayer: MediaPlayer
    private var timer: CountDownTimer? = null
    private var spinning = false
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
        } else
        {
            pauseSoundtrack()
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

    private fun releaseBottleSound(){
        // Liberar recursos del MediaPlayer al destruir el fragmento
        if (this::bottleMediaPlayer.isInitialized) {
            bottleMediaPlayer.release()
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

    private fun playBottleSound(){
        // Se le pasa la pista de audio
        bottleMediaPlayer = MediaPlayer.create(requireContext(), R.raw.bottle_spinning)

        // Se inicia la reproducción
        bottleMediaPlayer.start()

        bottleMediaPlayer.setOnCompletionListener {
            bottleMediaPlayer.seekTo(0) // Volver al inicio de la canción
            bottleMediaPlayer.start()   // Reproducir nuevamente
        }
    }

    private fun pressButton()
    {
        //binding.orangeButton.setOnClickListener()
        {
            try {
                if (!isMuted) {
                    releaseSoundtrack()
                }

                binding.tvCountdown.visibility = View.INVISIBLE
                binding.tvCountdown.text = "4"

                bottleSpin()
                {
                    binding.tvCountdown.visibility = View.VISIBLE
                    countdownTime()
                }

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

        binding.orangeButton.visibility = View.INVISIBLE
        binding.tvPressMe.visibility = View.INVISIBLE
        timer?.cancel()

        // Crea un temporizador que cuenta hacia atrás desde el valor inicial hasta 0
        timer = object : CountDownTimer(countdown, 1000) { // 1000 ms = 1 segundo
            override fun onTick(millisUntilFinished: Long) {
                // Actualiza el texto del TextView con el tiempo restante en segundos
                binding.tvCountdown.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                // Cuando el contador llega a 0, establece el texto en "0" o realiza otra acción
                binding.tvCountdown.text = "0"
                binding.orangeButton.visibility = View.VISIBLE
                binding.tvPressMe.visibility = View.VISIBLE
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

    private fun bottleSpin(onAnimationEnd: () -> Unit)
    {
        if(!spinning)
        {
            val newPosition = currentPosition + Random.nextInt(2400 ,4200)

            val pivotX = binding.bottle.width / 2f
            val pivotY = binding.bottle.height / 2f

            val rotate = RotateAnimation(currentPosition.toFloat(), newPosition.toFloat(), pivotX, pivotY).apply {
                duration = 5000
                fillAfter = true

                setAnimationListener(object : Animation.AnimationListener {
                    override fun onAnimationStart(animation: Animation?) {
                        spinning = true
                        binding.orangeButton.visibility = View.INVISIBLE
                        binding.tvPressMe.visibility = View.INVISIBLE
                        playBottleSound()
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        spinning = false
                        onAnimationEnd()
                        releaseBottleSound()
                    }

                    override fun onAnimationRepeat(animation: Animation?) {
                        // No hace nada en este caso
                    }
                })
            }

            currentPosition = newPosition

            binding.bottle.startAnimation(rotate)
        }
    }
}