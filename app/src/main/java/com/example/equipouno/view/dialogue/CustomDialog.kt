package com.example.equipouno.view.dialogue

import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.equipouno.R
import com.example.equipouno.databinding.CustomDialogBinding

class CustomDialog {
    companion object{
        fun showCustomDialog(
            context: Context
        ) {
            val inflater = LayoutInflater.from(context)
            val binding = CustomDialogBinding.inflate(inflater)

            val alertDialog = AlertDialog.Builder(context).create()
            alertDialog.window?.setBackgroundDrawableResource(android.R.color.white)
            alertDialog.setCancelable(false)
            alertDialog.setView(binding.root)

            binding.btnAceptar.setOnClickListener {
                // TODO
                Toast.makeText(context, "TODO",Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }

            binding.btnCancelar.setOnClickListener {
                // TODO
                Toast.makeText(context, "TODO",Toast.LENGTH_SHORT).show()
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }
}