package com.user.ui

import com.article.data.ArticleDataEntity
import com.article.data.ArticleUser
import com.user.data.UserEntity
import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.Author

interface ClickListener {
    fun onClick(article: ArticleUser, layoutPosition: Int)
    fun onCardClick(article: ArticleUser, layoutPosition: Int)
    fun onImageClick(author: UserEntity)

    fun onBookMarkClick(slug: String,isFavorited:Boolean,itemNumber:Int)
    fun onLikeClick()
}