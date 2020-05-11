package com.example.storytime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this;
        btnLogin.run {
            ;
            btnLogin.setOnClickListener({
                if(readerEmail.text.toString().length>0 && readerPassword.text.toString().length>0){
                    var reader = Reader("Bingo",readerEmail.text.toString(),readerPassword.text.toString())
                    var db = DBHandler(context)
                    db.insertData(reader)
                } else {
                    Toast.makeText(context,"Please fill corect input",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
