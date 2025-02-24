package com.jayr.task

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class Authentication : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_authentication)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        // Reference EditText and Button
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val btnLogin = findViewById<Button>(R.id.loginBtn)
        val progressBar = findViewById<ProgressBar>(R.id.progressIndicator)
        val toRegisterPage = findViewById<TextView>(R.id.registerPage)

        btnLogin.setOnClickListener() {
            login(emailInput, passwordInput, progressBar)
        }

        toRegisterPage.setOnClickListener() {
            navigateToRegister()
        }
    }
   private fun login(email:EditText?, password:EditText?, progressBar: ProgressBar, ){
       val userEmail = email?.text?.trim().toString()
       val userPassword = password?.text?.trim().toString()
       showProgressIndicator(progressBar, true)
       if(userEmail.isNotEmpty() && userPassword.isNotEmpty()){
           auth.signInWithEmailAndPassword(userEmail, userPassword)
               .addOnCompleteListener(this) { task ->
                   if (task.isSuccessful) {

                       showProgressIndicator(progressBar, false)
                       Toast.makeText(
                           baseContext,
                           "Success!",
                           Toast.LENGTH_SHORT,
                       ).show()
                       navigateToHome()
                   } else {

                       showProgressIndicator(progressBar, false)
                       Toast.makeText(
                           baseContext,
                           "Authentication failed. ${task.exception}",
                           Toast.LENGTH_SHORT,
                       ).show()
                   }
               }
       }
       else{
           showProgressIndicator(progressBar, false)
           Toast.makeText(
               baseContext,
               "Empty fields present",
               Toast.LENGTH_SHORT,
           ).show()
       }

    }

    private fun navigateToRegister(){
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
    }

    private fun navigateToHome(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    private fun showProgressIndicator(progressBar: ProgressBar, state:Boolean){
        if(state){
            progressBar.visibility = View.VISIBLE
        }else{
            progressBar.visibility = View.GONE

        }
    }
    fun showPopUp(message:String, view: View){
        Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .setAnchorView(R.id.fab).show()
    }

    fun registerUser(userEmail:EditText?, userPassword:EditText?, progressBar: ProgressBar,){
        val email = userEmail?.text?.trim().toString()
        val password = userPassword?.text?.trim().toString()
        showProgressIndicator(progressBar, true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    showProgressIndicator(progressBar, false)
                    Toast.makeText(
                        baseContext,
                        "Success!",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    showProgressIndicator(progressBar, false)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed. ${task.exception}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }
}