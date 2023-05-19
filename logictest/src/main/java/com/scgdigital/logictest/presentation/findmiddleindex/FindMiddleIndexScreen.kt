package com.scgdigital.logictest.presentation.findmiddleindex

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.resource.TextSize
import com.scgdigital.resource.R

@Composable
fun FindMiddleIndexScreen(
    state: FindMiddleIndexViewState,
    onFindMiddleIndexClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FindMiddleIndexLayout(state, onFindMiddleIndexClick)
    }
}

@Composable
fun FindMiddleIndexLayout(
    state: FindMiddleIndexViewState,
    onFindMiddleIndexClick: (String) -> Unit,
) {
    var inputNumber by remember { mutableStateOf("") }
    var alpha by remember { mutableStateOf(0.4f) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = inputNumber,
            onValueChange = { newValue ->
                if (newValue.indexOf(",,") < 0 && newValue.indexOf(" ") < 0) {
                    inputNumber = newValue
                    alpha = if (inputNumber.isNotEmpty() && inputNumber.contains("[0-9]".toRegex())) 1.0f else 0.4f
                    state.index = -2
                }
            },
            modifier = Modifier
                .padding(SpaceSize.L.value)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            label = { Text(stringResource(id = R.string.find_middle_index_enter_numbers_separated_by_commas)) },
            isError = inputNumber.isEmpty()
        )

        Button(
            enabled = alpha > 0.4f,
            onClick = {
                onFindMiddleIndexClick(inputNumber)
            },
            shape = RoundedCornerShape(SpaceSize.XS.value),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.kermit_green),
                contentColor = colorResource(id = R.color.white)),
            modifier = Modifier
                .padding(SpaceSize.L.value)
                .fillMaxWidth()
                .alpha(alpha),
        ) {
            Text(
                modifier = Modifier
                    .padding(SpaceSize.S.value),
                text = stringResource(id = R.string.find_middle_index_find_index),
                fontSize = TextSize.L.value
            )
        }

        if (state.index > -2) {
            val answer = if (state.index > -1) {
                stringResource(id = R.string.find_middle_index_has_index, state.index)
            } else {
                stringResource(id = R.string.find_middle_index_has_no_index)
            }
            Text(
                modifier = Modifier
                    .padding(SpaceSize.S.value),
                text = answer,
                fontSize = TextSize.L.value
            )
        }
    }
}