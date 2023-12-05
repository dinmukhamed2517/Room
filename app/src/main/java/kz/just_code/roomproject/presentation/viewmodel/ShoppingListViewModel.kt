package kz.just_code.roomproject.presentation.viewmodel

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.domain.usecase.AddShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.DeleteShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.DuplicateShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.GetCompletedItemCountUseCase
import kz.just_code.roomproject.domain.usecase.GetShoppingItemsUseCase
import kz.just_code.roomproject.domain.usecase.GetShoppingListsUseCase
import kz.just_code.roomproject.domain.usecase.InsertShoppingItemUseCase
import kz.just_code.roomproject.domain.usecase.MarkItemAsCompletedUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    private val addShoppingListUseCase: AddShoppingListUseCase,
    private val getCompletedItemCountUseCase: GetCompletedItemCountUseCase,
    private val getShoppingItemsUseCase: GetShoppingItemsUseCase,
    private val getShoppingListsUseCase: GetShoppingListsUseCase,
    private val insertShoppingItemUseCase: InsertShoppingItemUseCase,
    private val markItemAsCompletedUseCase: MarkItemAsCompletedUseCase,
    private val deleteShoppingListUseCase: DeleteShoppingListUseCase,
    private val duplicateShoppingListUseCase: DuplicateShoppingListUseCase,
) : ViewModel() {

    val shoppingLists: LiveData<List<ShoppingListEntity>> = getShoppingListsUseCase.execute()
        .asLiveData(viewModelScope.coroutineContext)

    private val _shoppingItems = MutableLiveData<Long>()
    val shoppingItems: LiveData<List<ShoppingItemEntity>> = _shoppingItems.switchMap { listId ->
        getShoppingItemsUseCase.execute(listId).asLiveData(viewModelScope.coroutineContext)
    }

    fun loadShoppingItems(listId: Long) {
        _shoppingItems.value = listId
    }

    fun addShoppingList(shoppingList: ShoppingListEntity) {
        viewModelScope.launch {
            addShoppingListUseCase.execute(shoppingList)
        }
    }

    fun getShoppingItems(listId: Long): LiveData<List<ShoppingItemEntity>> {
        return getShoppingItemsUseCase.execute(listId).asLiveData(viewModelScope.coroutineContext)
    }

    fun insertShoppingItem(shoppingItem: ShoppingItemEntity) {
        viewModelScope.launch {
            insertShoppingItemUseCase.execute(shoppingItem)
        }
    }

    fun markItemAsCompleted(item: ShoppingItemEntity) {
        viewModelScope.launch {
            markItemAsCompletedUseCase.execute(item)
        }
    }

    fun getCompletedItemCount(listId: Long): LiveData<Int> {
        return getCompletedItemCountUseCase.execute(listId).asLiveData(viewModelScope.coroutineContext)
    }

    fun deleteShoppingList(shoppingList: ShoppingListEntity) {
        viewModelScope.launch {
            deleteShoppingListUseCase.execute(shoppingList)
        }
    }

    fun duplicateShoppingList(listId: Long) {
        viewModelScope.launch {
            duplicateShoppingListUseCase(listId)
        }
    }

}
