package com.newsappmvvmarchitecture.www.utils

import android.graphics.Typeface
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.newsappmvvmarchitecture.www.utils.ext.getParentActivity


@BindingAdapter("adapter")
fun setAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("mutableVisibility")
fun setMutableVisibility(view: View,  visibility: MutableLiveData<Int>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = value?:View.VISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value ?: "" })
    }
}
    @BindingAdapter("mutableProgress")
    fun setMutableProgress(view: ProgressBar, progressStatus: MutableLiveData<Int>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if (parentActivity != null && progressStatus != null) {
            progressStatus.observe(parentActivity, Observer { value -> view.progress = value ?: 0 })
        }
    }

    @BindingAdapter("mutableProgressSize")
    fun setMutableProgressSize(view: ProgressBar, progressStatus: MutableLiveData<Int>?) {
        val parentActivity: AppCompatActivity? = view.getParentActivity()
        if (parentActivity != null && progressStatus != null) {
            progressStatus.observe(parentActivity, Observer { value -> view.max = value ?: 0 })
        }
    }

@BindingAdapter("mutableColor")
fun setLinearLayoutColor(view: LinearLayout, text: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.setBackgroundResource(value)})
    }
}

@BindingAdapter("mutableTextColor")
fun setMutableTextColor(view: TextView, text: MutableLiveData<Int>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if (parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.setTextColor(value)})
    }
}
