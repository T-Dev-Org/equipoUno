package com.example.equipouno.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.R
import com.example.equipouno.databinding.ItemChallengeBinding
import com.example.equipouno.model.Challenge
import android.view.animation.AnimationUtils
import com.example.equipouno.view.dialogue.CustomDialog
import com.example.equipouno.viewmodel.ChallengeViewModel

class ChallengeViewHolder(
    private val binding: ItemChallengeBinding,
    private val navController: NavController,
    private val viewModel: ChallengeViewModel
) : RecyclerView.ViewHolder(binding.root) {

    fun setItemChallenge(challenge: Challenge) {
        binding.tvDescription.text = challenge.description

        // Animación
        val pressAnimation =
            AnimationUtils.loadAnimation(binding.root.context, R.anim.scale_animation)

        // Botón Editar
        binding.imgbtnEdit.setOnClickListener {
            binding.imgbtnEdit.startAnimation(pressAnimation)

            CustomDialog.showCustomDialog(
                context = binding.root.context,
                title = "Editar Reto",
                hint = "Escriba el reto",
                positiveButtonText = "Guardar",
                negativeButtonText = "Cancelar",
                existingChallenge = challenge,
                onPositiveClick = { description ->
                    val updatedChallenge = challenge.copy(description = description)
                    viewModel.updateChallenge(updatedChallenge)
                },
                onNegativeClick = {}
            )
        }
    }
}