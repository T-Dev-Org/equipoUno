package com.example.equipouno.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.equipouno.R
import com.example.equipouno.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    private fun setup() {
        binding.tvRegister.setOnClickListener{
            registerUser()
        }

        binding.btnLogin.setOnClickListener{
            loginUser()
        }
    }

    private fun registerUser(){
        // TODO
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        Toast.makeText(context, "TODO: usuario registrado", Toast.LENGTH_SHORT).show()
    }

    private fun loginUser(){
        // TODO
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        Toast.makeText(context, "TODO: usuario logeado", Toast.LENGTH_SHORT).show()
    }

}