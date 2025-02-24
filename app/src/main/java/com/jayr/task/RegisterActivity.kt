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

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        auth = Firebase.auth
        // Reference EditText and Button
        val emailInput = findViewById<EditText>(R.id.email)
        val passwordInput = findViewById<EditText>(R.id.password)
        val btnRegister = findViewById<Button>(R.id.registerBtn)
        val progressBar = findViewById<ProgressBar>(R.id.progressIndicator)
        val toLoginPage = findViewById<TextView>(R.id.loginPage)

        btnRegister.setOnClickListener() {
            registerUser(emailInput, passwordInput, progressBar)
        }

        toLoginPage.setOnClickListener() {
            navigateToLogin()
        }

    }
private fun navigateToLogin(){
    val intent = Intent(this, Authentication::class.java)
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

private fun registerUser(userEmail: EditText?, userPassword: EditText?, progressBar: ProgressBar,){
    val email = userEmail?.text?.trim().toString()
    val password = userPassword?.text?.trim().toString()
    showProgressIndicator(progressBar, true)

    if(email.isNotEmpty() && password.isNotEmpty()){
    auth.createUserWithEmailAndPassword(email, password)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {

                showProgressIndicator(progressBar, false)
                Toast.makeText(
                    baseContext,
                    "Success!",
                    Toast.LENGTH_SHORT,
                ).show()
                navigateToLogin()
            } else {
                showProgressIndicator(progressBar, false)
                Toast.makeText(
                    baseContext,
                    "Authentication failed. ${task.exception}",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }}else{
        showProgressIndicator(progressBar, false)
        Toast.makeText(
            baseContext,
            "Empty fields present!",
            Toast.LENGTH_SHORT,
        ).show()
    }
}

}