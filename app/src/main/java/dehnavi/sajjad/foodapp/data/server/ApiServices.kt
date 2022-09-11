package dehnavi.sajjad.foodapp.data.server

import dehnavi.sajjad.foodapp.data.model.ResponseCategoryList
import dehnavi.sajjad.foodapp.data.model.ResponseFoodList
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServices {
    @GET("random.php")
    fun foodRandom(): Single<Response<ResponseFoodList>>

    @GET("search.php")
    fun foodList(@Query("f") letter: String): Single<Response<ResponseFoodList>>

    @GET("search.php")
    fun searchFood(@Query("s") letter: String): Single<Response<ResponseFoodList>>

    @GET("filter.php")
    fun filterByCategory(@Query("c") category: String): Single<Response<ResponseFoodList>>


    @GET("lookup.php")
    fun detailMeal(@Query("i") id: Int): Single<Response<ResponseFoodList>>

    @GET("categories.php")
    fun getCategoriesList(): Single<Response<ResponseCategoryList>>
}