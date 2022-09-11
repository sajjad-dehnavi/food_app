package dehnavi.sajjad.foodapp.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import dehnavi.sajjad.foodapp.R
import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.data.model.ResponseFoodList
import dehnavi.sajjad.foodapp.databinding.FragmentDetailBinding
import dehnavi.sajjad.foodapp.ui.detail.player.PlayerActivity
import dehnavi.sajjad.foodapp.utils.VIDEO_ID
import dehnavi.sajjad.foodapp.utils.iNetworkAvailable
import dehnavi.sajjad.foodapp.utils.showSnackBar
import org.json.JSONObject
import javax.inject.Inject


@AndroidEntryPoint
class DetailFragment : Fragment(), DetailContracts.View {
    //binding
    private lateinit var binding: FragmentDetailBinding

    @Inject
    lateinit var presenter: DetailPresenter

    @Inject
    lateinit var entity: FoodEntity

    //other
    private var isAddedFoodToDb = false
    private val args: DetailFragmentArgs by navArgs()
    private var mealId = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //get id
        mealId = args.mealId
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init views
        binding.apply {
            if (mealId > 0) {
                //cal Api
                showLoading()
                presenter.callDetailMeal(mealId)
            }
            //Back
            detailBack.setOnClickListener {
                findNavController().popBackStack()
            }

            //check favorite
            entity.id = mealId
            presenter.existsFood(entity.id)
        }
    }

    override fun showMeal(meal: ResponseFoodList.Meal) {
        binding.apply {
            foodCoverImg.load(meal.strMealThumb) {
                crossfade(true)
                crossfade(300)
            }
            //entity
            entity.title = meal.strMeal.toString()
            entity.img = meal.strMealThumb.toString()
            //views
            foodTitleTxt.text = meal.strMeal
            foodCategoryTxt.text = meal.strCategory
            foodAreaTxt.text = meal.strArea
            foodDescTxt.text = meal.strInstructions
            //play
            if (meal.strYoutube != null) {
                foodPlayImg.visibility = View.VISIBLE
                foodPlayImg.setOnClickListener {
                    val intent = Intent(requireActivity(), PlayerActivity::class.java)
                    val id = meal.strYoutube.toString().split("=")[1]
                    intent.putExtra(VIDEO_ID, id)
                    startActivity(intent)
                }
            } else
                foodPlayImg.visibility = View.GONE
            //Source
            if (meal.strSource != null) {
                foodSourceImg.visibility = View.VISIBLE
                foodSourceImg.setOnClickListener {
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(meal.strSource)))
                }
            } else
                foodSourceImg.visibility = View.GONE

            //Json
            val json = JSONObject(Gson().toJson(meal))
            for (i in 1..15) {
                //strIngredient
                if (json.has("strIngredient$i")) {
                    val ingredient: String = json.getString("strIngredient$i")
                    if (ingredient.isNullOrEmpty().not() && ingredient.equals("").not()) {
                        ingredientsTxt.append("$ingredient\n")
                    }
                }
                //strMeasure
                if (json.has("strMeasure$i")) {
                    val measure: String = json.getString("strMeasure$i")
                    if (measure.isNullOrEmpty().not() && measure.equals("").not()) {
                        measureTxt.append("$measure\n")
                    }
                }
            }
            //Favorite
            favoriteBtn.setOnClickListener {
                if (isAddedFoodToDb)
                    presenter.deleteFood(entity)
                else
                    presenter.saveFood(entity)
            }


        }
    }

    override fun notFoundMeal() {
        binding.root.showSnackBar("Not Found Food")
        findNavController().popBackStack()
    }

    override fun stateFavoriteFood(state: Boolean) {
        isAddedFoodToDb = state
        if (isAdded) {
            if (state) {
                binding.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_favorite_24
                    )
                )
            } else {
                binding.favoriteBtn.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_favorite_border_24
                    )
                )
            }
        }
    }

    override fun hideLoading() {
        binding.detailContentLay.visibility = View.VISIBLE
        binding.detailLoading.visibility = View.GONE

    }

    override fun showLoading() {
        binding.detailContentLay.visibility = View.GONE
        binding.detailLoading.visibility = View.VISIBLE
    }

    override fun checkInternet(): Boolean {
        return requireContext().iNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {

    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }


    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}