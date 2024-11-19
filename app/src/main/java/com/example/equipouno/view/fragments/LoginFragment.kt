package com.example.equipouno.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.text.InputType
import androidx.core.content.ContextCompat
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
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

        setupPasswordValidation()
        setupPasswordVisibility()
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

    private fun setupPasswordValidation() {
        binding.etPassword.setOnFocusChangeListener { _, hasFoces ->
            updatePasswordFieldState(hasFoces)
        }

        binding.etPassword.addTextChangedListener {
            val passwordInput = it.toString()
            if (passwordInput.length in 6..10) {
                clearErrorState()
            } else {
                showErrorState()
            }
        }
    }

    private fun updatePasswordFieldState(hasFocus: Boolean) {
        if (hasFocus) {
            binding.tilPassword.boxStrokeColor = resources.getColor(android.R.color.white, null)
        } else if (binding.etPassword.text.toString().length < 6) {
            showErrorState()
        } else {
            binding.tilPassword.boxStrokeColor = resources.getColor(android.R.color.darker_gray, null)
        }
    }

    private fun showErrorState() {
        binding.tilPassword.apply {
            error = "Mínimo 6 digitos"
            boxStrokeColor = resources.getColor(android.R.color.holo_red_light, null)
        }
    }

    private fun clearErrorState() {
        binding.tilPassword.apply {
            error = null
            boxStrokeColor = resources.getColor(android.R.color.white, null)
        }
    }

    private fun setupPasswordVisibility() {
        var isPasswordVisible = false

        binding.tilPassword.setEndIconOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                // Mostrar contraseña
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.tilPassword.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_closed)
            } else {
                // Ocultar contraseña
                binding.etPassword.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.tilPassword.endIconDrawable =
                    ContextCompat.getDrawable(requireContext(), R.drawable.ic_eye_open)
            }
            // Mantener el cursor al final del texto
            binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
        }

    }

}