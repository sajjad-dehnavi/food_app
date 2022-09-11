package dehnavi.sajjad.foodapp.ui.detail

import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.data.repository.DetailRepository
import dehnavi.sajjad.foodapp.utils.applySchedulerCompletableIo
import dehnavi.sajjad.foodapp.utils.applySchedulerIo
import dehnavi.sajjad.foodapp.utils.applySchedulerObservableIo
import dehnavi.sajjad.foodapp.utils.base.BasePresenterImpl
import javax.inject.Inject

class DetailPresenter @Inject constructor(
    private val repository: DetailRepository,
    private val view: DetailContracts.View
) : DetailContracts.Presenter, BasePresenterImpl() {
    override fun callDetailMeal(id: Int) {
        if (view.checkInternet()) {
            view.showLoading()
            disposable = repository.detailMeal(id)
                .applySchedulerIo()
                .subscribe({ response ->
                    view.hideLoading()
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let { itBody ->
                                if (itBody.meals.isNotEmpty()) {
                                    view.showMeal(itBody.meals[0])
                                }
                                if (response.body() == null)
                                    view.notFoundMeal()
                            }
                        }
                    }
                }, {
                    view.hideLoading()
                    view.serverError(it.message.toString())
                })
        } else {
            view.internetError(false)
        }
    }

    override fun saveFood(entity: FoodEntity) {
        disposable = repository.saveFood(entity)
            .applySchedulerCompletableIo()
            .subscribe {
                view.stateFavoriteFood(true)
            }
    }

    override fun deleteFood(entity: FoodEntity) {
        disposable = repository.deleteFood(entity)
            .applySchedulerCompletableIo()
            .subscribe {
                view.stateFavoriteFood(false)
            }
    }

    override fun existsFood(id: Int) {
        disposable = repository.existsFood(id)
            .applySchedulerObservableIo()
            .subscribe { state ->
                view.stateFavoriteFood(state)
            }
    }
}