package com.example.equipouno.view.dialogue

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.equipouno.R
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

            // Deshabilitar btnSave y cambiar color inicialmente
            binding.btnSave.isEnabled = false
            binding.btnSave.setBackgroundColor(ContextCompat.getColor(context, R.color.gray_700))

            // Agregar TextWatcher para habilitar/deshabilitar btnSave y cambiar color según el texto
            binding.etChallenge.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    val isNotEmpty = !s.isNullOrEmpty()
                    binding.btnSave.isEnabled = isNotEmpty
                    val color = if (isNotEmpty) R.color.orange_700 else R.color.gray_700
                    binding.btnSave.setBackgroundColor(ContextCompat.getColor(context, color))
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            })

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
