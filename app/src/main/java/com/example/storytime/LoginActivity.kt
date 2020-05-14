package com.example.storytime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.readerEmail
import kotlinx.android.synthetic.main.activity_main.readerPassword
import kotlinx.android.synthetic.main.signin.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signin)
        val context = this
        btnLogin.run {
            btnLogin.setOnClickListener({
                if(readerEmail.text.toString().length>0 && readerPassword.text.toString().length>0){
                        val reader = Reader(
                            "",
                            readerEmail.text.toString(),
                            readerPassword.text.toString()
                        )
                        var db = DBHandler(context)
                        val flag = db.readSigninData(reader)
                        if(flag) {
                            val myIntent = Intent(baseContext, DashboardActivity::class.java)
                            startActivity(myIntent)
                        } else{
                            val myIntent = Intent(baseContext, LoginActivity::class.java)
                            startActivity(myIntent)
                        }

                } else {
                    Toast.makeText(context,"Please fill corect input", Toast.LENGTH_SHORT).show()
                    val myIntent = Intent(baseContext, LoginActivity::class.java)
                    startActivity(myIntent)
                }
            })
        }
    }
}
