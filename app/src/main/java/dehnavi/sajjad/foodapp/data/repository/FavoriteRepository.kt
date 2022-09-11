package dehnavi.sajjad.foodapp.data.repository

import dehnavi.sajjad.foodapp.data.db.FoodDao
import dehnavi.sajjad.foodapp.data.db.FoodEntity
import javax.inject.Inject

class FavoriteRepository @Inject constructor(private val dao: FoodDao) {

    fun saveFood(entity: FoodEntity) = dao.insertFood(entity)

    fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)

    fun getAllFood() = dao.getAllFood()

    fun existsFood(id:Int) = dao.existsFood(id)
}