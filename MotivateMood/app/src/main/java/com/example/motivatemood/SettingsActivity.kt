package com.example.motivatemood

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        datas()
            var myPref = getSharedPreferences("momoPref", 0)
            var editor : SharedPreferences.Editor=(myPref as SharedPreferences).edit()
        checkBox.setOnCheckedChangeListener { _, _ ->
            editor.putBoolean("quotes", checkBox.isChecked)

            editor.commit()
        }

        checkBox2.setOnCheckedChangeListener { _, _ ->
            editor.putBoolean("actions", checkBox2.isChecked)
            editor.commit()
        }


            datas()
    }

    fun datas(){
        var databack : SharedPreferences = getSharedPreferences("momoPref", 0)
        if(databack.getBoolean("actions", true)) {
            Toast.makeText(this, "Saved Success", Toast.LENGTH_LONG).show()
            checkBox2.isChecked=true
        }
        if(databack.getBoolean("quotes", true)) {
            Toast.makeText(this, "Saved Success", Toast.LENGTH_LONG).show()
            checkBox.isChecked=true
        }
    }

    fun go2main(view: View){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun go2quote(view: View){
        var intent = Intent(this, QuoteActivity::class.java)
        startActivity(intent)
    }

    fun go2help(view: View){
        var intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }
}