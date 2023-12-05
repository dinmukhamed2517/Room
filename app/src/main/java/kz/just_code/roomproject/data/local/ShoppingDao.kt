package kz.just_code.roomproject.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM ShoppingListEntity")
    fun getShoppingLists(): Flow<List<ShoppingListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingList(shoppingList: ShoppingListEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(shoppingItem: ShoppingItemEntity)

    @Query("SELECT * FROM ShoppingItemEntity WHERE listId = :listId")
    fun getShoppingItems(listId: Long): Flow<List<ShoppingItemEntity>>

    @Update
    suspend fun updateShoppingItemCompletion(item: ShoppingItemEntity)

    @Query("SELECT COUNT(*) FROM ShoppingItemEntity WHERE listId = :listId AND isCompleted = 1")
    fun getCompletedItemCount(listId: Long): Flow<Int>

    @Delete
    suspend fun deleteShoppingList(shoppingList: ShoppingListEntity)

    @Query("SELECT * FROM ShoppingListEntity WHERE id = :listId")
    fun getShoppingListById(listId: Long): Flow<ShoppingListEntity>

    @Query("SELECT * FROM ShoppingItemEntity WHERE listId = :listId")
    fun getShoppingItemsById(listId: Long): List<ShoppingItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(items: List<ShoppingItemEntity>)
}
