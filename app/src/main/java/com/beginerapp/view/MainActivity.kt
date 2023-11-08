package com.beginerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.beginerapp.databinding.ActivityMainBinding
import com.beginerapp.model.UserParentModel
import com.beginerapp.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        getAllData()
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
        body?.let{
            binding.recyclUserslist.visibility = View.VISIBLE
            binding.recyclUserslist.layoutManager = LinearLayoutManager(this)
            binding.recyclUserslist.adapter = UserListAdapter(it.results)
        }
    }
}