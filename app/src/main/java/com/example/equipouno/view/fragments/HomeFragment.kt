package com.example.equipouno.view.fragments

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentHomeBinding
import com.example.equipouno.repository.ChallengeRepository
import com.example.equipouno.repository.PokemonRepository
import com.example.equipouno.view.dialogue.ChallengeDialog
import kotlin.random.Random

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var bottleMediaPlayer: MediaPlayer
    private lateinit var challengeRepository: ChallengeRepository // Instancia del repositorio de retos
    private lateinit var pokemonRepository: PokemonRepository // Instancia del repositorio de Pokémon
    private var timer: CountDownTimer? = null
    private var spinning = false
    private var isPaused = false
    private var isMuted = false // TODO: Util para el boton de la toolbar de silenciar y desilenciar
    private var currentPosition = 0
    private var actDir = 0


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
        challengeRepository = ChallengeRepository(requireContext())
        pokemonRepository = PokemonRepository()
        mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bg_soundtrack)
        toolbarOptions()

        if (!isMuted) {
            playSoundtrack()
        }

        pressButton()
    }

    override fun onResume() {
        super.onResume()
        resumeSoundtrack()
        resumeBottleSound()
        updateVolumeIcon()
    }

    override fun onPause() {
        super.onPause()
        pauseSoundtrack()
        pauseBottleSound()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        releaseSoundtrack()
        releaseBottleSound()
    }

    private fun pauseSoundtrack() {
        // Pausar el audio cuando el fragmento no esté visible
        if (!isPaused) {
            currentPosition = mediaPlayer.currentPosition
            mediaPlayer.pause()  // Pausar la reproducción
            isPaused = true      // Marcar que está pausado
        }
    }

    private fun pauseBottleSound() {
        // Pausar el audio cuando el fragmento no esté visible
        if (!isPaused) {
            currentPosition = bottleMediaPlayer.currentPosition
            bottleMediaPlayer.pause()  // Pausar la reproducción
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

    private fun resumeBottleSound() {
        // Reanudar la música si estaba pausada
        if (isPaused and !isMuted) {
            bottleMediaPlayer.seekTo(currentPosition)
            bottleMediaPlayer.start()  // Reanudar la reproducción
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
        //mediaPlayer = MediaPlayer.create(requireContext(), R.raw.bg_soundtrack)

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
        binding.orangeButton.setOnClickListener()
        {
            try {
                if (!isMuted) {
                    pauseSoundtrack()
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
                binding.tvCountdown.visibility = View.INVISIBLE
                challengeDialog()
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
            setMusic()
        }

        informacion?.setOnClickListener {
            if(!isMuted){
                pauseSoundtrack()
            }
            findNavController().navigate(R.id.action_homeFragment_to_instructionsFragment)
        }

        agregar?.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_challengesFragment)
        }

        compartir?.setOnClickListener {
            val text1 = "App pico botella"
            val text2 = "¡Solo los valientes lo juegan!"
            val url = "https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"

            val shareText = "$text1\n$text2\n$url"

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, shareText)
                type = "text/plain"
            }
            startActivity(Intent.createChooser(shareIntent, "Compartir con"))
        }
    }

    private fun rateApp(){
        val calificar = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.nequi.MobileApp&hl=es_419&gl=es"))
        startActivity(calificar)
    }

    private fun setMusic(){
        isMuted = !isMuted
        if(isMuted){
            pauseSoundtrack()
        }else{
            resumeSoundtrack()
        }
        updateVolumeIcon()
    }

    private fun updateVolumeIcon() {
        val volumen = view?.findViewById<ImageView>(R.id.volumen)
        if (isMuted) {
            volumen?.setImageResource(R.drawable.ic_volume_off)
        } else {
            volumen?.setImageResource(R.drawable.ic_volume_up)
        }
    }

    private fun bottleSpin(onAnimationEnd: () -> Unit)
    {
        if(!spinning)
        {
            val newDir = actDir + Random.nextInt(2520 ,3600)

            val pivotX = binding.bottle.width / 2f
            val pivotY = binding.bottle.height / 2f

            val rotate = RotateAnimation(actDir.toFloat(), newDir.toFloat(), pivotX, pivotY).apply {
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

            actDir = newDir

            binding.bottle.startAnimation(rotate)
        }
    }

    private fun challengeDialog() {
        ChallengeDialog.showChallengeDialog(
            context = requireContext(),
            challengeRepository = challengeRepository,
            pokemonRepository = pokemonRepository,
            btnCloseText = "Cerrar",
            onDialogClose = {
                resumeSoundtrack()
            }
        )
    }
}