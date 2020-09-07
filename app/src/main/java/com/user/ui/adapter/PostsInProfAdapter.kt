package com.user.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.databinding.ItemProfilePostBinding
import com.user.ui.ArticleView
import com.user.ui.ClickListener

class PostsInProfAdapter(
    private val list: MutableList<ArticleView>,
    val clickListener: ClickListener
) :
    RecyclerView.Adapter<PostsInProfAdapter.PostInProfViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostInProfViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemProfilePostBinding.inflate(inflater, parent, false)
        return PostInProfViewHolder(itemBinding)

    }

    override fun onBindViewHolder(holder: PostInProfViewHolder, position: Int) {
        holder.binding.article = list[position]

        holder.binding.executePendingBindings()
    }

    override fun getItemCount() = list.size

    inner class PostInProfViewHolder(var binding: ItemProfilePostBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.itemIconMenuTagPotProf.setOnClickListener {
                clickListener.onClick(list[layoutPosition], layoutPosition)
            }
        }

    }


}