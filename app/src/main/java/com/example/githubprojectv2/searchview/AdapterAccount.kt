package com.example.githubprojectv2.searchview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprojectv2.R
import com.example.githubprojectv2.ViewHolderAccount
import com.example.githubprojectv2.api.ItemsItem
import com.example.githubprojectv2.databinding.AccountItemRowBinding

class AdapterAccount(
    private val listAccount : ArrayList<ItemsItem>
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
            with(listAccount[position]){
                Glide.with(binding.rowAvatar)
                    .load(avatarUrl)
                    .circleCrop()
                    .into(binding.rowAvatar)

                if(this.favorite)
                    Glide.with(binding.favIcon)
                        .load(R.drawable.ic_fav_fill)
                        .into(binding.favIcon)
                else
                    Glide.with(binding.favIcon)
                        .load(R.drawable.ic_fav_empty)
                        .into(binding.favIcon)

                binding.rowName.text = this.login
                holder.itemView.setOnClickListener{
                    onItemClickCallback.onItemClicked(listAccount[holder.adapterPosition].login)
                }
                binding.favIcon.setOnClickListener{
                    onItemClickCallback.onFavClicked(
                        listAccount[holder.adapterPosition]
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listAccount.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:String)
        fun onFavClicked(fav: ItemsItem)
    }
}

