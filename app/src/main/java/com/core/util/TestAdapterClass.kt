package com.core.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.article.data.ArticleDataEntity
import com.article.data.ArticleUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.anull.databinding.ItemProfilePostBinding
import com.user.data.modelfromservice.Article
import com.user.ui.ClickListener

class TestAdapterClass(val clickListener: ClickListener) :
    RecyclerView.Adapter<TestAdapterClass.ArticleViewNew>() {

    inner class ArticleViewNew(var binding: ItemProfilePostBinding) :
        RecyclerView.ViewHolder(binding.root) {


        init {

            binding.itemIconMenuTagPotProf.setOnClickListener {
                clickListener.onClick(differ.currentList[layoutPosition], layoutPosition)
            }
            binding.cardItem.setOnClickListener {
                clickListener.onCardClick(differ.currentList[layoutPosition], layoutPosition)
            }
            binding.itemImagePostProf.setOnClickListener {
                clickListener.onImageClick(differ.currentList[layoutPosition].userEntity)
            }
            binding.likePost.setOnClickListener {
                clickListener.onLikeClick()
            }
            binding.favoriteIc.setOnClickListener {
                clickListener.onBookMarkClick(differ.currentList[layoutPosition].articleDataEntity.slug,
                    differ.currentList[layoutPosition].articleDataEntity.favorited,layoutPosition)
            }
        }
    }


    private val differCallBack = object : DiffUtil.ItemCallback<ArticleUser>() {

        override fun areItemsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
//            below code is when read from datta base
            return oldItem.articleDataEntity.slug == newItem.articleDataEntity.slug
//            return oldItem.url=newItem.url
        }

        override fun areContentsTheSame(oldItem: ArticleUser, newItem: ArticleUser): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewNew {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemProfilePostBinding.inflate(inflater, parent, false)
        return ArticleViewNew(
            itemBinding
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewNew, position: Int) {
        var author=differ.currentList[position].userEntity
        var article = differ.currentList[position].articleDataEntity
        holder.binding.article = article
        if (author.image != null) {
            holder.binding.apply {
                Glide.with(itemImagePostProf.context).load(author.image)
                    .transform(CircleCrop()).into(itemImagePostProf)
            }
        } else {
            // make sure Glide doesn't load anything into this view until told otherwise
            Glide.with(holder.binding.itemImagePostProf.context)
                .clear(holder.binding.itemImagePostProf)
            // remove the placeholder (optional); read comments below
            holder.binding.itemImagePostProf.setImageDrawable(null)
        }

        holder.binding.executePendingBindings()
//        val article=differ.currentList[position]
//        holder.itemView.apply {
////            Glide.with(this).load(article.imageRes).into(ivArticleImage)
////            tv_name.text=article.tvComment
//            //and so on
////            setOnClickListener {
////                onItemClickListener?.let {
////                    it(article)
////                }
////            }
//            item_tv_name_post_prof.text=article.author.username
//            tv_title_post_prof.text=article.title
//            tv_desc_post_prof.text=article.description
//        }
    }
//
//    private var onItemClickListener:((Article)->Unit)?=null
//
//    fun setOnItemClickListener(listener:(Article)->Unit){
//        onItemClickListener=listener
//    }


}