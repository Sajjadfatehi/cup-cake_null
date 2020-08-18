package com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.R
import com.model.home.PersonArticleModel
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_title.view.*

class ArticleAdapter(private val list: MutableList<PersonArticleModel>) :
    RecyclerView.Adapter<ArticleAdapter.TitleViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        return TitleViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_title, parent, false)
        )
    }

    override fun getItemCount() = list.size


    override fun onBindViewHolder(holder: ArticleAdapter.TitleViewHolder, position: Int) {
        holder.bind(list[position])

    }

    inner class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private var image: CircleImageView? = null
        private var lastDate: TextView? = null
        private var name: TextView? = null
        private var desc: TextView? = null
        private var favorite: ImageView? = null

        init {
            image = itemView.item_image_title
            name = itemView.item_tv_name_title
            lastDate = itemView.tv_date_of_title
            desc = itemView.tv_item_title
            favorite = itemView.item_icon_tag_title
        }

        fun bind(personArticleModel: PersonArticleModel) {
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


    }
}
