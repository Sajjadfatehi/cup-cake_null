package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.anull.databinding.ItemTitleBinding

import com.user.data.modelfromservice.Article

class ArticleAdapterDiff :
    RecyclerView.Adapter<ArticleAdapterDiff.TitleViewHolder>() {


    inner class TitleViewHolder(var binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root){
        init {

        }
    }




    private val differCallBack=object : DiffUtil.ItemCallback<Article>(){

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
//            below code is when read from datta base
//            return oldItem.url=newItem.url
            return oldItem.slug==newItem.slug
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }

    val differ= AsyncListDiffer(this,differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTitleBinding = ItemTitleBinding.inflate(inflater, parent, false)
        return TitleViewHolder(itemTitleBinding)
    }

    override fun getItemCount() = differ.currentList.size


    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {

        var article=differ.currentList[position]
        holder.binding.article = article
        if (article.author.image!=null){
            holder.binding.apply {
                Glide.with(itemImageTitle.context).load(article.author.image).transform(
                    CircleCrop()
                ).into(itemImageTitle)
            }
        }
        holder.binding.executePendingBindings()

    }

}
