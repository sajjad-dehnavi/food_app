package dehnavi.sajjad.foodapp.ui.home

import dehnavi.sajjad.foodapp.data.model.ResponseCategoryList
import dehnavi.sajjad.foodapp.data.model.ResponseFoodList
import dehnavi.sajjad.foodapp.utils.base.BasePresenter
import dehnavi.sajjad.foodapp.utils.base.BaseView

interface HomeContracts {
    interface View : BaseView {
        fun loadRandomFood(meal: ResponseFoodList.Meal)
        fun showCategories(categoryList: List<ResponseCategoryList.Category>)
        fun showFoodList(foodList: List<ResponseFoodList.Meal>)
        fun loadingStateFood(state: Boolean)
        fun emptyList()
    }

    interface Presenter : BasePresenter {
        fun callRandomFood()
        fun callCategoriesList()
        fun callFoodList(letter: String)
        fun callSearchFood(letter: String)
        fun callFilterByCategory(category: String)
    }
}