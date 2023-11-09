package com.beginerapp.view

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.beginerapp.R
import com.beginerapp.databinding.ActivityEditUserBinding
import com.beginerapp.model.Result

class EditUserActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditUserBinding
    lateinit var userDetails: Result
    private var pos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.let {
            if (it.hasExtra("USER_DETAILS")) {
                showData(getData(it))
            }
            if (it.hasExtra("POS"))
                intent.getIntExtra("POS", 0).also { pos = it }
        }
    }

    private fun showData(data: Result) {
        userDetails = data
        binding.editEtTitle.setText(userDetails.name.title)
        binding.editEtFirstName.setText(userDetails.name.first)
        binding.editEtLastName.setText(userDetails.name.last)
        binding.editBtnSubmit.setOnClickListener {
            userDetails.apply {
                this.name.title = binding.editEtTitle.text.toString()
                this.name.first = binding.editEtFirstName.text.toString()
                this.name.last = binding.editEtLastName.text.toString()
            }
            val intent = Intent()
            intent.action = "UPDATE_USER_DETAILS"
            intent.putExtra("USER_DETAILS", userDetails)
            intent.putExtra("POS", pos)
            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
            finish()
        }
    }


    private fun getData(it: Intent): Result {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getSerializableExtra("USER_DETAILS", Result::class.java)!!
        else
            it.getSerializableExtra("USER_DETAILS") as Result
    }
}