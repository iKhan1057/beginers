package com.beginerapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.beginerapp.R
import com.bumptech.glide.Glide

class UserDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)

//        "${this.name.title} ${this.name.first} ${this.name.last}".also { binding.txtName.text = it }
//        Glide.with(binding.root)
//            .load(this.picture.thumbnail)
//            .into(binding.imgProfile)
//                binding.txtGender.text = this.gender
//                binding.txtEmail.text = this.email
//                binding.txtMobile.text = this.phone
//                binding.txtDob.text = this.dob.date
//                binding.txtAge.text = this.dob.age.toString()
    }
}