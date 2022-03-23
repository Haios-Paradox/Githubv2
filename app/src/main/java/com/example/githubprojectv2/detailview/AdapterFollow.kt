package com.example.githubprojectv2.detailview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprojectv2.ViewHolderAccount
import com.example.githubprojectv2.api.ResponseFollowItem
import com.example.githubprojectv2.databinding.AccountItemRowBinding

class AdapterFollow(
    private val listFollow : ArrayList<ResponseFollowItem>
) : RecyclerView.Adapter<ViewHolderAccount>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderAccount {
        val binding = AccountItemRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolderAccount(binding)
    }

    override fun onBindViewHolder(holder: ViewHolderAccount, position: Int) {
        with(holder){
            with(listFollow[position]){
                Glide.with(binding.rowAvatar)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(binding.rowAvatar)
                binding.rowName.text = this.login
                holder.itemView.setOnClickListener{
                    onItemClickCallback.onItemClicked(listFollow[holder.adapterPosition].login)
                }

            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:String)
    }

    override fun getItemCount(): Int {
        return listFollow.size
    }

}