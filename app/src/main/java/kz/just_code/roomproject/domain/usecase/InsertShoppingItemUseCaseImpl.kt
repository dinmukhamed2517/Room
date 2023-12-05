package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.ShoppingDao
import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import javax.inject.Inject

class InsertShoppingItemUseCaseImpl @Inject constructor(private val shoppingDao: ShoppingDao) :
    InsertShoppingItemUseCase {
    override suspend fun execute(shoppingItem: ShoppingItemEntity) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }
}