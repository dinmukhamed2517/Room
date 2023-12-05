package kz.just_code.roomproject.data.repository

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.data.local.ShoppingDao
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ShoppingRepositoryImpl @Inject constructor(private val shoppingDao: ShoppingDao) :
    ShoppingRepository {

    override fun getShoppingLists(): Flow<List<ShoppingListEntity>> =
        shoppingDao.getShoppingLists()

    override suspend fun insertShoppingList(shoppingList: ShoppingListEntity) {
        withContext(Dispatchers.IO) {
            shoppingDao.insertShoppingList(shoppingList)
        }
    }

    override fun getShoppingItems(listId: Long): Flow<List<ShoppingItemEntity>> =
        shoppingDao.getShoppingItems(listId)

    override suspend fun updateShoppingItemCompletion(item: ShoppingItemEntity) {
        withContext(Dispatchers.IO) {
            shoppingDao.updateShoppingItemCompletion(item)
        }
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity) {
        withContext(Dispatchers.IO) {
            shoppingDao.insertShoppingItem(shoppingItem)
        }
    }

    override fun getCompletedItemCount(listId: Long): Flow<Int> =
        shoppingDao.getCompletedItemCount(listId)

    override suspend fun deleteShoppingList(shoppingList: ShoppingListEntity) {
        withContext(Dispatchers.IO) {
            shoppingDao.deleteShoppingList(shoppingList)
        }
    }

    override suspend fun duplicateShoppingList(listId: Long) {
        withContext(Dispatchers.IO) {
            val originalShoppingList = shoppingDao.getShoppingListById(listId).firstOrNull()
            originalShoppingList?.let {
                val duplicatedList = it.copy(id = 0)
                val newShoppingListId = shoppingDao.insertShoppingList(duplicatedList)

                val originalItems = shoppingDao.getShoppingItemsById(listId)
                val duplicatedItems = originalItems.map { item ->
                    item.copy(id = 0, listId = newShoppingListId)
                }

                shoppingDao.insertShoppingItems(duplicatedItems)
            }
        }
    }
}
