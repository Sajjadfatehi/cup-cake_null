package com.icallback

import com.model.PostInProf

interface ClickListener {
    fun onClick(postInProf: PostInProf, layoutPosition: Int)
}