package com.example.equipouno.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.equipouno.databinding.ItemChallengeBinding
import com.example.equipouno.model.Challenge

class ChallengeViewHolder(binding: ItemChallengeBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root){
        val bindingItem = binding
        //val navController = navController
        fun setItemChallenge(challenge: Challenge) {
            bindingItem.tvDescription.text = challenge.description

            /**bindingItem.cardViewChallenge.setOnClickListener {
                val bundle = Bundle()
                bundle.putSerializable("key", challenge)

            }*/
        }
    }