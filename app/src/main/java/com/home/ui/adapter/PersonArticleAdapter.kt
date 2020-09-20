package com.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.article.data.ArticleUser
import com.example.anull.R
import com.home.ui.fragment.HomeFragmentDirections
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_person_article.view.*

/**
Created by Moha.Azizi on 16/08/2020 .
 */

class PersonArticleAdapter() :
    RecyclerView.Adapter<PersonArticleAdapter.ViewHolder>() {
    private lateinit var list: List<ArticleUser>

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var image: CircleImageView? = null
        private var lastDate: TextView? = null
        private var name: TextView? = null
        private var desc: TextView? = null
        private var favorite: ImageView? = null

        init {
            image = itemView.item_image_person_article
            name = itemView.title_item_person_article
            lastDate = itemView.date_item_person_article
            desc = itemView.tv_desc_person_article
            favorite = itemView.item_favorite_person_article

            itemView.setOnClickListener {
                itemView.findNavController()
                    .navigate(HomeFragmentDirections.actionHomeFragmentToArticleFragment())
            }
        }

        fun bind(articles: ArticleUser) {
            image?.setImageResource(R.drawable.prof_image)
            name?.text = articles.articleDataEntity.createdAt
            lastDate?.text = articles.articleDataEntity.updatedAt
            desc?.text = articles.articleDataEntity.description
            if (articles.articleDataEntity.favorited) {
                favorite?.setImageResource(R.drawable.ic_is_bookmark)
            } else {
                favorite?.setImageResource(R.drawable.ic_bookmark)
            }
        }


    }

    fun setList(list: List<ArticleUser>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_person_article, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}