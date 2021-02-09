package unschooler.code.ui.main.themes.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.i_theme.view.*
import unschooler.code.R

class ThemeAdapter(val list: List<Theme>, val themeListener: (Theme) -> Unit, val themeButtonListener: (Theme) -> Unit) :
    RecyclerView.Adapter<ThemeAdapter.ThemeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        return ThemeHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.i_theme,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        holder.itemView.title.text = list[position].title
        if (list[position].isLearned){
            holder.itemView.buttonIcon.setImageResource(R.drawable.ic_completed)
            holder.itemView.buttonText.setText(R.string.ic_theme_completed)
            holder.itemView.buttonText.setTextColor(holder.itemView.resources.getColor(R.color.googleButton))
        } else {
            holder.itemView.buttonIcon.setImageResource(R.drawable.ic_learn)
            holder.itemView.buttonText.setText(R.string.ic_theme_learn)
            holder.itemView.buttonText.setTextColor(holder.itemView.resources.getColor(R.color.colorAccent))
        }
    }

    override fun getItemCount(): Int = list.size

    inner class ThemeHolder(view: View) : RecyclerView.ViewHolder(view) {

        init {
            view.setOnClickListener {
                themeListener.invoke(list[adapterPosition])
            }
            view.buttonIcon.setOnClickListener {
                updateItem()
            }
            view.buttonText.setOnClickListener {
                updateItem()
            }
        }

        private fun updateItem() {
            list[adapterPosition].isLearned = !list[adapterPosition].isLearned
            notifyItemChanged(adapterPosition)
            themeButtonListener.invoke(list[adapterPosition])
        }
    }
}