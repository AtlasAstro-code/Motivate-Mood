package com.example.motivatemood

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_quote.*
import kotlin.random.Random

class QuoteActivity : AppCompatActivity() {
    private var mDbAdapter: MyDBAdapterQ? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "my_channel_02"

        val name = getString(R.string.abc_action_bar_home_description)

        val description = getString(R.string.abc_action_bar_home_description)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val mChannel = NotificationChannel(id, name, importance)

        mChannel.description =description
        mChannel.enableLights(true)

        mChannel.lightColor = Color.RED
        mChannel.enableVibration(true)
        mNotificationManager.createNotificationChannel(mChannel)

        initializeViews()
        initializeDatabase()
        loadList()
    }



    fun testNotification(view: View){
        val CHANNEL_ID = "my_channel_02"
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_motivate_foreground)
            .setContentTitle("Mood Motivator:: Quote")
            .setContentText(randomStringInList())

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(2, mBuilder.build())
    }

    fun go2settings(view: View){
        var intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun go2main(view: View){
        var intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }


    fun go2help(view: View){
        var intent = Intent(this, HelpActivity::class.java)
        startActivity(intent)
    }

    private fun initializeViews() {


        add_quote.setOnClickListener {
            mDbAdapter?.insertQuote(quote_name.text.toString())
            loadList()
        }
        delete_quotes.setOnClickListener {
            mDbAdapter?.deleteAllQuotes()
            loadList()
        }

    }

    private fun initializeDatabase() {
        mDbAdapter = MyDBAdapterQ(this@QuoteActivity)
        mDbAdapter?.open()
    }

    private fun randomStringInList(): String? {
        val allQuotes: List<String>? = mDbAdapter?.selectAllQuotes()
        val randoNum = allQuotes?.let { Random.nextInt(it.size) }
        return randoNum?.let { allQuotes?.get(it) }
    }

    private fun loadList() {
        val allQuotes: List<String>? = mDbAdapter?.selectAllQuotes()
        val adapter = ArrayAdapter(this@QuoteActivity, android.R.layout.simple_list_item_1, allQuotes!!)
        quotes_list.adapter = adapter
    }
}