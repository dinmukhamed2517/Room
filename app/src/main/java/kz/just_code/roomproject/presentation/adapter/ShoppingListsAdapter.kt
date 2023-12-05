package kz.just_code.roomproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.databinding.ListItemBinding

class ShoppingListsAdapter(
    private val onItemClick: (ShoppingListEntity) -> Unit,
    private val onDuplicateClick: (ShoppingListEntity) -> Unit,
    private val onHideClick: (ShoppingListEntity) -> Unit,
    private val getCompletedItemCount: (Long) -> LiveData<Int>,
    private val getTotalItemCount: (Long) -> LiveData<Int>,
    private val getItemTitles: (Long) -> LiveData<String>,
) : ListAdapter<ShoppingListEntity, ShoppingListsAdapter.ViewHolder>(ShoppingListDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onItemClick, onDuplicateClick, onHideClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val shoppingList = getItem(position)
        val completedItemCountLiveData = getCompletedItemCount(shoppingList.id)
        val totalItemCountLiveData = getTotalItemCount(shoppingList.id)
        val itemTitlesLiveData = getItemTitles(shoppingList.id)


        totalItemCountLiveData.observeForever { totalItemCount ->
            completedItemCountLiveData.observeForever { completedItemCount ->
                holder.updateCompletedItemCount(completedItemCount, totalItemCount)
            }
        }

        itemTitlesLiveData.observeForever { itemTitles ->
            holder.updateTitleTextView(itemTitles)
        }

        holder.bind(shoppingList)
    }

    class ViewHolder(
        private val binding: ListItemBinding,
        private val onItemClick: (ShoppingListEntity) -> Unit,
        private val onDuplicateClick: (ShoppingListEntity) -> Unit,
        private val onHideClick: (ShoppingListEntity) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(shoppingList: ShoppingListEntity) {
            binding.titleTextView.text = shoppingList.title

            itemView.setOnClickListener { onItemClick(shoppingList) }
            binding.duplicateImageView.setOnClickListener { onDuplicateClick(shoppingList) }
            binding.hideImageView.setOnClickListener { onHideClick(shoppingList) }
        }

        fun updateCompletedItemCount(completedItemCount: Int, totalItemCount: Int) {
            binding.progressText.text = "$completedItemCount/$totalItemCount"
            binding.progressBar.progress = (completedItemCount.toFloat() / totalItemCount * 100).toInt()
        }

        fun updateTitleTextView(itemTitles: String) {
            binding.itemsTextView.text = itemTitles
        }
    }

    private class ShoppingListDiffCallback : DiffUtil.ItemCallback<ShoppingListEntity>() {
        override fun areItemsTheSame(
            oldItem: ShoppingListEntity,
            newItem: ShoppingListEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingListEntity,
            newItem: ShoppingListEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
