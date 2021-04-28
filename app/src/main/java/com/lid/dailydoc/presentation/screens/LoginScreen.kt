package com.lid.dailydoc.presentation.screens


import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lid.dailydoc.R
import com.lid.dailydoc.data.authorization.LoginWithGoogle
import com.lid.dailydoc.data.extras.appName
import com.lid.dailydoc.data.extras.googleSignInText
import com.lid.dailydoc.data.extras.informationText
import com.lid.dailydoc.data.extras.summaryText
import com.lid.dailydoc.navigation.UiDrawerState
import com.lid.dailydoc.viewmodels.UserViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    vm: UserViewModel,
    uiState: MutableTransitionState<UiDrawerState>
) {
    val launcher = rememberLauncherForActivityResult(LoginWithGoogle()) {
        if (it != null) {
            vm.loginWithGoogle(it)
        }
    }
    val scope = rememberCoroutineScope()
    val loading by vm.loading.observeAsState(false)
    val signIn by vm.signedIn.observeAsState(vm.signedIn.value!!)

    LaunchedEffect(signIn) {
        if (signIn) {
//            uiState.targetState = UiDrawerState.LOADING
//            delay(2000)
            uiState.targetState = UiDrawerState.LOADING
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = appName,
            style = MaterialTheme.typography.h3,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
        Text(
            text = summaryText,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
        GoogleSignInOption(enabled = !loading) {
            scope.launch {
                launcher.launch()
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = informationText,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(24.dp)
        )
    }
}


@Composable
private fun GoogleSignInOption(enabled: Boolean = true, onClick: () -> Unit) {
    val modifier =
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = MaterialTheme.colors.onBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(10.dp)
            .background(MaterialTheme.colors.background)
            .clickable(enabled = enabled) { onClick() }
    SignInOption(
        textColor = MaterialTheme.colors.onBackground,
        icon = R.drawable.ic_google,
        modifier = modifier,
    )
}

@Composable
fun SignInOption(
    textColor: Color = Color.White,
    icon: Int = R.drawable.ic_google,
    modifier: Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
    ) {
        Image(
            modifier = Modifier.size(30.dp),
            painter = painterResource(id = icon),
            contentDescription = "Google Icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = googleSignInText,
            color = textColor,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

