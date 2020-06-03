package com.example.storytime


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


class LoginActivity : AppCompatActivity() {
    lateinit var readerEmail: EditText
    lateinit var readerPassword: EditText
    lateinit var btnLogin: Button
    lateinit var fgpwd:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        fgpwd = findViewById(R.id.fgpwd)
        fgpwd.setOnClickListener{
            val myIntent = Intent(baseContext, ForgotPasswordActivity::class.java)
            startActivity(myIntent)
        }
        readerEmail = findViewById(R.id.loginEmail)
        readerPassword = findViewById(R.id.loginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnLogin.setOnClickListener{
            getLoginData()
        }

    }
    private fun getLoginData() {
        val readerEmail = readerEmail.text.toString().trim()
        var lflag = false
          if(readerEmail.isEmpty()){
              Toast.makeText(applicationContext,"Enter correct username/Email", Toast.LENGTH_LONG).show()
              lflag=true
          }
        val readerPassword = readerPassword.text.toString().trim()
        if (readerPassword.isEmpty()) {
            Toast.makeText(applicationContext,"Enter correct password",Toast.LENGTH_LONG).show()
            lflag=true
        }
        if(!lflag){
        val database = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.getReference("reader")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {

                if(p0!!.exists()){
                    //P0!! to address null value
                    var flag = false
                    for(item in p0.children) {
                        val emailOk = item.child("email").getValue().toString().equals(readerEmail)
                        val pwdOk = item.child("password").getValue().toString().equals(readerPassword)
                        if (emailOk && pwdOk) {
                            flag = true
                            Toast.makeText(
                                applicationContext,
                                "Login Success",
                                Toast.LENGTH_LONG
                            ).show()
                            val prefs =
                                getSharedPreferences("MyApp", Context.MODE_PRIVATE)
                            prefs.edit().putString("username", item.child("name").getValue().toString()).commit()
                            val myIntent = Intent(baseContext, DashboardActivity::class.java)
                            startActivity(myIntent)
                            return
                        }
                    }
                    if(!flag){
                        Toast.makeText(
                            applicationContext,
                            "Enter correct credentials",
                            Toast.LENGTH_LONG
                        ).show()
                        val myIntent = Intent(baseContext, LoginActivity::class.java)
                        startActivity(myIntent)
                    }
                }
            }
            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


    } else {
            Toast.makeText(
                applicationContext,
                "Enter correct credentials",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}