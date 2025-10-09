package com.example.adminlivria.bookcontext.presentation
import androidx.navigation.NavController

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.adminlivria.R
import com.example.adminlivria.bookcontext.domain.Book
import com.example.adminlivria.common.ui.theme.*
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import com.example.adminlivria.bookcontext.presentation.components.BookGridTile
import com.example.adminlivria.common.navigation.NavDestinations

@Composable
fun BooksScreen( navController: NavController,
    viewModel: BooksManagementViewModel = viewModel(factory = BooksViewModelFactory(LocalContext.current))
) {
    val search by viewModel.search.collectAsState()
    val stats by viewModel.stats.collectAsState()
    val books by viewModel.books.collectAsState()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "Livria Management",
                    textAlign = TextAlign.Center,
                    color = LivriaOrange,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                )
                Text(
                    "Statistics",
                    textAlign = TextAlign.Center,
                    color = LivriaBlack,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 18.dp)
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    MiniStats(title = "Total Books", value = stats.totalBooks.toString(), modifier = Modifier.weight(1f))
                    MiniStats(title = "Total Genres", value = stats.totalGenres.toString(), modifier = Modifier.weight(1f))
                    MiniStats(title = "Average Price", value = "S/ %.2f".format(stats.averagePrice), modifier = Modifier.weight(1f))
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    MiniStats(title = "Total Books", value = stats.totalBooksRow1.toString(), modifier = Modifier.weight(1f))
                    MiniStats(title = "Total Books", value = stats.totalBooksRow2.toString(), modifier = Modifier.weight(1f))
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                    thickness = 2.dp,
                    color = LivriaSoftCyan
                )
            }


            SearchNFilterCardBooks(
                search = search,
                onSearchChange = { viewModel.onSearch(it) }
            )


            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 168.dp),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp),
                horizontalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(books, key = { it.id }) { book ->
                    BookGridTile(
                        book = book,
                        onView = { navController.navigate("${NavDestinations.BOOK_DETAIL_ROUTE}/${book.id}") }
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniStats(modifier: Modifier = Modifier, title: String, value: String) {
    OutlinedCard(
        modifier = modifier.padding(6.dp).height(80.dp).width(110.dp),
        colors = cardColors(containerColor = LivriaWhite),
        border = BorderStroke(1.dp, LivriaLightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = LivriaBlue,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
            )
            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = LivriaBlack,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 11.sp
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
private fun SearchNFilterCardBooks(
    search: String,
    onSearchChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        colors = cardColors(containerColor = LivriaWhite),
        elevation = cardElevation(defaultElevation = 10.dp)
    ) {
        Column {
            Row(Modifier.fillMaxWidth().padding(vertical = 18.dp)) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "BOOK COLLECTION",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AsapCondensedFontFamily,
                        color = LivriaAmber,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                IconButton(
                    onClick = {  },
                    modifier = Modifier.size(24.dp).background(Color.Transparent).weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filters",
                        tint = LivriaOrange,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = search,
                    onValueChange = onSearchChange,
                    label = {
                        Text(
                            text = "Enter a title to search",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = LivriaBlack),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LivriaWhite,
                        unfocusedContainerColor = LivriaWhite,
                        disabledContainerColor = LivriaWhite,
                        focusedTextColor = LivriaBlack,
                        unfocusedTextColor = LivriaBlack,
                        focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray,
                        focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue,
                        cursorColor = LivriaBlue
                    ),
                    modifier = Modifier.weight(1f).padding(horizontal = 30.dp, vertical = 15.dp)
                )

                IconButton(
                    onClick = {  },
                    modifier = Modifier.padding(end = 16.dp).size(24.dp).background(Color.Transparent)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = LivriaBlue,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun BookCard(
    book: Book,
    onView: () -> Unit
) {
    Card(
        colors = cardColors(containerColor = LivriaWhite),
        elevation = cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = book.cover,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp).clip(RoundedCornerShape(10.dp))
            )
            Spacer(Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    book.title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontFamily = AlexandriaFontFamily,
                        fontWeight = FontWeight.SemiBold
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = LivriaBlack
                )
                Text(
                    book.author,
                    style = MaterialTheme.typography.bodySmall,
                    color = LivriaBlue,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "S/ %.2f".format(book.price),
                    style = MaterialTheme.typography.bodySmall,
                    color = LivriaBlack
                )
            }
            Button(
                onClick = onView,
                colors = ButtonDefaults.buttonColors(containerColor = LivriaBlue),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text("VIEW")
            }
        }
    }
}
