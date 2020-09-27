package com.core.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.article.data.ArticleUser
import com.example.anull.databinding.ItemProfilePostBinding
import com.user.ui.ClickListener

class TestAdapterClass(val clickListener: ClickListener) :
    RecyclerView.Adapter<TestAdapterClass.ArticleViewNew>() {

    inner class ArticleViewNew(var binding: ItemProfilePostBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {

            binding.itemIconMenuTagPotProf.setOnClickListener {
                clickListener.onClick(differ.currentList[layoutPosition], layoutPosition)
            }
            binding.cardItem.setOnClickListener {
                clickListener.onCardClick(differ.currentList[layoutPosition], layoutPosition)
            }
            binding.itemImagePostProf.setOnClickListener {
                clickListener.onImageClick(differ.currentList[layoutPosition].userEntity)
            }
            binding.likePost.setOnClickListener {
                clickListener.onLikeClick()
            }
            binding.favoriteIc.setOnClickListener {
                clickListener.onBookMarkClick(differ.currentList[layoutPosition].articleDataEntity.slug,
                    differ.currentList[layoutPosition].articleDataEntity.favorited,layoutPosition)
            }
        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<ArticleUser>() {

        override fun areItemsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
//            below code is when read from datta base
            return oldItem.articleDataEntity.slug == newItem.articleDataEntity.slug
//            return oldItem.url=newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewNew {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemProfilePostBinding.inflate(inflater, parent, false)
        return ArticleViewNew(
            itemBinding
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewNew, position: Int) {
        var author = differ.currentList[position].userEntity
        var article = differ.currentList[position].articleDataEntity

        holder.binding.article = article
        holder.binding.imageUrl = author.image
        holder.binding.executePendingBindings()
    }

}