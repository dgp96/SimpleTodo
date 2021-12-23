package com.example.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * A bridge that tells the recylcer view how to display the data
 */


class TaskItemAdapter(val listOfItems:List<String>, val longClickListener: OnLongClickListener) : RecyclerView.Adapter<TaskItemAdapter.ViewHolder>() {


    interface OnLongClickListener{ //Interface to be implemented by Main where it will delete the item long clicked
        fun onItemLongClicked(position: Int){

        }
    }

    // Usually involves inflating a layout from XML and returning the holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_expandable_list_item_1, parent, false)
        // Return a new holder instance

        return ViewHolder(contactView)

    }
    // Involves populating data into the item through holder
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //Get the data model based on position
        val item =  listOfItems.get(position)

        holder.textView.text = item
    }

    override fun getItemCount(): Int {
        return listOfItems.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store references to elements in our layout view

        val textView: TextView

        init{
            textView = itemView.findViewById(android.R.id.text1)
            itemView.setOnLongClickListener {
                //Log.i("Dharmit","Long click detected on item: "+adapterPosition)
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}