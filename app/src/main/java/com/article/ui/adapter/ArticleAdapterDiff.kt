package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.databinding.ItemTitleBinding

import com.home.ui.PersonArticleModelView
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
//        val article=differ.currentList[position]
        holder.binding.article = differ.currentList[position]

        holder.binding.executePendingBindings()

    }

}
