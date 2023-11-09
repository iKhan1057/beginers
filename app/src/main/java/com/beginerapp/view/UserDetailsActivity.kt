package com.beginerapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.beginerapp.databinding.ActivityUserDetailsBinding
import com.beginerapp.model.Result
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class UserDetailsActivity : AppCompatActivity() {
    private var pos: Int = 0
    lateinit var binding: ActivityUserDetailsBinding
    lateinit var dataUsers: Result

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.hasExtra("USER_DETAILS")) {
                showData(getData(it))
            }
            if (it.hasExtra("POS"))
                intent.getIntExtra("POS", 0).also { pos = it }
        }

        binding.fabEdit.setOnClickListener {
            val intent = Intent(this, EditUserActivity::class.java)
            intent.putExtra("USER_DETAILS", dataUsers)
            intent.putExtra("POS", pos)
            startActivity(intent)
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction("UPDATE_USER_DETAILS")
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(broadcastRev, intentFilter)
    }

    private val broadcastRev = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {
            when (intent?.action) {
                "UPDATE_USER_DETAILS" -> {
                    if (intent.hasExtra("USER_DETAILS")) {
                        showData(getData(intent))
                    }
                }
            }
        }
    }

    private fun showData(data: Result) {
        dataUsers = data
        "${data.name.title} ${data.name.first} ${data.name.last}".also { binding.txtName.text = it }
        Glide.with(this)
            .load(data.picture.large)
            .into(binding.imgProfile)
        "Gender: ${data.gender}".also { binding.txtGender.text = it }
        binding.txtEmail.text = data.email
        ("Contact: " + data.phone).also { binding.txtMobile.text = it }
        ("Date Of Birth: " + data.dob.date.toDate()
            .formatTo("dd MMM yyyy")).also { binding.txtDob.text = it }
        ("Age: " + data.dob.age.toString()).also { binding.txtAge.text = it }
    }

    private fun getData(it: Intent): Result {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getSerializableExtra("USER_DETAILS", Result::class.java)!!
        else
            it.getSerializableExtra("USER_DETAILS") as Result
    }

    fun String.toDate(
        dateFormat: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        timeZone: TimeZone = TimeZone.getTimeZone("UTC")
    ): Date {
        val parser = SimpleDateFormat(dateFormat, Locale.getDefault())
        parser.timeZone = timeZone
        return parser.parse(this)
    }

    fun Date.formatTo(dateFormat: String, timeZone: TimeZone = TimeZone.getDefault()): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        formatter.timeZone = timeZone
        return formatter.format(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastRev)
    }
}