package unschooler.code.ui.main.profile.progress.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import app.futured.donut.DonutSection
import kotlinx.android.synthetic.main.i_subject_progress.view.*
import unschooler.code.R

class ProgressAdapter(val list: List<Progress>) :
    RecyclerView.Adapter<ProgressAdapter.ProgressHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressHolder {
        return ProgressHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.i_subject_progress,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProgressHolder, position: Int) {
        with(holder.itemView){
            title.text = list[position].title
            description.text = list[position].learn.toString() + " тем из " + list[position].all

            percent.text = (list[position].learn * 100 / list[position].all).toString() + "%"

            donut.clear()
            donut.cap = list[position].all.toFloat()
            val leaveAmount = DonutSection(
                name = "leave",
                color = ContextCompat.getColor(context, R.color.colorAccent),
                amount = list[position].learn.toFloat()
            )
            donut.submitData(listOf(leaveAmount))

            user1.setImageResource(list[position].photos[0])
            user2.setImageResource(list[position].photos[1])
            user3.setImageResource(list[position].photos[2])
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ProgressHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
        }
    }
}