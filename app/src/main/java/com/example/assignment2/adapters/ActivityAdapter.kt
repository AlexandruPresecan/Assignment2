package com.example.assignment2.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment2.MainActivity
import com.example.assignment2.R
import com.example.assignment2.models.Activity

class ActivityAdapter(private val activity: MainActivity, private val dataSet: ArrayList<Activity>) :
    RecyclerView.Adapter<ActivityAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val activityView: TextView
        val accessibilityView: TextView
        val typeView: TextView
        val participantsView: TextView
        val priceView: TextView
        val favouriteView: ImageView

        init {
            // Define click listener for the ViewHolder's View
            activityView = view.findViewById(R.id.activity_text)
            accessibilityView = view.findViewById(R.id.accessibility_text)
            typeView = view.findViewById(R.id.type_text)
            participantsView = view.findViewById(R.id.participants_text)
            priceView = view.findViewById(R.id.price_text)
            favouriteView = view.findViewById(R.id.favourite)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.activity_item, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.activityView.text = dataSet[position].activity
        viewHolder.accessibilityView.text = dataSet[position].accessibility.toString()
        viewHolder.typeView.text = dataSet[position].type
        viewHolder.participantsView.text = dataSet[position].participants.toString()
        viewHolder.priceView.text = dataSet[position].price.toString()

        if (dataSet[position].favourite)
            viewHolder.favouriteView.visibility = View.VISIBLE
        else
            viewHolder.favouriteView.visibility = View.INVISIBLE

        if (dataSet[position].sent)
            viewHolder.itemView.setBackgroundColor(Color.LTGRAY)
        else
            viewHolder.itemView.setBackgroundColor(Color.WHITE)

        viewHolder.itemView.setOnClickListener {

            dataSet[position].favourite = !dataSet[position].favourite

            if (dataSet[position].favourite)
                viewHolder.favouriteView.visibility = View.VISIBLE
            else
                viewHolder.favouriteView.visibility = View.INVISIBLE
        }

        viewHolder.itemView.setOnLongClickListener {
            viewHolder.itemView.showContextMenu()
        }

        activity.registerForContextMenu(viewHolder.itemView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size
}