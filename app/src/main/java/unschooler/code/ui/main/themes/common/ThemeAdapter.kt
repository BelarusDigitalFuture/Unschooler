package unschooler.code.ui.main.themes.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.i_theme.view.*
import unschooler.code.R

class ThemeAdapter(
    val list: List<Theme>,
    val themeListener: (Theme) -> Unit,
    val themeButtonListener: (Theme) -> Unit,
    val hasPremium: Boolean = false) :
    RecyclerView.Adapter<ThemeAdapter.ThemeHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemeHolder {
        if (viewType == 1)
            return ThemeHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.i_theme_premium,
                    parent,
                    false
                )
            )
        return ThemeHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.i_theme,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasPremium && position == 0) 1 else 0
    }

    override fun onBindViewHolder(holder: ThemeHolder, position: Int) {
        if (!hasPremium || position != 0) {
            val index = position - (if (hasPremium) 1 else 0)

            holder.itemView.title.text = list[index].title
            if (list[index].isLearned) {
                holder.itemView.buttonIcon.setImageResource(R.drawable.ic_completed)
                holder.itemView.buttonText.setText(R.string.ic_theme_completed)
                holder.itemView.buttonText.setTextColor(holder.itemView.resources.getColor(R.color.googleButton))
            } else {
                holder.itemView.buttonIcon.setImageResource(R.drawable.ic_learn)
                holder.itemView.buttonText.setText(R.string.ic_theme_learn)
                holder.itemView.buttonText.setTextColor(holder.itemView.resources.getColor(R.color.colorAccent))
            }
            holder.buttons()
        }
    }

    override fun getItemCount(): Int = (if (hasPremium) 1 else 0) + list.size

    inner class ThemeHolder(view: View) : RecyclerView.ViewHolder(view) {

        private fun updateItem() {
            list[adapterPosition].isLearned = !list[adapterPosition].isLearned
            notifyItemChanged(adapterPosition)
            themeButtonListener.invoke(list[adapterPosition])
        }

        fun buttons() {
            if (!hasPremium || adapterPosition != 0) {
                itemView.setOnClickListener {
                    themeListener.invoke(list[adapterPosition - (if (hasPremium) 1 else 0)])
                }
                itemView.buttonIcon.setOnClickListener {
                    updateItem()
                }
                itemView.buttonText.setOnClickListener {
                    updateItem()
                }
            }
        }
    }
}