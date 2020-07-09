package com.kostikum.kvartirka.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.kostikum.kvartirka.databinding.FlatItemBinding
import com.kostikum.kvartirka.entity.Flat
import javax.inject.Inject

class RecyclerViewAdapter
    @Inject constructor(): RecyclerView.Adapter<RecyclerViewAdapter.CompanyViewHolder>() {
    var flats: List<Flat> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }
    internal var clickListener: (Flat, View) -> Unit = { _, _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CompanyViewHolder(
            FlatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false))

    override fun getItemCount() = flats.size

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(flats[position], clickListener)
    }

    class CompanyViewHolder(private val binding: FlatItemBinding) : ViewHolder(binding.root) {
        fun bind(item: Flat, clickListener: (Flat, View) -> Unit) {
            binding.apply {
                flat = item
                setClickListener { clickListener(item, binding.root) }
                executePendingBindings()
            }
        }
    }
}
