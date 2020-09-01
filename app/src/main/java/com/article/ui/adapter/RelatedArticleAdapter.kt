package com.article.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.article.data.RelatedArticleModelEntity
import com.example.anull.R
import kotlinx.android.synthetic.main.item_person_article.view.*
import kotlinx.android.synthetic.main.item_profile_post.view.*

/**
Created by Moha.Azizi on 16/08/2020 .
 */

class RelatedArticleAdapter(private val list: MutableList<RelatedArticleModelEntity>) :
    RecyclerView.Adapter<RelatedArticleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(relatedArticleModel: RelatedArticleModelEntity) {
            image?.setImageResource(R.drawable.prof_image)
            name?.text = relatedArticleModel.name
            lastDate?.text = relatedArticleModel.date
            desc?.text = relatedArticleModel.description
            if (relatedArticleModel.favorite) {
                favorite?.setImageResource(R.drawable.ic_is_bookmark)
            } else {
                favorite?.setImageResource(R.drawable.ic_bookmark)
            }

            itemView.setOnClickListener {
//                itemView.findNavController()
//                    .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())

            }
        }

        private var image: ImageView? = null
        private var lastDate: TextView? = null
        private var name: TextView? = null
        private var desc: TextView? = null
        private var favorite: ImageView? = null

        init {
            image = itemView.item_image_post_prof
            name = itemView.title_item_person_article
            lastDate = itemView.date_item_person_article
            desc = itemView.tv_desc_person_article
            favorite = itemView.item_favorite_person_article
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_related_article, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}