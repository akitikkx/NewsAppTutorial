package com.ahmedtikiwa.mynewsapp.ui.headlines

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ahmedtikiwa.mynewsapp.R
import com.ahmedtikiwa.mynewsapp.databinding.HeadlineItemBinding
import com.ahmedtikiwa.mynewsapp.domain.Article

class HeadlinesAdapter(private val listener: HeadlineClickListener) :
    RecyclerView.Adapter<HeadlinesAdapter.ViewHolder>() {

    var headlines: List<Article> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val withDataBinding: HeadlineItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            ViewHolder.LAYOUT,
            parent,
            false
        )
        return ViewHolder(withDataBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.headline = headlines[position]
            it.headlineItemListener = listener
        }
    }

    override fun getItemCount(): Int = headlines.size

    class HeadlineClickListener(val block: (Article) -> Unit) {
        fun onClick(article: Article) = block(article)
    }

    class ViewHolder(val viewDataBinding: HeadlineItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.headline_item
        }
    }
}