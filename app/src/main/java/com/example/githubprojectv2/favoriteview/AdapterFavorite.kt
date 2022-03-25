package com.example.githubprojectv2.favoriteview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubprojectv2.R
import com.example.githubprojectv2.ViewHolderAccount
import com.example.githubprojectv2.database.FavData
import com.example.githubprojectv2.databinding.AccountItemRowBinding

class AdapterFavorite(
    private val listFavorite : ArrayList<FavData>
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
            with(listFavorite[position]){
                Glide.with(binding.rowAvatar)
                    .load(avatar)
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

                binding.rowName.text = id
                holder.itemView.setOnClickListener{
                    onItemClickCallback.onItemClicked(listFavorite[holder.adapterPosition].id)
                }
                binding.favIcon.setOnClickListener{
                    onItemClickCallback.onFavClicked(
                        listFavorite[holder.adapterPosition]
                    )
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:String)
        fun onFavClicked(fav: FavData)
    }
}