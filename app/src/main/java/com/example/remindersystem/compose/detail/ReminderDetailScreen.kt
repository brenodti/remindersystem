package com.example.remindersystem.compose.detail

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.remindersystem.model.Reminder

@Composable
fun ReminderDetailScreen(reminder: Reminder) {
    ReminderDetailCard(reminder.name, reminder.imageUrl, Modifier.fillMaxSize())
}

@Composable
@OptIn(ExperimentalGlideComposeApi::class)
private fun ReminderDetailCard(
    name: String,
    imageUrl: String?,
    modifier: Modifier
) {
    /*Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(CornerSize(12.dp)))
        )*/
    Column(modifier = modifier) {
        GlideImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(CornerSize(12.dp)))
        )
        Text(
            text = name,
            fontSize = 20.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(start = 8.dp)
                .border(
                    width = 1.dp,
                    shape = RoundedCornerShape(CornerSize(12.dp)),
                    color = Color.Black
                )
                .padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ReminderDetailCardPreview() {
    ReminderDetailCard(
        name = "Exemplo de reminder",
        imageUrl = null,
        modifier = Modifier.fillMaxSize()
    )
}