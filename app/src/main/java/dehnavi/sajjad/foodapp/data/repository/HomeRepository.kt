package dehnavi.sajjad.foodapp.data.repository

import dehnavi.sajjad.foodapp.data.server.ApiServices
import javax.inject.Inject

class HomeRepository @Inject constructor(private val apiServices: ApiServices) {

    fun loadFoodRandom() = apiServices.foodRandom()

    fun categoriesList() = apiServices.getCategoriesList()

    fun foodList(letter: String) = apiServices.foodList(letter)

    fun searchFood(letter: String) = apiServices.searchFood(letter)

    fun filterByCategory(category: String) = apiServices.filterByCategory(category)
}