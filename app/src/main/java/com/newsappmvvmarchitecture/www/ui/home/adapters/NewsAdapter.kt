package com.newsappmvvmarchitecture.www.ui.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.newsappmvvmarchitecture.domain.core.home.Results
import com.newsappmvvmarchitecture.www.R
import com.newsappmvvmarchitecture.www.databinding.RowNewsBinding

class NewsAdapter(private val newsList: MutableList<Results>, val onPostClickListener: OnPostClickListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    fun updateList(mProducts: List<Results>) {
        newsList.clear()
        newsList.addAll(mProducts)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: RowNewsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Results, clickListener: OnPostClickListener) {
            binding.apply {
                model = post
                listener = clickListener
            }
//            val img = "http://openweathermap.org/img/w/${product.weather!![0].icon}.png"
//            Picasso.get().load(img).error(R.mipmap.ic_launcher)
//                .into(itemBinding.imgIcon);
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: RowNewsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.row_news,
            parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(newsList[position], onPostClickListener)

    override fun getItemCount() = newsList.size
}

interface OnPostClickListener {
    fun onPostClickListener(dataModel: Results)
}