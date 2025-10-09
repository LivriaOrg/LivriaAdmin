package com.example.adminlivria.bookcontext.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import com.example.adminlivria.bookcontext.domain.Book
import com.example.adminlivria.common.ui.theme.*

@Composable
fun BookGridTile(
    book: Book,
    onView: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            AsyncImage(
                model = book.cover,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(3f / 4f) // 3:4
                    .clip(RoundedCornerShape(12.dp))
            )

            Text(
                text = book.title,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.SemiBold
                ),
                color = LivriaBlack,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = book.author,
                style = MaterialTheme.typography.bodySmall,
                color = LivriaBlue,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "S/ ${"%.2f".format(book.price)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = LivriaOrange
                )
                Button(
                    onClick = onView,
                    colors = ButtonDefaults.buttonColors(containerColor = LivriaBlue),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) { Text("VIEW") }
            }
        }
    }
}
