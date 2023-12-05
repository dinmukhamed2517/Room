package kz.just_code.roomproject.domain.usecase

interface DuplicateShoppingListUseCase {
    suspend operator fun invoke(listId: Long)
}