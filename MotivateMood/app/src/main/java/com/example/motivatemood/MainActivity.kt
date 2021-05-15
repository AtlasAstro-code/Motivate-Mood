package com.example.motivatemood

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.NotificationCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private var mDbAdapter: MyDBAdapter? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
            .setContentTitle("Mood Motivator::Action")
            .setContentText(randomStringInList())

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(2, mBuilder.build())
    }

    fun go2settings(view: View){
        var intent = Intent(this, SettingsActivity::class.java)
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

    private fun initializeViews() {


        add_action.setOnClickListener {
            mDbAdapter?.insertAction(action_name.text.toString())
            loadList()
        }
        delete_actions.setOnClickListener {
            mDbAdapter?.deleteAllActions()
            loadList()
        }

    }

    private fun initializeDatabase() {
        mDbAdapter = MyDBAdapter(this@MainActivity)
        mDbAdapter?.open()
    }

    private fun randomStringInList(): String? {
        val allActions: List<String>? = mDbAdapter?.selectAllActions()
        val randoNum = allActions?.let { Random.nextInt(it.size) }
        return randoNum?.let { allActions?.get(it) }
    }

    private fun loadList() {
        val allActions: List<String>? = mDbAdapter?.selectAllActions()
        val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, allActions!!)
        actions_list.adapter = adapter
    }
}