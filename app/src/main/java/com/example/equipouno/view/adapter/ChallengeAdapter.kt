package com.example.equipouno.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.databinding.ItemChallengeBinding
import com.example.equipouno.model.Challenge
import com.example.equipouno.view.viewholder.ChallengeViewHolder
import com.example.equipouno.viewmodel.ChallengeViewModel

class ChallengeAdapter(
    private val listChallenge: List<Challenge>,
    private val navController: NavController,
    private val viewModel: ChallengeViewModel // Añade el ViewModel
) : RecyclerView.Adapter<ChallengeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        val binding = ItemChallengeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChallengeViewHolder(binding, navController, viewModel) // Pasa el ViewModel
    }

    override fun getItemCount(): Int {
        return listChallenge.size
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val challenge = listChallenge[position]
        holder.setItemChallenge((challenge))
    }
}