package dehnavi.sajjad.foodapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import dehnavi.sajjad.foodapp.utils.FOOD_DB_TABLE

@Entity(tableName = FOOD_DB_TABLE)
data class FoodEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String = "",
    var img: String = ""
)
