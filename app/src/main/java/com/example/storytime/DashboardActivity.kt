package com.example.storytime

import android.R
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.dashboard_story.*


class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard_story)
        val context = this;

        val t2 = findViewById<View>(R.id.insertstorydata) as TextView
        t2.movementMethod = LinkMovementMethod.getInstance()

        insertstorydata.run {
            insertstorydata.setOnClickListener({

                val myIntent = Intent(baseContext, LoginActivity::class.java)
                startActivity(myIntent)

            })
        }
    }
}
