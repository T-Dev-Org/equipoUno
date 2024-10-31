package com.example.equipouno.view.dialogue

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.equipouno.R
import com.example.equipouno.databinding.DialogConfirmationBinding

class ConfirmationDialog {
    companion object {
        fun showConfirmationDialog(
            context: Context,
            challengeDescription: String,
            onConfirm: () -> Unit,
            onCancel: () -> Unit
        ) {
            val binding: DialogConfirmationBinding = DataBindingUtil.inflate(
                LayoutInflater.from(context),
                R.layout.dialog_confirmation,
                null,
                false
            )

            val alertDialog = AlertDialog.Builder(context)
                .setView(binding.root)
                .setCancelable(false)
                .create()

            binding.dialogChallengeDescription.text = challengeDescription

            binding.textNo.setOnClickListener {
                alertDialog.dismiss()
                onCancel()
            }

            binding.textSi.setOnClickListener {
                alertDialog.dismiss()
                onConfirm()
            }

            alertDialog.show()
        }
    }
}
