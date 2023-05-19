package com.scgdigital.news.presentation.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.rememberAsyncImagePainter
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.resource.TextSize
import com.scgdigital.news.domain.model.Article
import com.scgdigital.resource.R

@Composable
fun NewsDetailScreen(
    state: NewsDetailViewState,
    article: Article,
    convertDateTime: (String) -> String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NewsDetailLayout(state, article, convertDateTime)
    }
}

@Composable
fun NewsDetailLayout(
    state: NewsDetailViewState,
    article: Article,
    convertDateTime: (String) -> String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
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
            modifier = Modifier.fillMaxWidth()
                .padding(
                start = SpaceSize.M.value,
                end = SpaceSize.M.value,
                bottom = SpaceSize.M.value
            ),
            text = article.description.toString(),
            fontSize = TextSize.L.value,
        )
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(
                start = SpaceSize.M.value,
                end = SpaceSize.M.value,
                bottom = SpaceSize.M.value
            ),
            text = stringResource(id = R.string.news_updated, convertDateTime(article.publishedAt.toString())),
            fontSize = TextSize.L.value,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}