    package com.example.equipouno.view
    import android.content.Context
    import android.content.Intent
    import android.content.SharedPreferences
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.text.InputType
    import android.view.View
    import android.widget.Toast
    import androidx.activity.viewModels
    import androidx.core.content.ContextCompat
    import androidx.core.widget.addTextChangedListener
    import androidx.databinding.DataBindingUtil
    import com.example.equipouno.R
    import com.example.equipouno.databinding.ActivityLoginBinding
    import com.example.equipouno.model.UserRequest
    import com.example.equipouno.viewmodel.LoginViewModel
    import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class LoginActivity : AppCompatActivity() {
        private lateinit var binding: ActivityLoginBinding
        private val loginViewModel: LoginViewModel by viewModels()
        private lateinit var sharedPreferences: SharedPreferences
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = DataBindingUtil.setContentView(this,R.layout.activity_login)

            sharedPreferences = getSharedPreferences("shared", Context.MODE_PRIVATE)
            sesion()
            setup()
            viewModelObservers()
        }
        private fun viewModelObservers() {
            observerIsRegister()
        }
        private fun observerIsRegister() {
            loginViewModel.isRegister.observe(this) { userResponse ->
                if (userResponse.isRegister) {
                    Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
                    sharedPreferences.edit().putString("email",userResponse.email).apply()
                    goToHome()
                } else {
                    Toast.makeText(this, userResponse.message, Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun setup() {
            binding.tvRegister.setOnClickListener {
                registerUser()
            }

            binding.btnLogin.setOnClickListener {
                loginUser()
            }

            setupValidationObservers()
            setupFieldListeners()
            setupPasswordVisibility()
        }

        private fun registerUser() {
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            val userRequest = UserRequest(email, pass)

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                loginViewModel.registerUser(userRequest)
            } else {
                Toast.makeText(this, "Campos vacíos", Toast.LENGTH_SHORT).show()
            }

        }
        private fun goToHome(){
            val intent = Intent (this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        private fun loginUser(){
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            loginViewModel.loginUser(email,pass){ isLogin ->
                if (isLogin){
                    sharedPreferences.edit().putString("email",email).apply()
                    goToHome()
                }else {
                    Toast.makeText(this, "Login incorrecto", Toast.LENGTH_SHORT).show()
                }
            }
        }
        private fun sesion(){
            val email = sharedPreferences.getString("email",null)
            loginViewModel.sesion(email){ isEnableView ->
                if (isEnableView){
                    binding.clContainer.visibility = View.INVISIBLE
                    goToHome()
                }
            }
        }


        private fun setupValidationObservers() {
            loginViewModel.isEmailValid.observe(this) { isValid ->
                if (!isValid) {
                    binding.tilEmail.error = "Correo inválido"
                } else {
                    binding.tilEmail.error = null
                }
            }

            loginViewModel.isPasswordValid.observe(this) { isValid ->
                if (!isValid) {
                    binding.tilPassword.error = "Mínimo 6 caracteres y máximo 10"
                } else {
                    binding.tilPassword.error = null
                }
            }

            loginViewModel.isFormValid.observe(this) { isValid ->
                binding.btnLogin.isEnabled = isValid
                binding.tvRegister.isEnabled = isValid
            }
        }

        private fun setupFieldListeners() {
            binding.etEmail.addTextChangedListener { editable ->
                loginViewModel.validateEmail(editable.toString())
            }

            binding.etPassword.addTextChangedListener { editable ->
                loginViewModel.validatePassword(editable.toString())
            }
        }

        private fun setupPasswordVisibility() {
            var isPasswordVisible = false

            binding.tilPassword.setEndIconOnClickListener {
                isPasswordVisible = !isPasswordVisible
                if (isPasswordVisible) {
                    binding.etPassword.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    binding.tilPassword.endIconDrawable =
                        ContextCompat.getDrawable(this, R.drawable.ic_eye_closed)
                } else {
                    binding.etPassword.inputType =
                        InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    binding.tilPassword.endIconDrawable =
                        ContextCompat.getDrawable(this, R.drawable.ic_eye_open)
                }
                binding.etPassword.setSelection(binding.etPassword.text?.length ?: 0)
            }
        }
    }