package kz.just_code.roomproject.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.databinding.ShoppingItemBinding

class ShoppingListAdapter(
    private val onItemClick: (ShoppingItemEntity) -> Unit,
    private val onItemCheckedChange: (ShoppingItemEntity, Boolean) -> Unit
) : ListAdapter<ShoppingItemEntity, ShoppingListAdapter.ShoppingItemViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingItemViewHolder {
        val binding =
            ShoppingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ShoppingItemViewHolder(binding, onItemCheckedChange)
    }

    override fun onBindViewHolder(holder: ShoppingItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    inner class ShoppingItemViewHolder(
        private val binding: ShoppingItemBinding,
        private val onItemCheckedChange: (ShoppingItemEntity, Boolean) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ShoppingItemEntity) {
            binding.itemCheckbox.setOnCheckedChangeListener(null) // Сброс слушателя перед установкой статуса
            binding.itemCheckbox.isChecked = item.isCompleted
            binding.nameTextView.text =
                item.title // Предполагая, что у вас есть TextView с id itemName

            binding.itemCheckbox.setOnCheckedChangeListener { _, isChecked ->
                onItemCheckedChange(item, isChecked)
            }

            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ShoppingItemEntity>() {
        override fun areItemsTheSame(
            oldItem: ShoppingItemEntity,
            newItem: ShoppingItemEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ShoppingItemEntity,
            newItem: ShoppingItemEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}
