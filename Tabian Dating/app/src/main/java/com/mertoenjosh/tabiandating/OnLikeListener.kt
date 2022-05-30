package com.mertoenjosh.tabiandating

import android.widget.ImageView

interface OnLikeListener {
    fun liked(likeButton: ImageView)
    fun unLiked(likeButton: ImageView)
}