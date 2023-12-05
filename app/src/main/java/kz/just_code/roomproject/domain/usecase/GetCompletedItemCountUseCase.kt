package kz.just_code.roomproject.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetCompletedItemCountUseCase {
    fun execute(listId: Long): Flow<Int>
}