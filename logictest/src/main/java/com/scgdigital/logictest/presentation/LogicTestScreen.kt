package com.scgdigital.logictest.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.resource.TextSize
import com.scgdigital.logictest.LogicTestViewState
import com.scgdigital.resource.R

@Composable
fun LogicTestScreen(
    state: LogicTestViewState,
    onFindMiddleIndexClick: () -> Unit,
    onPalindromeClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LogicTestLayout(onFindMiddleIndexClick, onPalindromeClick)
    }
}

@Composable
fun LogicTestLayout(
    onFindMiddleIndexClick: () -> Unit,
    onPalindromeClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onFindMiddleIndexClick() },
            shape = RoundedCornerShape(SpaceSize.XS.value),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.kermit_green),
                contentColor = colorResource(id = R.color.white)),
            modifier = Modifier
                .padding(SpaceSize.L.value)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(SpaceSize.S.value)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.logictest_txt_find_middle_index),
                fontSize = TextSize.L.value,
                textAlign = TextAlign.Start
            )
        }

        Button(
            onClick = { onPalindromeClick() },
            shape = RoundedCornerShape(SpaceSize.XS.value),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.kermit_green),
                contentColor = colorResource(id = R.color.white)),
            modifier = Modifier
                .padding(SpaceSize.L.value)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .padding(SpaceSize.S.value)
                    .fillMaxWidth(),
                text = stringResource(id = R.string.logictest_txt_palindrome),
                fontSize = TextSize.L.value,
                textAlign = TextAlign.Start
            )
        }
    }
}
