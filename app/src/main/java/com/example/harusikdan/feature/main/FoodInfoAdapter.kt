package com.example.harusikdan.feature.main

import android.media.Image
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.harusikdan.R
import com.example.harusikdan.data.entity.Food
import kotlinx.android.synthetic.main.item_food_info.view.*

class FoodInfoAdapter(
    private val foodList: ArrayList<Food>,
    private val isClicked: (Long) -> Unit
) : RecyclerView.Adapter<FoodInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    inner class ViewHolder(parent: ViewGroup) :
        RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_food_info, parent, false)
        ) {
        private val foodImage: ImageView = itemView.food_image
        private val totalCalorie: TextView = itemView.food_calorie

        fun bind() {

        }
    }
}