package dehnavi.sajjad.foodapp.data.repository

import dehnavi.sajjad.foodapp.data.db.FoodDao
import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.data.server.ApiServices
import javax.inject.Inject

class DetailRepository @Inject constructor(private val apiServices: ApiServices , private val dao: FoodDao) {

    fun detailMeal(id:Int)=apiServices.detailMeal(id)

    fun saveFood(entity: FoodEntity) = dao.insertFood(entity)

    fun deleteFood(entity: FoodEntity) = dao.deleteFood(entity)

    fun existsFood(id:Int) = dao.existsFood(id)
}