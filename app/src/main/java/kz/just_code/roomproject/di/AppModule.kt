package kz.just_code.roomproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kz.just_code.roomproject.data.local.ShoppingDao
import kz.just_code.roomproject.data.local.ShoppingDatabase
import kz.just_code.roomproject.domain.repository.ShoppingRepository
import kz.just_code.roomproject.data.repository.ShoppingRepositoryImpl
import kz.just_code.roomproject.domain.usecase.AddShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.AddShoppingListUseCaseImpl
import kz.just_code.roomproject.domain.usecase.DeleteShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.DeleteShoppingListUseCaseImpl
import kz.just_code.roomproject.domain.usecase.DuplicateShoppingListUseCase
import kz.just_code.roomproject.domain.usecase.DuplicateShoppingListUseCaseImpl
import kz.just_code.roomproject.domain.usecase.GetCompletedItemCountUseCase
import kz.just_code.roomproject.domain.usecase.GetCompletedItemCountUseCaseImpl
import kz.just_code.roomproject.domain.usecase.GetShoppingItemsUseCase
import kz.just_code.roomproject.domain.usecase.GetShoppingItemsUseCaseImpl
import kz.just_code.roomproject.domain.usecase.GetShoppingListsUseCase
import kz.just_code.roomproject.domain.usecase.GetShoppingListsUseCaseImpl
import kz.just_code.roomproject.domain.usecase.InsertShoppingItemUseCase
import kz.just_code.roomproject.domain.usecase.InsertShoppingItemUseCaseImpl
import kz.just_code.roomproject.domain.usecase.MarkItemAsCompletedUseCase
import kz.just_code.roomproject.domain.usecase.MarkItemAsCompletedUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideShoppingRepository(shoppingDao: ShoppingDao): ShoppingRepository {
        return ShoppingRepositoryImpl(shoppingDao)
    }

    @Provides
    @Singleton
    fun provideShoppingDao(@ApplicationContext context: Context): ShoppingDao {
        return Room.databaseBuilder(
            context,
            ShoppingDatabase::class.java,
            "shopping_database"
        ).build().shoppingDao()
    }

    @Provides
    @Singleton
    fun provideApplication(@ApplicationContext context: Context): Application {
        return context.applicationContext as Application
    }

    @Provides
    @Singleton
    fun provideAddShoppingListUseCase(shoppingRepository: ShoppingRepository): AddShoppingListUseCase {
        return AddShoppingListUseCaseImpl(shoppingRepository)
    }

    @Provides
    @Singleton
    fun provideGetCompletedItemCountUseCase(shoppingRepository: ShoppingRepository): GetCompletedItemCountUseCase {
        return GetCompletedItemCountUseCaseImpl(shoppingRepository)
    }

    @Provides
    @Singleton
    fun provideGetShoppingItemsUseCase(shoppingRepository: ShoppingRepository): GetShoppingItemsUseCase {
        return GetShoppingItemsUseCaseImpl(shoppingRepository)
    }

    @Provides
    @Singleton
    fun provideGetShoppingListsUseCase(shoppingRepository: ShoppingRepository): GetShoppingListsUseCase {
        return GetShoppingListsUseCaseImpl(shoppingRepository)
    }

    @Provides
    @Singleton
    fun provideInsertShoppingItemUseCase(shoppingDao: ShoppingDao): InsertShoppingItemUseCase {
        return InsertShoppingItemUseCaseImpl(shoppingDao)
    }

    @Provides
    @Singleton
    fun provideMarkItemAsCompletedUseCase(shoppingRepository: ShoppingRepository): MarkItemAsCompletedUseCase {
        return MarkItemAsCompletedUseCaseImpl(shoppingRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteShoppingListUseCase(repository: ShoppingRepository): DeleteShoppingListUseCase {
        return DeleteShoppingListUseCaseImpl(repository)
    }
    @Provides
    @Singleton
    fun provideDuplicateShoppingListUseCase(repository: ShoppingRepository): DuplicateShoppingListUseCase {
        return DuplicateShoppingListUseCaseImpl(repository)
    }
}