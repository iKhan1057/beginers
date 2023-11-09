package com.beginerapp.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.beginerapp.databinding.ActivityMainBinding
import com.beginerapp.model.Result
import com.beginerapp.model.UserParentModel
import com.beginerapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var userlist: List<Result>
    lateinit var userlistAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getAllData()

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
                        val userdetails = getData(intent)
                        if (intent.hasExtra("POS")) {
                            userlistAdapter.updateList(userdetails, intent.getIntExtra("POS",0))
                        }
                    }
                }
            }
        }
    }

    private fun getData(it: Intent): Result {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            it.getSerializableExtra("USER_DETAILS", Result::class.java)!!
        else
            it.getSerializableExtra("USER_DETAILS") as Result
    }

    private fun getAllData() {
        binding.progress.visibility = View.VISIBLE
        val call = ApiClient.apiService.getAllUserDetails()
        call.enqueue(object : Callback<UserParentModel> {
            override fun onResponse(
                call: Call<UserParentModel>,
                response: Response<UserParentModel>
            ) {
                binding.progress.visibility = View.GONE
                Log.d("LIST", response.body().toString())
                showData(response.body())
            }

            override fun onFailure(call: Call<UserParentModel>, t: Throwable) {
                t.localizedMessage?.let { Log.d("ERROR", it.toString()) }
                binding.progress.visibility = View.GONE
            }
        })
    }

    private fun showData(body: UserParentModel?) {
        body?.let {
            userlist = it.results
            binding.recyclUserslist.visibility = View.VISIBLE
            binding.recyclUserslist.layoutManager = LinearLayoutManager(this)
            userlistAdapter = UserListAdapter(it.results)
            binding.recyclUserslist.adapter = userlistAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(broadcastRev)
    }
}