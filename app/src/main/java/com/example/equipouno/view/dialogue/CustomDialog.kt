package com.example.equipouno.view.dialogue

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.equipouno.databinding.CustomDialogBinding

class CustomDialog {
    companion object {
        fun showCustomDialog(
            context: Context,
            title: String,
            hint: String,
            positiveButtonText: String,
            negativeButtonText: String,
            onPositiveClick: () -> Unit,
            onNegativeClick: () -> Unit
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = CustomDialogBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.white)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            // Configurar título, hint y nombres de botones
            binding.dialogTitle.text = title
            binding.etChallenge.hint = hint
            binding.btnSave.text = positiveButtonText
            binding.btnCancel.text = negativeButtonText

            // Configurar funcionalidad de los botones
            binding.btnSave.setOnClickListener {
                onPositiveClick()
                alertDialog.dismiss()
            }

            binding.btnCancel.setOnClickListener {
                onNegativeClick()
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }
}
