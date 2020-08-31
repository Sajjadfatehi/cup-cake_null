package com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.article.data.CommentArticleModelEntity
import com.example.anull.R
import kotlinx.android.synthetic.main.item_person_article.view.*
import kotlinx.android.synthetic.main.item_profile_post.view.*

/**
Created by Moha.Azizi on 16/08/2020 .
 */

class CommentAdapter(private val list: MutableList<CommentArticleModelEntity>) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(commentArticleModel: CommentArticleModelEntity) {
            image?.setImageResource(R.drawable.prof_image)
            name?.text = commentArticleModel.name
            desc?.text = commentArticleModel.description

        }

        private var image: ImageView? = null
        private var name: TextView? = null
        private var desc: TextView? = null

        init {
            image = itemView.item_image_post_prof
            name = itemView.title_item_person_article
            desc = itemView.tv_desc_person_article
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.comment_adapter, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}