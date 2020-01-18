package com.test.rajat.wikisearch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test.rajat.wikisearch.utli.OnCardClickListener
import com.test.rajat.wikisearch.R
import com.test.rajat.wikisearch.model.SearchResult


class ArticleAdapter(private val mContext: Context,
                     private val searchList:ArrayList<SearchResult>,
                     private val onCLick: OnCardClickListener
): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleAdapter.ViewHolder {
        return ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_search_result,parent,false))
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.titleText.text = searchList[position].title
        if(searchList[position].description == "null") {
            holder.description.text = ""
        }else {
            holder.description.text = searchList[position].description
        }
        Picasso.get().load(searchList[position].source).placeholder(R.drawable.ic_image_black_24dp).into(holder.imageBackground)

    }

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {

        val imageBackground:ImageView = itemView.findViewById(R.id.page_list_item_image)
        val titleText:TextView = itemView.findViewById(R.id.page_list_item_text)
        val description:TextView = itemView.findViewById(R.id.page_list_item_description)
        init {
            itemView.setOnClickListener {
                onCLick.onClick(searchList[adapterPosition])
            }
        }
    }
}