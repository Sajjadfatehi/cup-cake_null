package com.home.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.R
import com.home.data.PersonArticleModelEntity
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_person_article.view.*

/**
Created by Moha.Azizi on 16/08/2020 .
 */

class BestArticleAdapter(private val list: MutableList<PersonArticleModelEntity>) :
    RecyclerView.Adapter<BestArticleAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(personArticleModel: PersonArticleModelEntity) {
            image?.setImageResource(R.drawable.prof_image)
            name?.text = personArticleModel.name
            lastDate?.text = personArticleModel.date
            desc?.text = personArticleModel.description
            if (personArticleModel.favorite) {
                favorite?.setImageResource(R.drawable.ic_is_bookmark)
            } else {
                favorite?.setImageResource(R.drawable.ic_bookmark)
            }
        }

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
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_best_article, parent, false)
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }
}