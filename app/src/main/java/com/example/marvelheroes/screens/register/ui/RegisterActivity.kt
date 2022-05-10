package com.example.marvelheroes.screens.register.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.marvelheroes.databinding.ActivityRegisterBinding
import com.example.marvelheroes.screens.home.ui.HomeActivity
import com.example.marvelheroes.screens.register.model.UserAccount
import com.example.marvelheroes.screens.register.utils.RegisterConstants
import com.example.marvelheroes.screens.register.viewmodel.ViewModelRegister
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var viewModel: ViewModelRegister

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onStart() {
        super.onStart()

        binding.button2.setOnClickListener{
            val user = UserAccount(
                fullName=  binding.textInputEditText4.text.toString().trim(),
                email = binding.textInputEditText5.text.toString().trim(),
                password = binding.textInputEditText6.text.toString().trim(),
                confirmPassword = binding.textInputEditText7.text.toString().trim(),

                )
          validateRegisterData(user)
        }

        viewModel.createUser(UserAccount(email = "tai2@gmail.com", password = "12345678", confirmPassword = "12345678", fullName = "TESTE"))

        viewModel.booleanCreateUserLiveData.observe(this){
            if(it){
            startActivity(Intent(this@RegisterActivity, HomeActivity::class.java))
        }}
    }

    private fun validateRegisterData(user: UserAccount) {
        when (viewModel.validateRegisterData(userAccount = user)) {
            RegisterConstants.NAME_EMPTY -> {
                Toast.makeText(this, "Enter your name...", Toast.LENGTH_SHORT).show()
            }
            RegisterConstants.EMAIL_INVALID -> {
                Toast.makeText(this, "Invalid Email Pattern...", Toast.LENGTH_SHORT).show()
            }
            RegisterConstants.PASSWORD_EMPTY -> {
                Toast.makeText(this, "Enter password...", Toast.LENGTH_SHORT).show()
            }
            RegisterConstants.CONFIRM_PASSWORD_EMPTY -> {
                Toast.makeText(this, "Confirm password...", Toast.LENGTH_SHORT).show()
            }
            RegisterConstants.PASSWORD_NOT_MATCH -> {
                Toast.makeText(this, "Password doesn't match...", Toast.LENGTH_SHORT).show()
            }
            else -> {
                createUserAccount(user)
            }
        }
    }

    private fun createUserAccount(user: UserAccount) {
     val resultCreateUser =  viewModel.createUser(user)

    }
}