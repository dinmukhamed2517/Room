package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

interface GetShoppingItemsUseCase {
    fun execute(listId: Long): Flow<List<ShoppingItemEntity>>
}
