package dehnavi.sajjad.foodapp.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import dehnavi.sajjad.foodapp.R
import dehnavi.sajjad.foodapp.databinding.ActivityFoodBinding

@AndroidEntryPoint
class FoodActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityFoodBinding

    //other
    private lateinit var navHost: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //navController
        navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        //bottom nav
        binding.bottomNav.setupWithNavController(navHost.navController)
        //show bottom nav
        navHost.navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNav.isVisible = (destination.id == R.id.detailFragment).not()
        }
    }

    override fun onNavigateUp(): Boolean {
        return super.onNavigateUp() || navHost.navController.navigateUp()
    }
}