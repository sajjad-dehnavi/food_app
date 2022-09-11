package dehnavi.sajjad.foodapp.ui.favorite

import dehnavi.sajjad.foodapp.data.db.FoodDao
import dehnavi.sajjad.foodapp.data.repository.FavoriteRepository
import dehnavi.sajjad.foodapp.utils.applySchedulerObservableIo
import dehnavi.sajjad.foodapp.utils.base.BasePresenterImpl
import javax.inject.Inject

class FavoritePresenter @Inject constructor(
    private val repository: FavoriteRepository,
    private val view: FavoriteContracts.View
) : FavoriteContracts.Presenter, BasePresenterImpl() {
    override fun getAllFood() {
        disposable = repository.getAllFood()
            .applySchedulerObservableIo()
            .subscribe {
                if (it.isNullOrEmpty()) {
                    view.emptyFoods()
                } else {
                    view.showAllFoods(it)
                }
            }
    }
}