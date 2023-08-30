import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@Composable
fun ReminderDetailScreen(navController: NavController, imageUrl: String?, name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val imageHeight = with(LocalDensity.current) {
            (180.dp.toPx() * 0.75f).toDp()
        }
        ReminderImage(imageUrl, imageHeight)
        Spacer(modifier = Modifier.height(16.dp))
        ReminderName(name)
//        Spacer(modifier = Modifier.height(8.dp))
//        ReminderDescription(reminder.description)
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ReminderImage(imageUrl: String?, imageHeight: Dp) {
    GlideImage(
        model = imageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}

@Composable
fun ReminderName(name: String) {
    BasicTextField(
        value = name,
        onValueChange = {},
        textStyle = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
//            .align(Alignment.BottomCenter)
    )
}

@Composable
fun ReminderDescription(description: String) {
    BasicTextField(
        value = description,
        onValueChange = {},
        textStyle = TextStyle(fontSize = 16.sp),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = null
        ),
        modifier = Modifier.fillMaxWidth()
    )
}