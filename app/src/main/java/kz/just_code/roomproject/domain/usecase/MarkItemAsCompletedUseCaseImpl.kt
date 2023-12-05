package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import javax.inject.Inject

class MarkItemAsCompletedUseCaseImpl @Inject constructor(private val repository: ShoppingRepository) :
    MarkItemAsCompletedUseCase {
    override suspend fun execute(item: ShoppingItemEntity) {
        repository.updateShoppingItemCompletion(item)
    }
}
