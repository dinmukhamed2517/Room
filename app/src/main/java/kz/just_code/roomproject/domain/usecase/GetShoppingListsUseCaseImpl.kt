package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShoppingListsUseCaseImpl @Inject constructor(private val repository: ShoppingRepository) :
    GetShoppingListsUseCase {
    override fun execute(): Flow<List<ShoppingListEntity>> {
        return repository.getShoppingLists()
    }
}
