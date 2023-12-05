package kz.just_code.roomproject.domain.repository

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun getShoppingLists(): Flow<List<ShoppingListEntity>>
    suspend fun insertShoppingList(shoppingList: ShoppingListEntity)
    fun getShoppingItems(listId: Long): Flow<List<ShoppingItemEntity>>
    suspend fun updateShoppingItemCompletion(item: ShoppingItemEntity)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity)
    fun getCompletedItemCount(listId: Long): Flow<Int>
    suspend fun deleteShoppingList(shoppingList: ShoppingListEntity)
    suspend fun duplicateShoppingList(listId: Long)
}
