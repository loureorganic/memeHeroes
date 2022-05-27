package com.example.marvelheroes.screens.home.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.marvelheroes.screens.home.ui.compose.CharacterRowList
import com.example.marvelheroes.screens.home.ui.compose.MainAppBar
import com.example.marvelheroes.screens.home.ui.ui.theme.MarvelHeroesTheme
import com.example.marvelheroes.screens.home.ui.utils.AutoSliding
import com.example.marvelheroes.screens.home.ui.utils.SearchWidgetState
import com.example.marvelheroes.screens.home.viewmodel.ViewModelHome
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelHome: ViewModelHome

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelHome.getCharactersForSliding()
        viewModelHome.getAllComics()

        val searchWidgetState by viewModelHome.searchWidgetState
        val searchTextState by viewModelHome.searchTextState

        viewModelHome.marvelListAllComics.observe(this) { list2   ->
            viewModelHome.marvelList.observe(this) { list ->
                setContent {
                    MarvelHeroesTheme {
                        Surface(color = Color.White) {
                            Column(modifier = Modifier
                                .verticalScroll(rememberScrollState())) {
                                MainAppBar(
                                    searchWidgetState = searchWidgetState,
                                    searchTextState = searchTextState,
                                    onTextChange = {
                                        viewModelHome.updateSearchTextState(newValue = it)
                                    },
                                    onCloseClicked = {
                                        viewModelHome.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                                    },
                                    onSearchClicked = {
                                        Log.d("Searched Text", it)
                                    },
                                    onSearchTriggered = {
                                        viewModelHome.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                                    }
                                )
                                AutoSliding(list)
                                Spacer(modifier = Modifier.height(8.dp))
                                CharacterRowList(cards = list2)
                                Spacer(modifier = Modifier.height(40.dp))
                            }

                        }
                    }
                }
            }
        }
    }
}

