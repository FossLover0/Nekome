package com.chesire.nekome.features.search

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.chesire.nekome.core.Resource
import com.chesire.nekome.helpers.launchActivity
import com.chesire.nekome.helpers.login
import com.chesire.nekome.injection.DatabaseModule
import com.chesire.nekome.injection.SearchModule
import com.chesire.nekome.kitsu.AuthProvider
import com.chesire.nekome.robots.activity
import com.chesire.nekome.robots.search.search
import com.chesire.nekome.search.api.SearchApi
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

private const val GENERIC_ERROR = "GENERIC_ERROR"
private const val NO_RESULTS_ERROR = "NO_RESULTS_ERROR"

@HiltAndroidTest
@UninstallModules(
    DatabaseModule::class,
    SearchModule::class
)
@RunWith(AndroidJUnit4::class)
class SearchTests {

    @get:Rule
    val hilt = HiltAndroidRule(this)

    @Inject
    lateinit var authProvider: AuthProvider

    @BindValue
    val searchApi = mockk<SearchApi> {
        coEvery {
            searchForAnime(GENERIC_ERROR)
        } coAnswers {
            Resource.Error("")
        }
        coEvery {
            searchForAnime(NO_RESULTS_ERROR)
        } coAnswers {
            Resource.Success(listOf())
        }
    }

    @Before
    fun setUp() {
        hilt.inject()
        authProvider.login()
    }

    @Test
    fun canReachSearch() {
        launchActivity()

        activity {
            goToSearch()
        }
        search {
            validate { isVisible() }
        }
    }

    @Test
    fun emptySearchTermShowsError() {
        launchActivity()

        activity {
            goToSearch()
        }
        search {
            searchTerm("")
            selectAnime()
            clickSearch()
        } validate {
            isEmptySearchError()
        }
    }

    @Test
    fun genericErrorFromSearchShowsError() {
        launchActivity()

        activity {
            goToSearch()
        }
        search {
            searchTerm(GENERIC_ERROR)
            selectAnime()
            clickSearch()
        } validate {
            isGenericError()
        }
    }

    @Test
    fun noSeriesFoundErrorFromSearchShowsError() {
        launchActivity()

        activity {
            goToSearch()
        }
        search {
            searchTerm(NO_RESULTS_ERROR)
            selectAnime()
            clickSearch()
        } validate {
            isNoSeriesFoundError()
        }
    }
}
