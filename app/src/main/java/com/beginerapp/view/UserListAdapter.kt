package com.beginerapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.beginerapp.R
import com.beginerapp.databinding.AdapterUserListBinding
import com.beginerapp.model.Result
import com.bumptech.glide.Glide

class UserListAdapter(private val results: List<Result>) :
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

                when ("female" == this.gender) {
                    true -> binding.usersListParent.setBackgroundColor(
                        binding.usersListParent.context.resources.getColor(
                            R.color.green,
                            null
                        )
                    )

                    false -> binding.usersListParent.setBackgroundColor(
                        binding.usersListParent.context.resources.getColor(
                            R.color.red,
                            null
                        )
                    )
                }

                "${this.name.title} ${this.name.first} ${this.name.last}".also {
                    binding.txtName.text = it
                }

                Glide.with(binding.root)
                    .load(this.picture.thumbnail)
                    .into(binding.imgProfile)
            }
        }
    }

}
