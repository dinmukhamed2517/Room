package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

// В пакете domain.usecase
interface GetShoppingListsUseCase {
    fun execute(): Flow<List<ShoppingListEntity>>
}
