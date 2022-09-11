package dehnavi.sajjad.foodapp.utils.di

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dehnavi.sajjad.foodapp.ui.detail.DetailContracts
import dehnavi.sajjad.foodapp.ui.favorite.FavoriteContracts
import dehnavi.sajjad.foodapp.ui.home.HomeContracts

@Module
@InstallIn(FragmentComponent::class)
object ContractsModuleFR {

    @Provides
    fun provideViewHome(fragment: Fragment): HomeContracts.View {
        return fragment as HomeContracts.View
    }

    @Provides
    fun provideViewDetail(fragment: Fragment): DetailContracts.View {
        return fragment as DetailContracts.View
    }
    @Provides
    fun provideViewFavorite(fragment: Fragment): FavoriteContracts.View {
        return fragment as FavoriteContracts.View
    }
}