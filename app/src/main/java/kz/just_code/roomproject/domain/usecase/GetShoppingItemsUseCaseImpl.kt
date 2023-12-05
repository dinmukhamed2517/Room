package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingItemsUseCaseImpl @Inject constructor(private val repository: ShoppingRepository) :
    GetShoppingItemsUseCase {
    override fun execute(listId: Long): Flow<List<ShoppingItemEntity>> {
        return repository.getShoppingItems(listId)
    }
}
