package dehnavi.sajjad.foodapp.ui.favorite

import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.utils.base.BasePresenter
import dehnavi.sajjad.foodapp.utils.base.BaseView

interface FavoriteContracts {
    interface View  {
        fun emptyFoods()
        fun showAllFoods(foods: List<FoodEntity>)
    }

    interface Presenter : BasePresenter {
        fun getAllFood()
    }
}