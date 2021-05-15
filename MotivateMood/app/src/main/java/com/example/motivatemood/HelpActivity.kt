package com.example.motivatemood

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity

class HelpActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_help)
    }
    fun go2settings(view: View){
        var intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun go2quote(view: View){
        var intent = Intent(this, QuoteActivity::class.java)
        startActivity(intent)
    }

    fun go2main(view: View){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}