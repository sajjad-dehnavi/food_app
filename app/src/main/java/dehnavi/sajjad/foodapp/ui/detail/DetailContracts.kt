package dehnavi.sajjad.foodapp.ui.detail

import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.data.model.ResponseFoodList
import dehnavi.sajjad.foodapp.utils.base.BasePresenter
import dehnavi.sajjad.foodapp.utils.base.BaseView

interface DetailContracts {
    interface View : BaseView {
        fun showMeal(meal: ResponseFoodList.Meal)
        fun notFoundMeal()
        fun stateFavoriteFood(state:Boolean)
    }

    interface Presenter : BasePresenter {
        fun callDetailMeal(id: Int)
        fun saveFood(entity: FoodEntity)
        fun deleteFood(entity: FoodEntity)
        fun existsFood(id: Int)
    }
}