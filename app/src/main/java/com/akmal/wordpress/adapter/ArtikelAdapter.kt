package com.akmal.wordpress.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.akmal.wordpress.model.ArtikelModel
import com.akmal.wordpress.databinding.ListPostBinding
import com.akmal.wordpress.utilities.loadImageGlide

const val LAYOUT_CARD = 1
const val LAYOUT_LIST = 2
class ArtikelAdapter(var list: ArrayList<ArtikelModel>, var context: Context):
    RecyclerView.Adapter<ArtikelAdapter.ViewHolder>() {
    class ViewHolder(var binding: ListPostBinding): RecyclerView.ViewHolder(binding.root) {
        var imageView: ImageView = binding.ivSample
        fun bind(model: ArtikelModel, context: Context){
            binding.apply {
                tvJudulpost.text = model.judul
                tvPenulis.text = model.penulis
                loadImageGlide(model.gambar, imageView, context )


            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ListPostBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount()= list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val model = list[position]
        holder.bind(model, context)
    }
}