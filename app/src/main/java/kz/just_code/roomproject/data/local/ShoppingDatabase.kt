package kz.just_code.roomproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import kz.just_code.roomproject.data.local.ShoppingDao
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.data.local.entity.ShoppingListEntity


@Database(entities = [ShoppingItemEntity::class, ShoppingListEntity::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao
}
