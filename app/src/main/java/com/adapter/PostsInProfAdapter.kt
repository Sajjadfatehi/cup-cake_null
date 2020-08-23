package com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.R
import com.icallback.ClickListener
import com.model.PostInProf
import kotlinx.android.synthetic.main.item_profile_post.view.*

class PostsInProfAdapter(
    private val list: MutableList<PostInProf>,
    val clickListener: ClickListener? = null
) :
    RecyclerView.Adapter<PostsInProfAdapter.PostInProfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostInProfViewHolder {
        val newView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_post, parent, false)
        return PostInProfViewHolder(newView)

    }

    override fun onBindViewHolder(holder: PostInProfViewHolder, position: Int) {

        holder.image?.setImageResource(list[position].image)

        holder.name?.text = list[position].name
        holder.title?.text = list[position].title
        holder.lastDate?.text = list[position].lastDate
        holder.desc?.text = list[position].desc
        holder.like?.text = list[position].tvLike
        holder.comment?.text = list[position].tvComment

        /*holder.bind(list[position])*/
    }


    override fun getItemCount() = list.size


    inner class PostInProfViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = null

        var title: TextView? = null
        var lastDate: TextView? = null
        var desc: TextView? = null
        var like: TextView? = null
        var comment: TextView? = null
        var name: TextView? = null


        init {

            image = itemView.item_image_post_prof
            image as ImageView
            title = itemView.tv_title_post_prof
            lastDate = itemView.tv_date_of_post
            desc = itemView.tv_desc_post_prof
            like = itemView.tv_like_post_prof
            comment = itemView.tv_number_of_comments_post_prof
            name = itemView.item_tv_name_post_prof

            itemView.item_icon_menu_tag_pot_prof.setOnClickListener {
                clickListener?.onClick(list[layoutPosition], layoutPosition)
            }
            image!!.setOnClickListener {
                clickListener?.onClick(list[layoutPosition], layoutPosition)
            }

        }

        /*fun bind(post: PostInProf) {
            //bind image
            //image?.setImageResource(R.drawable.prof_image)
            name?.text = post.name
            title?.text = post.title
            lastDate?.text = post.lastDate
            desc?.text = post.desc
            like?.text = post.tvLike
            comment?.text = post.tvComment


        }*/

    }


}