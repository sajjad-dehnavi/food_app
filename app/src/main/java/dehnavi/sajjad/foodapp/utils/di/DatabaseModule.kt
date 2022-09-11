package dehnavi.sajjad.foodapp.utils.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dehnavi.sajjad.foodapp.data.db.FoodDatabase
import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.utils.FOOD_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context, FoodDatabase::class.java,
        FOOD_DB
    ).fallbackToDestructiveMigration()
        .allowMainThreadQueries()
        .build()

    @Provides
    @Singleton
    fun provideFoodDao(database: FoodDatabase) = database.foodDao()

    @Provides
    @Singleton
    fun provideFoodEntity() = FoodEntity()
}