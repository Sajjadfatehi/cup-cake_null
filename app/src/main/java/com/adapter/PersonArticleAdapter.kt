package com.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.anull.R

/**
Created by Moha.Azizi on 16/08/2020 .
 */

class PersonArticleAdapter() : RecyclerView.Adapter<PersonArticleAdapter.ViewHolder>() {

inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

    init {

    }
}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder( LayoutInflater.from(parent.context).inflate(R.layout.person_article_adapter, parent, false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}