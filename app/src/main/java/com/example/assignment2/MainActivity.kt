package com.example.assignment2

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.adapters.ActivityAdapter
import com.example.assignment2.databinding.ActivityMainBinding
import com.example.assignment2.models.Activity
import com.example.assignment2.services.ActivityService
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var lastMenuItem: MenuItem
    private lateinit var service: ActivityService
    private lateinit var activityView: RecyclerView

    private var activities: ArrayList<Activity> = arrayListOf()
    private var instance: MainActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        getSupportActionBar()?.setTitle("Random")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.boredapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()

        service = retrofit.create(ActivityService::class.java)
        getActivities("Random")

        activityView = findViewById(R.id.activity_list)
        activityView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
    }

    fun getActivities(category: String) {

        activities = arrayListOf()

        for (i in 1..15)
            service
                .getActivity(category.lowercase(Locale.getDefault()).takeIf { it != "random" })
                .enqueue(object : Callback<Activity> {

                    override fun onResponse(call: Call<Activity>, response: Response<Activity>) {
                        if (response.isSuccessful) {
                            response.body()?.let { activities.add(it) }
                            activityView.adapter = ActivityAdapter(instance, activities)
                        }
                    }

                    override fun onFailure(call: Call<Activity>, t: Throwable) {

                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        lastMenuItem = menu.findItem(R.id.random)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        lastMenuItem.setVisible(true)
        getSupportActionBar()?.setTitle(item.title)
        item.setVisible(false)
        lastMenuItem = item
        getActivities(item.title.toString())

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        menu?.findItem(R.id.send_to_friend)?.setOnMenuItemClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Send to Friend")
            builder.setMessage("Are you sure you want to send this to a friend?")
            builder.setPositiveButton("Yes") { _, _ ->
                v?.setBackgroundColor(Color.LTGRAY)
                (activityView.adapter as ActivityAdapter).sent = (activityView.adapter as ActivityAdapter).sent.plus(v?.findViewById<TextView>(R.id.key_text)?.text.toString())
            }
            builder.setNegativeButton("No") { _, _ ->

            }

            val dialog: AlertDialog = builder.create()
            dialog.show()

            true
        }
    }
}