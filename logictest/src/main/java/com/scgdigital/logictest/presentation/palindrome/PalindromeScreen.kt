package com.scgdigital.logictest.presentation.palindrome

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.scgdigital.common.presentation.resource.SpaceSize
import com.scgdigital.common.presentation.resource.TextSize
import com.scgdigital.resource.R

@Composable
fun PalindromeScreen(
    state: PalindromeViewState,
    onPalindromeClick: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.azure_buffet)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PalindromeLayout(state, onPalindromeClick)
    }
}

@Composable
fun PalindromeLayout(
    state: PalindromeViewState,
    onPalindromeClick: (String) -> Unit,
) {
    var word by remember { mutableStateOf("") }
    var alpha by remember { mutableStateOf(0.4f) }
    var isTextChange by remember { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = word,
            onValueChange = { newValue ->
                if (newValue.indexOf(" ") < 0) {
                    word = newValue.filter { it.isLetterOrDigit() }
                    alpha = if (word.isNotEmpty()) 1.0f else 0.4f
                    isTextChange = true
                }
            },
            modifier = Modifier
                .padding(SpaceSize.L.value)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text(stringResource(id = R.string.palindrome_enter_the_word)) },
            isError = word.isEmpty()
        )

        Button(
            enabled = alpha > 0.4f,
            onClick = {
                onPalindromeClick(word)
                isTextChange = false
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
                text = stringResource(id = R.string.palindrome_check),
                fontSize = TextSize.L.value
            )
        }

        if (!isTextChange) {
            val answer = if (state.isPalindrome) stringResource(id = R.string.palindrome_is_true, word)
                        else stringResource(id = R.string.palindrome_is_false, word)
            Text(
                modifier = Modifier
                    .padding(SpaceSize.S.value),
                text = answer,
                fontSize = TextSize.L.value
            )
        }
    }
}
