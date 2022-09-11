package dehnavi.sajjad.foodapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dehnavi.sajjad.foodapp.R
import dehnavi.sajjad.foodapp.data.db.FoodEntity
import dehnavi.sajjad.foodapp.databinding.FragmentFavoriteBinding
import hilt_aggregated_deps._dagger_hilt_android_internal_lifecycle_HiltWrapper_HiltViewModelFactory_ViewModelModule
import javax.inject.Inject
@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteContracts.View {
    //binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var presenter: FavoritePresenter

    @Inject
    lateinit var foodFavoriteAdapter: FoodFavoriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get All Foods
        presenter.getAllFood()
    }

    override fun emptyFoods() {
        binding.empty.visibility = View.VISIBLE
        binding.favoriteList.visibility = View.GONE
    }

    override fun showAllFoods(foods: List<FoodEntity>) {
        //hide Empty
        binding.empty.visibility = View.GONE
        binding.favoriteList.visibility = View.VISIBLE

        binding.favoriteList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = foodFavoriteAdapter
        }
        foodFavoriteAdapter.setData(foods)
        foodFavoriteAdapter.setOnItemClickListener {
            val directions = FavoriteFragmentDirections.actionHomeFragmentToDetailFragment(it.id)
            findNavController().navigate(directions)
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

}