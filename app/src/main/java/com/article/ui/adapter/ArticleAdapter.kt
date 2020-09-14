package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.databinding.ItemTitleBinding

import com.home.ui.PersonArticleModelView

class ArticleAdapter(private val list: MutableList<PersonArticleModelView>) :
    RecyclerView.Adapter<ArticleAdapter.TitleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemTitleBinding = ItemTitleBinding.inflate(inflater, parent, false)
        return TitleViewHolder(itemTitleBinding)
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        //holder.binding.article = list[position]

        holder.binding.executePendingBindings()

    }

    inner class TitleViewHolder(var binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root)
}
