package unschooler.code.ui.main.themes.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.i_schedule.view.*
import unschooler.code.R

class SubjectsAdapter(val list: List<Subject>, val subjectListener: (Subject) -> Unit, val themeListener: (Theme) -> Unit, val themeButtonListener: (Theme) -> Unit) :
    RecyclerView.Adapter<SubjectsAdapter.SubjectHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHolder {
        return SubjectHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.i_schedule,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SubjectHolder, position: Int) {
        val linearLayoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)
        holder.itemView.list.layoutManager = linearLayoutManager
        holder.itemView.title.text = list[position].title
        holder.itemView.list.adapter = ThemeAdapter(list[position].themes, themeListener, themeButtonListener, list[position].title.equals("Алгебра", true))
    }

    override fun getItemCount(): Int = list.size

    inner class SubjectHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.seeAll.setOnClickListener {
                subjectListener.invoke(list[adapterPosition])
            }
        }
    }
}