package com.example.equipouno.view.dialogue

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.example.equipouno.databinding.ChallengeDialogBinding
import com.example.equipouno.repository.ChallengeRepository
import com.example.equipouno.repository.PokemonRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChallengeDialog {
    companion object {
        fun showChallengeDialog(
            context: Context,
            challengeRepository: ChallengeRepository,
            pokemonRepository: PokemonRepository,
            btnCloseText: String,
            onDialogClose: () -> Unit
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = ChallengeDialogBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.setCancelable(false)
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            alertDialog.setView(binding.root)

            binding.btnClose.text = btnCloseText

            // Cargar imagen de Pokémon de manera asíncrona y mostrarla en el ImageView
            CoroutineScope(Dispatchers.Main).launch {
                val pokemonImageUrl = pokemonRepository.getRandomPokemon()
                Glide.with(context)
                    .load(pokemonImageUrl.sprites.frontDefault)
                    .into(binding.ivChallengeImage)

                // Cargar reto y mostrarlo en el TextView
                val challengeDescription = challengeRepository.getRandomChallenge()?.description
                binding.selectedChallenge.text = challengeDescription
            }

            // Configurar el botón para cerrar el diálogo
            binding.btnClose.setOnClickListener {
                onDialogClose()
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }
}
