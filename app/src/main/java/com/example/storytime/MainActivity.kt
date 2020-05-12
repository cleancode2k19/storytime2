package com.example.storytime

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val context = this;
        btnSignup.run {
            ;
            btnSignup.setOnClickListener({
                if(readerEmail.text.toString().length>0 && readerPassword.text.toString().length>0){
                    if(readerPassword.text.toString()==readerRepeatPassword.text.toString() && accept.isChecked) {
                        var reader = Reader(
                            "Bingo",
                            readerEmail.text.toString(),
                            readerPassword.text.toString()
                        )
                        var db = DBHandler(context)
                        db.insertData(reader)
                    } else{
                        Toast.makeText(context,"Please fill corect password or accept the license aggrement",Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context,"Please fill corect input",Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
