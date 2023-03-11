package com.shiftkey.codingchallenge.design.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.shiftkey.codingchallenge.design.DesignDrawables
import com.shiftkey.codingchallenge.design.R
import com.shiftkey.codingchallenge.design.theme.*

@Composable
fun ScreenError(
    errorMessage: String,
    isRetryVisible: Boolean = true,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(SizeXXL),
            painter = painterResource(DesignDrawables.ic_error),
            tint = LightBlue,
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .padding(top = SizeS),
            text = errorMessage,
            style = MaterialTheme.typography.h4,
            color = Black
        )

        if (isRetryVisible) {
            Button(
                modifier = Modifier
                    .padding(top = SizeL),
                onClick = onRetry,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Gray249,
                    contentColor = Black
                )
            ) {
                Text(
                    text = stringResource(R.string.try_again),
                    style = MaterialTheme.typography.h4
                )
            }
        }
    }
}
