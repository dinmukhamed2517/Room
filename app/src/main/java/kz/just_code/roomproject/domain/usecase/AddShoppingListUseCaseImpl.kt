package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingListEntity
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import javax.inject.Inject

class AddShoppingListUseCaseImpl @Inject constructor(private val repository: ShoppingRepository) :
    AddShoppingListUseCase {
    override suspend fun execute(shoppingList: ShoppingListEntity) {
        repository.insertShoppingList(shoppingList)
    }
}