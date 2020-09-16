package com.user.ui

import com.user.data.modelfromservice.Article
import com.user.data.modelfromservice.Author

interface ClickListener {
    fun onClick(article: Article, layoutPosition: Int)
    fun onCardClick(article: Article, layoutPosition: Int)
    fun onImageClick(author: Author)

    fun onBookMarkClick(slug: String)
    fun onLikeClick()
}