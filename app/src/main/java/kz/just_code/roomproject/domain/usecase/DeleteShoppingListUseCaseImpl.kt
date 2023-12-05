package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import javax.inject.Inject

class DeleteShoppingListUseCaseImpl @Inject constructor(private val repository: ShoppingRepository) :
    DeleteShoppingListUseCase {
    override suspend fun execute(shoppingList: ShoppingListEntity) {
        repository.deleteShoppingList(shoppingList)
    }
}