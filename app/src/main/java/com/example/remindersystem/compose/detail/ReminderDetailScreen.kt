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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.remindersystem.R
import com.example.remindersystem.model.Reminder

@Composable
fun ReminderDetailScreen(reminder: Reminder) {
    ReminderDetailCard(
        reminder.name,
        reminder.imageUrl,
        Modifier.fillMaxSize()
    )
}

@Composable
private fun ReminderDetailCard(
    name: String,
    imageUrl: String?,
    modifier: Modifier
) {
    Column(modifier = modifier) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            placeholder = painterResource(id = R.drawable.loading),
            error = painterResource(id = R.drawable.error),
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
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
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
@Preview(showBackground = true, backgroundColor = 0x808080)
fun ReminderDetailCardPreview() {
    ReminderDetailCard(
        name = "Exemplo de reminder",
        imageUrl = null,
        modifier = Modifier.fillMaxWidth()
    )
}