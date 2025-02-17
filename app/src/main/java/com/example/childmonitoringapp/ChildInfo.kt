package com.org.childmonitorparent.parent.adapters

import android.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.childmonitorparent.parent.models.ChildInfo

class ChildAdapter(
    childList: List<ChildInfo>,
    private val listener: OnChildClickListener?
) :
    RecyclerView.Adapter<ChildAdapter.ChildViewHolder>() {
    private val childList: List<ChildInfo> = childList

    // Interface for handling click events
    interface OnChildClickListener {
        fun onChildClick(child: ChildInfo?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_child, parent, false)
        return ChildViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val child: ChildInfo = childList[position]
        holder.bind(child)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvChildName: TextView = itemView.findViewById<TextView>(R.id.tvChildName)
        private val tvChildAge: TextView = itemView.findViewById<TextView>(R.id.tvChildAge)
        private val tvChildLocation: TextView =
            itemView.findViewById<TextView>(R.id.tvChildLocation)

        fun bind(child: ChildInfo) {
            tvChildName.setText(child.getName())
            tvChildAge.text = "Age: " + child.getSelectedAge()
            tvChildLocation.text = "Location: " + child.getLocation()

            itemView.setOnClickListener { v: View? ->
                listener?.onChildClick(child)
            }
        }
    }
}