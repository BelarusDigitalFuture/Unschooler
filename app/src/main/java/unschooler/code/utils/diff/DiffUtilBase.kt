package unschooler.code.utils.diff

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlin.reflect.KMutableProperty0

class DiffUtilBase(
    private val oldList: List<Diffable>,
    private val newList: List<Diffable>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue.getKey() == newValue.getKey()
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val oldValue = oldList[oldItemPosition]
        val newValue = newList[newItemPosition]
        return oldValue.getContent() == newValue.getContent()
    }

    companion object{
        fun <T: Diffable> updateAdapter(
            kMutableProperty: KMutableProperty0<List<T>>,
            it: List<T>,
            adapter: RecyclerView.Adapter<*>
        ) {
            val callback =
                DiffUtilBase(
                    kMutableProperty.get(),
                    it
                )
            val productDiffResult = DiffUtil.calculateDiff(callback)
            kMutableProperty.set(it)
            productDiffResult.dispatchUpdatesTo(adapter)
        }

        fun <T: Diffable> updateMutableAdapter(
            kMutableProperty: KMutableProperty0<MutableList<T>>,
            it: MutableList<T>,
            adapter: RecyclerView.Adapter<*>
        ) {
            val callback =
                DiffUtilBase(
                    kMutableProperty.get(),
                    it
                )
            val productDiffResult = DiffUtil.calculateDiff(callback)
            kMutableProperty.set(it)
            productDiffResult.dispatchUpdatesTo(adapter)
        }
    }
}