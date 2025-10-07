package com.example.pocoyo

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.pocoyo.ui.theme.PocoyoTheme

data class Message(val author: String, val body: String)


object SampleData {
    val conversationSample = listOf(
        Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!"),
        Message("Lexi", "Less code, powerful tools, and intuitive Kotlin APIs :)"),
        Message("Lexi", "It's available from API 21+ :)"),
        Message("Lexi", "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"),
        Message("Lexi", "Android Studio next version’s name is Arctic Fox."),
        Message("Lexi", "This is a long message to demonstrate the expansion feature. When you click on me, you'll see all this text instead of just the first line! Jetpack Compose handles all the sizing and color changes smoothly, thanks to the animation modifiers and state management.")
    )
}

// -------------------------------------------------------------
// 🔹 Actividad principal — Donde se muestra la interfaz
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PocoyoTheme {
                Conversation(SampleData.conversationSample)
            }
        }
    }
}


@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            MessageCard(msg = message)
        }
    }
}

@Composable
fun MessageCard(msg: Message) {
    var isExpanded by remember { mutableStateOf(false) }
    val surfaceColor by animateColorAsState(
        targetValue = if (isExpanded)
            MaterialTheme.colorScheme.primaryContainer
        else
            MaterialTheme.colorScheme.surface,
        label = "SurfaceColor"
    )


    Row(
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable { isExpanded = !isExpanded }
    ) {

        Image(
            painter = painterResource(R.drawable.pokemon),

            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {

            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(4.dp))


            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                tonalElevation = 1.dp,
                color = surfaceColor,
                modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(8.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    PocoyoTheme {
        Conversation(SampleData.conversationSample)
    }
}