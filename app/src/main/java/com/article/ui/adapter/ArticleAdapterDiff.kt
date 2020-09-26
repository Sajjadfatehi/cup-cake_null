package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.article.data.ArticleUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.anull.databinding.ItemTitleBinding

class ArticleAdapterDiff :
    RecyclerView.Adapter<ArticleAdapterDiff.TitleViewHolder>() {

    inner class TitleViewHolder(var binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<ArticleUser>() {

        override fun areItemsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
//            below code is when read from datta base
//            return oldItem.url=newItem.url
            return oldItem.articleDataEntity.slug == newItem.articleDataEntity.slug
        }

        override fun areContentsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTitleBinding = ItemTitleBinding.inflate(inflater, parent, false)
        return TitleViewHolder(itemTitleBinding)
    }

    override fun getItemCount() = differ.currentList.size


    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {

        var article = differ.currentList[position].articleDataEntity
        var author = differ.currentList[position].userEntity
        holder.binding.article = article
        if (author.image != null) {
            holder.binding.apply {
                Glide.with(itemImageTitle.context).load(author.image).transform(
                    CircleCrop()
                ).into(itemImageTitle)
            }
        }
        holder.binding.executePendingBindings()

    }

}
