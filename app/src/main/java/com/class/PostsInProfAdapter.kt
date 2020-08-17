package com.`class`

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.R
import kotlinx.android.synthetic.main.item_profile_post.view.*

class PostsInProfAdapter(private val list: MutableList<PostInProf>) :
    RecyclerView.Adapter<PostsInProfAdapter.PostInProfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostInProfViewHolder {
        val newView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_post, parent, false)
        return PostInProfViewHolder(newView)

    }

    override fun onBindViewHolder(holder: PostInProfViewHolder, position: Int) {
        // holder.itemView.fi
        holder.bind(list[position])
    }


    override fun getItemCount() = list.size


    inner class PostInProfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //    private var image: ImageView? = null
        private var title: TextView? = null
        private var lastDate: TextView? = null
        private var desc: TextView? = null
        private var like: TextView? = null
        private var comment: TextView? = null
        private var name: TextView? = null


        init {

          //  image = itemView.item_image_post_prof
            title = itemView.tv_title_post_prof
            lastDate = itemView.tv_date_of_post
            desc = itemView.tv_desc_post_prof
            like = itemView.tv_like_post_prof
            comment = itemView.tv_number_of_comments_post_prof
            name = itemView.item_tv_name_post_prof

        }

        fun bind(post: PostInProf) {
            //bind image
            //image?.setImageResource(R.drawable.prof_image)
            name?.text = post.name
            title?.text = post.title
            lastDate?.text = post.lastDate
            desc?.text = post.desc
            like?.text = post.tvLike
            comment?.text = post.tvComment


        }

    }


}