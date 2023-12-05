package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.domain.repository.ShoppingRepository
import javax.inject.Inject

class DuplicateShoppingListUseCaseImpl @Inject constructor(private val shoppingRepository: ShoppingRepository) :
    DuplicateShoppingListUseCase {

    override suspend fun invoke(listId: Long) {
        shoppingRepository.duplicateShoppingList(listId)
    }
}