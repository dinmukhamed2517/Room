package kz.just_code.roomproject.domain.usecase

import kz.just_code.roomproject.data.local.entity.ShoppingItemEntity

interface MarkItemAsCompletedUseCase {
    suspend fun execute(item: ShoppingItemEntity)
}