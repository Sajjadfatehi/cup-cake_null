package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.article.data.ArticleUser
import com.article.ui.viewmodel.TagClickListener
import com.example.anull.databinding.ItemTitleBinding

class ArticleAdapterDiff(val clickListener: TagClickListener) :
    RecyclerView.Adapter<ArticleAdapterDiff.TitleViewHolder>() {

    inner class TitleViewHolder(var binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.cardView.setOnClickListener {
                clickListener.onCardClick(differ.currentList[layoutPosition], layoutPosition)
            }
            binding.itemImageTitle.setOnClickListener {
                clickListener.onImageClick(differ.currentList[layoutPosition].userEntity)
            }
            binding.itemIconTagTitle.setOnClickListener {
                clickListener.onBookMarkClick(
                    differ.currentList[layoutPosition].articleDataEntity.slug,
                    differ.currentList[layoutPosition].articleDataEntity.favorited, layoutPosition
                )
            }
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
        holder.binding.author = author

        holder.binding.executePendingBindings()

    }

}
