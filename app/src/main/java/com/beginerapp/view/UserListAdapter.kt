package com.beginerapp.view

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beginerapp.databinding.AdapterUserListBinding
import com.bumptech.glide.Glide
import com.beginerapp.model.Result

class UserListAdapter(private var results: List<Result>) :
    RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: AdapterUserListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            AdapterUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(results[position]) {

                "${this.name.title} ${this.name.first} ${this.name.last}".also { binding.txtName.text = it }

                Glide.with(binding.root)
                    .load(this.picture.medium)
                    .into(binding.imgProfile)

                binding.usersListParent.setOnClickListener {
                    val intent  = Intent(binding.root.context,UserDetailsActivity::class.java)
                    intent.putExtra("USER_DETAILS",this)
                    intent.putExtra("POS",position)
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }

    fun updateList(userdet:Result, pos: Int){
        this.results.get(pos).apply {
            name.title = userdet.name.title
            name.first = userdet.name.first
            name.last = userdet.name.last
        }
        notifyItemChanged(pos)
    }
}
