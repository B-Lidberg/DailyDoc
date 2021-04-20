package com.lid.dailydoc.authorization

/** Not Implemented Yet. Still testing
 *  May try to stick to Single Activity or decide on building a Login Activity
 *  Firebase Auth / Google Sign in?
 */

import android.os.Build
import android.util.AttributeSet
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.ContentLoadingProgressBar
import com.lid.dailydoc.R
import com.lid.dailydoc.viewmodels.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun LoginScreen(
    vm: LoginViewModel,
    toMain: () -> Unit,
    toSplash: () -> Unit,
) {
    val launcher = rememberLauncherForActivityResult(LoginWithGoogle()) {
        if (it != null) {
            vm.loginWithGoogle(it)
        }
    }
    val loading by vm.loading.observeAsState(false)
    val signIn by vm.signedIn.observeAsState(vm.signedIn.value!!)

    LaunchedEffect(signIn) {
        if (signIn) {
            delay(2500)
            toSplash()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            toMain, modifier = Modifier
                .align(Alignment.End)
                .padding(top = 16.dp, end = 16.dp)
        )
        {
            Text(guestSignInText, color = Color.White)
        }
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
            CoroutineScope(Dispatchers.Main).launch {
                launcher.launch()
            }
        }
        ProgressBar(signIn)
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
fun ProgressBar(signedIn: Boolean) {
    if (signedIn) {
        Column() {
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .height(20.dp))
            Text("(Delay set to feature progress bar)")
        }

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

