package com.example.equipouno.view.dialogue

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class StandardDialog {
    companion object{
        fun showDialog(context: Context): AlertDialog {

            val builder = AlertDialog.Builder(context)
            builder.setCancelable(false)
            builder.setTitle("Borrar")
                .setPositiveButton("SI") { dialog, _ ->
                    Toast.makeText(context,"SI", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
                .setNegativeButton("NO") { dialog, _ ->
                    Toast.makeText(context,"NO", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
                }
            return builder.create()
        }
    }
}