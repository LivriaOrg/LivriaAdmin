@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.adminlivria.bookcontext.presentation

import androidx.navigation.NavController
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material3.*
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.CardDefaults.cardElevation
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adminlivria.R
import com.example.adminlivria.bookcontext.domain.BookFilters
import com.example.adminlivria.bookcontext.presentation.components.BookGridTile
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.common.ui.theme.*

@Composable
fun BooksScreen(
    navController: NavController,
    viewModel: BooksManagementViewModel = viewModel(factory = BooksViewModelFactory(LocalContext.current))
) {
    // ✅ Detectar cambios y refrescar automáticamente
    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val refreshFlow = remember(savedStateHandle) {
        savedStateHandle?.getStateFlow("refresh_books", false)
    }
    val shouldRefresh by (refreshFlow?.collectAsState(initial = false) ?: remember { mutableStateOf(false) })

    LaunchedEffect(shouldRefresh) {
        if (shouldRefresh) {
            viewModel.refresh()
            savedStateHandle?.set("refresh_books", false)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) viewModel.refresh()
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    // -------- UI principal --------
    val search by viewModel.search.collectAsState()
    val stats by viewModel.stats.collectAsState()
    val books by viewModel.books.collectAsState()
    val genres by viewModel.genres.collectAsState()
    val languages by viewModel.languages.collectAsState()
    val currentFilters by viewModel.filters.collectAsState()

    var showFilters by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Column(Modifier.fillMaxWidth().padding(paddingValues)) {

            Column(Modifier.fillMaxWidth().padding(horizontal = 24.dp)) {
                Text(
                    "Livria Management",
                    textAlign = TextAlign.Center,
                    color = LivriaOrange,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold, fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth().padding(6.dp)
                )
                Text(
                    "Statistics",
                    textAlign = TextAlign.Center,
                    color = LivriaBlack,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 18.dp)
                )

                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    MiniStats("Total Books", stats.totalBooks.toString(), Modifier.weight(1f))
                    MiniStats("Total Genres", stats.totalGenres.toString(), Modifier.weight(1f))
                    MiniStats("Average Price", "S/ %.2f".format(stats.averagePrice), Modifier.weight(1f))
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    MiniStats("Books in Stock", stats.booksInStock.toString(), Modifier.weight(1f))
                    MiniStats("Most Reviewed", stats.totalBooksRow2.toString(), Modifier.weight(1f))
                }

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                    thickness = 2.dp, color = LivriaSoftCyan
                )
            }

            SearchNFilterCardBooks(
                search = search,
                onSearchChange = { viewModel.onSearch(it) },
                onOpenFilters = { showFilters = true }
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
                        onView = { navController.navigate("${NavDestinations.BOOK_DETAIL_ROUTE}/${book.id}") },
                        onStock = { navController.navigate("${NavDestinations.INVENTORY_INDIVIDUAL_STOCK_ROUTE}/${book.id}") }
                    )
                }
            }
        }
    }

    if (showFilters) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { showFilters = false },
            sheetState = sheetState
        ) {
            FiltersSheet(
                genres = genres,
                languages = languages,
                initial = currentFilters,
                onApply = {
                    viewModel.applyFilters(it)
                    showFilters = false
                },
                onClear = {
                    viewModel.clearFilters()
                    showFilters = false
                }
            )
        }
    }
}

@Composable
private fun MiniStats(title: String, value: String, modifier: Modifier = Modifier) {
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
                text = title, textAlign = TextAlign.Center, color = LivriaBlue,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily, fontWeight = FontWeight.SemiBold, fontSize = 12.sp
                )
            )
            Text(
                text = value, textAlign = TextAlign.Center, color = LivriaBlack,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily, fontWeight = FontWeight.Normal, fontSize = 11.sp
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}

@Composable
private fun SearchNFilterCardBooks(
    search: String,
    onSearchChange: (String) -> Unit,
    onOpenFilters: () -> Unit
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
                        color = LivriaAmber, fontWeight = FontWeight.SemiBold, fontSize = 20.sp, letterSpacing = 2.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(2f)
                )
                IconButton(
                    onClick = onOpenFilters,
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
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp)
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(color = LivriaBlack),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = LivriaWhite, unfocusedContainerColor = LivriaWhite,
                        disabledContainerColor = LivriaWhite, focusedTextColor = LivriaBlack,
                        unfocusedTextColor = LivriaBlack, focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray, focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue, cursorColor = LivriaBlue
                    ),
                    modifier = Modifier.weight(1f).padding(horizontal = 30.dp, vertical = 15.dp)
                )

                IconButton(
                    onClick = { },
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
