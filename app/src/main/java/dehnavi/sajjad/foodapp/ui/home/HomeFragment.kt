package dehnavi.sajjad.foodapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.flaviofaria.kenburnsview.KenBurnsView
import com.flaviofaria.kenburnsview.Transition
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding4.widget.textChanges
import dagger.hilt.android.AndroidEntryPoint
import dehnavi.sajjad.foodapp.R
import dehnavi.sajjad.foodapp.data.model.ResponseCategoryList
import dehnavi.sajjad.foodapp.data.model.ResponseFoodList
import dehnavi.sajjad.foodapp.databinding.FragmentHomeBinding
import dehnavi.sajjad.foodapp.ui.home.adapter.CategoryAdapter
import dehnavi.sajjad.foodapp.ui.home.adapter.FoodsAdapter
import dehnavi.sajjad.foodapp.utils.iNetworkAvailable
import dehnavi.sajjad.foodapp.utils.showSnackBar
import greyfox.rxnetwork.RxNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), HomeContracts.View {
    //binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var presenter: HomePresenter

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var foodsAdapter: FoodsAdapter
    //other

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("CheckResult")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Init views
        binding.apply {
            //Poster
            presenter.callRandomFood()
            //categories
            presenter.callCategoriesList()
            //food list
            presenter.callFoodList("a")
            //Search
            searchEdt.textChanges()
                .skipInitialValue()
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.toString().length > 1) {
                        //cal api
                        presenter.callSearchFood(it.toString())
                    }
                }
            //Filter
            filtersFood()
        }
        //check Internet
        RxNetwork.init(requireContext()).observe()
            .subscribeOn(Schedulers.io())
            .observeOn(io.reactivex.android.schedulers.AndroidSchedulers.mainThread())
            .subscribe {
                internetError(it.isConnected)
            }
    }

    private fun filtersFood() {
        val filters = listOf('A'..'Z').flatten()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_spinner, filters)
        adapter.setDropDownViewResource(R.layout.item_spinner_list)
        binding.filterSpinner.adapter = adapter
        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //cal api
                presenter.callFoodList(filters[p2].toString())
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }

    override fun loadRandomFood(meal: ResponseFoodList.Meal) {
        binding.apply {
            imgPoster.load(meal.strMealThumb) {
                crossfade(true)
                crossfade(400)
            }
            imgPoster.setTransitionListener(object :KenBurnsView.TransitionListener{
                override fun onTransitionStart(transition: Transition?) {

                    titleHeader.animate().alpha(1f).duration = 800L
                }

                override fun onTransitionEnd(transition: Transition?) {

                }

            })
            titleHeader.text = meal.strMeal
            headerLyt.setOnClickListener {
                meal.idMeal?.let {
                    val directions =
                        HomeFragmentDirections.actionHomeFragmentToDetailFragment(meal.idMeal.toInt())
                    findNavController().navigate(directions)
                }

            }
        }

    }

    override fun showCategories(categoryList: List<ResponseCategoryList.Category>) {
        categoryAdapter.setData(categoryList)
        binding.categoryList.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        categoryAdapter.setOnItemClickListener { category ->
            //cal filter api
            category.strCategory?.let {
                presenter.callFilterByCategory(it)
            }
        }
    }

    override fun showFoodList(foodList: List<ResponseFoodList.Meal>) {
        binding.foodsList.apply {
            binding.homeFoodsContent.visibility = View.VISIBLE
            binding.homeDisLay.visibility = View.GONE
            adapter = foodsAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
        foodsAdapter.setData(foodList)
        foodsAdapter.setOnItemClickListener {
            it.idMeal?.let { id ->
                val directions =
                    HomeFragmentDirections.actionHomeFragmentToDetailFragment(id.toInt())
                findNavController().navigate(directions)
            }
        }
    }

    override fun loadingStateFood(state: Boolean) {
        binding.homeFoodsLoading.isVisible = state
        binding.foodsList.isVisible = !state
    }

    override fun emptyList() {
        binding.apply {
            homeFoodsContent.visibility = View.GONE
            homeDisLay.visibility = View.VISIBLE
            //change image
            disconnectLay.disImg.setImageResource(R.drawable.box)
            disconnectLay.disTxt.text = getString(R.string.emptyList)
        }
    }

    override fun showLoading() {
        binding.homeCategoryLoading.visibility = View.VISIBLE
        binding.categoryList.visibility = View.GONE
    }

    override fun hideLoading() {
        binding.homeCategoryLoading.visibility = View.GONE
        binding.categoryList.visibility = View.VISIBLE
    }

    override fun checkInternet(): Boolean {
        return requireContext().iNetworkAvailable()
    }

    override fun internetError(hasInternet: Boolean) {
        if (!hasInternet) {
            binding.apply {
                foodsList.visibility = View.GONE
                homeDisLay.visibility = View.VISIBLE
                //change image
                disconnectLay.disImg.setImageResource(R.drawable.disconnect)
                disconnectLay.disTxt.text = getString(R.string.checkInternet)
                root.showSnackBar("Disconnect")
            }
        } else {
            binding.foodsList.visibility = View.VISIBLE
            binding.homeDisLay.visibility = View.GONE
        }
    }

    override fun serverError(message: String) {
        binding.root.showSnackBar(message)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}