package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingListEntity

interface DeleteShoppingListUseCase {
    suspend fun execute(shoppingList: ShoppingListEntity)
}