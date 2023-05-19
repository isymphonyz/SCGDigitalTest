package com.scgdigital.news.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.resource.TextSize
import com.scgdigital.news.domain.model.Article
import com.scgdigital.resource.R

@Composable
fun NewsScreen(
    state: NewsViewState,
    performQuery: (String) -> Unit,
    onNewsClick: (Article) -> Unit,
    convertDateTime: (String) -> String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NewsLayout(state, performQuery, onNewsClick, convertDateTime)
    }
}

@Composable
fun NewsLayout(
    state: NewsViewState,
    performQuery: (String) -> Unit,
    onNewsClick: (Article) -> Unit,
    convertDateTime: (String) -> String,
) {
    SearchAppBar(performQuery)

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        if (!state.articles.isNullOrEmpty()) {
            itemsIndexed(state.articles) { index, article ->
                NewsAdapter(article = article, onNewsClick, convertDateTime)
                if (index < state.articles.size-1)
                    Divider(color = colorResource(id = R.color.dark_blue), thickness = SpaceSize.LINE.value)
            }
        }
    }
}

@Composable
fun SearchAppBar(
    performQuery: (query: String) -> Unit
) {
    var query by remember { mutableStateOf("") }
    var showClearIcon by remember { mutableStateOf(false) }

    if (query.isEmpty()) {
        showClearIcon = false
    } else if (query.isNotEmpty()) {
        showClearIcon = true
    }

    OutlinedTextField(
        value = query,
        onValueChange = { onQueryChanged ->
            query = onQueryChanged
            if (onQueryChanged.isNotEmpty()) {
                performQuery(query)
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                tint = MaterialTheme.colors.onBackground,
                contentDescription = "Search Icon"
            )
        },
        trailingIcon = {
            if (showClearIcon) {
                IconButton(onClick = { query = "" }) {
                    Icon(
                        imageVector = Icons.Rounded.Clear,
                        tint = MaterialTheme.colors.onBackground,
                        contentDescription = "Clear Icon"
                    )
                }
            }
        },
        label = { Text(stringResource(id = R.string.news_search)) },
        maxLines = 1,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
        textStyle = MaterialTheme.typography.subtitle1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = Modifier
            .padding(SpaceSize.L.value)
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background, shape = RectangleShape)
    )
}

@Composable
fun NewsAdapter(
    article: Article,
    onNewsClick: (Article) -> Unit,
    convertDateTime: (String) -> String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet))
            .clickable(
                onClick = { onNewsClick(article) }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    )  {
        Image(
            painter = rememberAsyncImagePainter(article.urlToImage),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .height(SpaceSize.XXXXXXL.value)
                .fillMaxWidth()
                .padding(
                    top = SpaceSize.M.value
                ),
        )
        Text(
            modifier = Modifier
                .padding(SpaceSize.M.value)
                .fillMaxWidth(),
            text = article.title.toString(),
            color = colorResource(id = R.color.greyish_brown),
            fontSize = TextSize.XL.value,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(
                start = SpaceSize.M.value,
                end = SpaceSize.M.value,
                bottom = SpaceSize.M.value
            ),
            text = article.description.toString(),
            fontSize = TextSize.L.value,
            maxLines = 6,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier.padding(
                start = SpaceSize.M.value,
                end = SpaceSize.M.value,
                bottom = SpaceSize.M.value
            ),
            text = stringResource(id = R.string.news_updated, convertDateTime(article.publishedAt.toString())),
            fontSize = TextSize.M.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}