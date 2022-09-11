package dehnavi.sajjad.foodapp.data.db

import androidx.room.*
import dehnavi.sajjad.foodapp.utils.FOOD_DB_TABLE
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

@Dao
interface FoodDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFood(entity: FoodEntity): Completable

    @Delete
    fun deleteFood(entity: FoodEntity): Completable

    @Query("SELECT * FROM $FOOD_DB_TABLE")
    fun getAllFood(): Observable<List<FoodEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM $FOOD_DB_TABLE WHERE id =:id)")
    fun existsFood(id: Int): Observable<Boolean>
}