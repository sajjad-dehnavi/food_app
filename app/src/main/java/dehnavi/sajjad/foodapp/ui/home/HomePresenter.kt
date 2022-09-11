package dehnavi.sajjad.foodapp.ui.home

import dehnavi.sajjad.foodapp.data.repository.HomeRepository
import dehnavi.sajjad.foodapp.utils.applySchedulerIo
import dehnavi.sajjad.foodapp.utils.base.BasePresenterImpl
import hilt_aggregated_deps._dagger_hilt_android_internal_managers_ViewComponentManager_ViewWithFragmentComponentBuilderEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HomePresenter @Inject constructor(
    private val repository: HomeRepository,
    private val view: HomeContracts.View
) : HomeContracts.Presenter, BasePresenterImpl() {
    override fun callRandomFood() {
        if (view.checkInternet()) {
            disposable = repository.loadFoodRandom()
                .applySchedulerIo()
                .subscribe({ response ->
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let {
                                it.meals?.get(0)?.let { meal ->
                                    //post date
                                    view.loadRandomFood(meal)
                                }
                            }
                        }
                        in 400..499 -> {

                        }
                        in 500..599 -> {

                        }
                    }

                }, {
                    view.serverError(it.message.toString())
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callCategoriesList() {
        if (view.checkInternet()) {
            //show loading
            view.showLoading()
            disposable = repository.categoriesList()
                .applySchedulerIo()
                .subscribe({ response ->
                    //hide loading
                    view.hideLoading()
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let {
                                it.categories?.let { categories ->
                                    //post date
                                    view.showCategories(categories)
                                }
                            }
                        }
                        in 400..499 -> {

                        }
                        in 500..599 -> {

                        }
                    }

                }, {
                    view.serverError(it.message.toString())
                    view.hideLoading()
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callFoodList(letter: String) {
        if (view.checkInternet()) {
            //show loading
            view.loadingStateFood(true)
            disposable = repository.foodList(letter)
                .applySchedulerIo()
                .subscribe({ response ->
                    //hide loading
                    view.loadingStateFood(false)
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let {
                                it.meals?.let { meals ->
                                    view.showFoodList(meals)
                                }
                                if (it.meals == null)
                                    view.emptyList()

                            }
                        }
                        in 400..499 -> {

                        }
                        in 500..599 -> {

                        }
                    }

                }, {
                    view.serverError(it.message.toString())
                    view.loadingStateFood(false)
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callSearchFood(letter: String) {
        if (view.checkInternet()) {
            //show loading
            view.loadingStateFood(true)
            disposable = repository.searchFood(letter)
                .applySchedulerIo()
                .subscribe({ response ->
                    //hide loading
                    view.loadingStateFood(false)
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let {
                                it.meals?.let { meals ->
                                    view.showFoodList(meals)
                                }
                                if (it.meals == null)
                                    view.emptyList()
                            }
                        }
                        in 400..499 -> {

                        }
                        in 500..599 -> {

                        }
                    }

                }, {
                    view.serverError(it.message.toString())
                    view.loadingStateFood(false)
                })
        } else {
            view.internetError(false)
        }
    }

    override fun callFilterByCategory(category: String) {
        if (view.checkInternet()) {
            //show loading
            view.loadingStateFood(true)
            disposable = repository.filterByCategory(category)
                .applySchedulerIo()
                .subscribe({ response ->
                    //hide loading
                    view.loadingStateFood(false)
                    when (response.code()) {
                        in 200..299 -> {
                            response.body()?.let {
                                it.meals?.let { meals ->
                                    view.showFoodList(meals)
                                }
                                if (it.meals == null)
                                    view.emptyList()

                            }
                        }
                        in 400..499 -> {

                        }
                        in 500..599 -> {

                        }
                    }

                }, {
                    view.serverError(it.message.toString())
                    view.loadingStateFood(false)
                })
        } else {
            view.internetError(false)
        }
    }
}