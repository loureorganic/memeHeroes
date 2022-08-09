package com.example.marvelheroes.screens.character.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.marvelheroes.R
import com.example.marvelheroes.model.comics.ResultComics
import com.example.marvelheroes.repositories.network.api.models.characterModel.ResultCharacters
import com.example.marvelheroes.model.series.ResultSeries
import com.example.marvelheroes.screens.character.viewmodel.ViewModelCharacter
import com.example.marvelheroes.screens.home.ui.utils.loadPicture
import com.example.marvelheroes.screens.search.ui.ui.theme.darkBlue
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsRow(pagerState: PagerState) {
    val tabTextAndIcon = listOf(
        "About", "Series", "Comics"
    )

    val coroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .wrapContentWidth(Alignment.CenterHorizontally),
            backgroundColor = darkBlue, contentColor = darkBlue, divider = {
                TabRowDefaults.Divider(
                    thickness = 3.dp,
                    color = Color.Black
                )
            }, indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                    height = 3.dp,
                    color = Color.Yellow
                )
            }) {
            tabTextAndIcon.forEachIndexed { index, pair ->

                val selected = pagerState.currentPage == index

                Tab(
                    modifier = Modifier.wrapContentWidth(Alignment.CenterHorizontally),
                    selected = selected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    enabled = true,
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                            .height(30.dp)
                            .fillMaxWidth(), verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally

                    ) {
                        Text(pair, color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerContent(
    pagerState: PagerState,
    character: ResultCharacters,
    viewModelCharacter: ViewModelCharacter
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .defaultMinSize(0.dp, 240.dp)
            .background(darkBlue)
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> {
                    AboutSection(character)
                }
                1 -> {
                    SeriesSection(viewModelCharacter)
                }
                2 -> {
                    ComicsSection(viewModelCharacter)
                }
            }
        }
    }
}

@Composable
fun AboutSection(character: ResultCharacters) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBlue)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            character.description,
            color = Color.White,
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun SeriesSection(viewModelCharacter: ViewModelCharacter) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            FlowRow(modifier = Modifier.background(darkBlue)) {
                viewModelCharacter.getValuesOfMarvelListCharacterSeries()
                viewModelCharacter.marvelListCharacterSeries.value?.let {  it ->
                    it.forEach { result ->
                        GridItem(data = result)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GridItem(data: List<ResultSeries>) {
    data.map { item ->
        Card(
            modifier = Modifier
                .width(130.dp)
                .height(170.dp)
                .padding(4.dp),
        ) {
            val url = item.thumbnail.path.replaceRange(
                4,
                4,
                "s"
            ) + "/standard_amazing.${item.thumbnail.extension}"
            val image = loadPicture(
                url = url,
                defaultImage = R.drawable.marvel_heroes_placeholder
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}
@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun GridItemComics(data: List<ResultComics>) {
    data.map { item ->
        Card(
            modifier = Modifier
                .width(130.dp)
                .height(170.dp)
                .padding(4.dp),
        ) {
            val url = item.thumbnail.path.replaceRange(
                4,
                4,
                "s"
            ) + "/standard_amazing.${item.thumbnail.extension}"
            val image = loadPicture(
                url = url,
                defaultImage = R.drawable.marvel_heroes_placeholder
            ).value
            image?.let { img ->
                Image(
                    bitmap = img.asImageBitmap(),
                    contentDescription = "Description",
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
fun ComicsSection(viewModelCharacter: ViewModelCharacter) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        item {
            FlowRow(modifier = Modifier.background(darkBlue)) {
                viewModelCharacter.getValuesOfMarvelListCharacterComics()
                viewModelCharacter.marvelListCharacterComics.value?.let {  it ->
                    it.forEach { result ->
                        GridItemComics(data = result)
                    }
                }
            }
        }
    }
}