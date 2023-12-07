package kz.just_code.roomproject.presentation

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.airbnb.lottie.LottieAnimationView
import dagger.hilt.android.AndroidEntryPoint
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.R
import kz.just_code.roomproject.databinding.FragmentShoppingListBinding
import kz.just_code.roomproject.presentation.adapter.ShoppingListAdapter
import kz.just_code.roomproject.presentation.viewmodel.ShoppingListViewModel

@AndroidEntryPoint
class ShoppingListFragment : Fragment() {
    private var _binding: FragmentShoppingListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ShoppingListViewModel by viewModels()
    private lateinit var shoppingListAdapter: ShoppingListAdapter
    private val listId: Long by lazy {
        arguments?.getLong("listId") ?: throw IllegalArgumentException("List ID is required")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShoppingListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        viewModel.loadShoppingItems(listId)
        viewModel.shoppingItems.observe(viewLifecycleOwner) { items ->
            shoppingListAdapter.submitList(items)
            updateProgress(items)
            updateEmptyState(items)
            updateAddItemButtonGravity(items)
        }

        binding.addItemButton.setOnClickListener {
            showAddItemDialog()
        }
        binding.orderBtn.setOnClickListener {
            showCustomDialog("Success", "You orders will be delivered soon")
        }
    }

    private fun updateAddItemButtonGravity(items: List<ShoppingItemEntity>) {
        val layoutParams = binding.addItemButton.layoutParams as LinearLayout.LayoutParams

        if (items.isEmpty()) {
            layoutParams.gravity = Gravity.CENTER
        } else {
            layoutParams.gravity = Gravity.END
        }

        binding.addItemButton.layoutParams = layoutParams
    }

    private fun updateEmptyState(items: List<ShoppingItemEntity>) {
        if (items.isEmpty()) {
            binding.emptyImageView.visibility = View.VISIBLE
            binding.emptyTextView.visibility = View.VISIBLE
        } else {
            binding.emptyImageView.visibility = View.GONE
            binding.emptyTextView.visibility = View.GONE
        }
    }
    protected fun showCustomDialog(title: String, content: String) {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_success_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val animationView: LottieAnimationView = dialog.findViewById(R.id.animation)
        val titleTextView: TextView = dialog.findViewById(R.id.title)
        val contentTextView: TextView = dialog.findViewById(R.id.content)

        animationView.playAnimation()
        titleTextView.text = title
        contentTextView.text = content
        dialog.show()
        val button: Button = dialog.findViewById(R.id.ok_btn)
        button.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun showAddItemDialog() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom_add_item, null)
        val editTextItemName = dialogView.findViewById<EditText>(R.id.editTextItemName)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialogView.findViewById<Button>(R.id.cancelButton).setOnClickListener {
            dialog.cancel()
        }

        dialogView.findViewById<Button>(R.id.createButton).setOnClickListener {
            val itemName = editTextItemName.text.toString().trim()
            if (itemName.isNotEmpty()) {
                addNewItem(itemName, listId)
                dialog.dismiss()
            } else {
                editTextItemName.error = "Please enter an item name"
            }
        }

        dialog.show()
    }


    private fun addNewItem(itemName: String, listId: Long) {
        val newItem = ShoppingItemEntity(listId = listId, title = itemName, isCompleted = false)
        viewModel.insertShoppingItem(newItem)
        viewModel.loadShoppingItems(listId)
    }

    private fun setupRecyclerView() {
        shoppingListAdapter = ShoppingListAdapter(
            onItemClick = { item ->
                // Обработка клика на элемент (например, переход к деталям элемента)
            },
            onItemCheckedChange = { item, isChecked ->
                // Обновить элемент в базе данных
                val updatedItem = item.copy(isCompleted = isChecked)
                viewModel.markItemAsCompleted(updatedItem)
            }
        )

        binding.shoppingListRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = shoppingListAdapter
        }
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun updateProgress(items: List<ShoppingItemEntity>) {
        val completedItems = items.count { it.isCompleted }
        val newProgress = (completedItems.toFloat() / items.size.toFloat()) * 100

        val animator = ObjectAnimator.ofFloat(
            binding.progressBar,
            "progress",
            binding.progressBar.progress.toFloat(),
            newProgress
        )
        animator.duration = 500

        animator.addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Float
            binding.progressBar.progress = animatedValue.toInt()
        }

        animator.start()
        binding.progressText.text = "$completedItems/${items.size}"
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
