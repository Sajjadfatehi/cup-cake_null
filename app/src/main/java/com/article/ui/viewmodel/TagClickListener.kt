package com.article.ui.viewmodel

import com.article.data.ArticleUser
import com.user.data.UserEntity

interface TagClickListener {
    fun onCardClick(article: ArticleUser, layoutPosition: Int)
    fun onImageClick(author: UserEntity)
    fun onBookMarkClick(slug: String, isFavorited: Boolean, itemNumber: Int)
}