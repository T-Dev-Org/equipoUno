package com.example.equipouno.view.viewholder

import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.R
import com.example.equipouno.databinding.ItemChallengeBinding
import com.example.equipouno.model.Challenge
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.equipouno.view.dialogue.CustomDialog
import com.example.equipouno.view.dialogue.StandardDialog.Companion.showDialog

class ChallengeViewHolder(
    binding: ItemChallengeBinding,
    navController: NavController) :
    RecyclerView.ViewHolder(binding.root){
        val bindingItem = binding
        //val navController = navController
        fun setItemChallenge(challenge: Challenge) {
            bindingItem.tvDescription.text = challenge.description

            // Cargar la animación
            val pressAnimation = AnimationUtils.loadAnimation(bindingItem.root.context, R.anim.scale_animation)

            // Agregar animación al pulsar el botón de editar
            bindingItem.imgbtnEdit.setOnClickListener {
                bindingItem.imgbtnEdit.startAnimation(pressAnimation)
                // Acción para editar el reto
                // TODO: Crear funcionalidad de edicion
                CustomDialog.showCustomDialog(
                    context = bindingItem.root.context,
                    title = "Editar Reto",
                    hint = "Escriba el reto",
                    positiveButtonText = "Guardar",
                    negativeButtonText = "Cancelar",
                    // TODO: Crear funcionalidad para guardar reto
                    onPositiveClick = {
                        Toast.makeText(bindingItem.root.context, "Modification not implemented yet", Toast.LENGTH_SHORT).show()
                    },
                    onNegativeClick = {}
                )
            }

            // Agregar animación al pulsar el botón de eliminar
            bindingItem.imgbtnDelete.setOnClickListener {
                bindingItem.imgbtnDelete.startAnimation(pressAnimation)
                // Acción para eliminar el reto
                showDialog(bindingItem.root.context).show()
            }
        }
    }