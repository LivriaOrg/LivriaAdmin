package com.example.adminlivria.orderscontext.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.adminlivria.R
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrdersViewModel = viewModel(factory = OrdersViewModelFactory(LocalContext.current))
) {
    val state = viewModel.state.value
    val search = viewModel.search.value

    LaunchedEffect(Unit) {
        //viewModel.loadAllOrders()
    }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 24.dp)
            ) {
                Text(
                    "Order Management",
                    textAlign = TextAlign.Center,
                    color = LivriaOrange,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                        .padding(6.dp)
                )
                Text(
                    "Manage and analyze all Livria orders",
                    textAlign = TextAlign.Center,
                    color = LivriaBlack,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
                    ),
                    modifier = Modifier.fillMaxWidth().padding(top = 10.dp, bottom = 18.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MiniStats(modifier = Modifier.weight(1f), "Total Orders", "chvr")
                    MiniStats(modifier = Modifier.weight(1f), "Total Revenue", "chvr")
                    MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "chvr")
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MiniStats(modifier = Modifier.weight(1f), "Pending Orders", "chvr")
                    MiniStats(modifier = Modifier.weight(1f), "Completed Orders", "chvr")
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 18.dp),
                    thickness = 2.dp,
                    color = LivriaSoftCyan
                )
            }

            SearchNFilterCard(viewModel, search)

        }
    }
}

@Composable
fun MiniStats(
    modifier: Modifier = Modifier,
    title: String,
    value: String
) {
    OutlinedCard(
        modifier = Modifier
            .padding(6.dp)
            .height(80.dp)
            .width(110.dp),
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        border = BorderStroke(1.dp, LivriaLightGray)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
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
fun SearchNFilterCard(
    viewModel: OrdersViewModel,
    search: String
) {
    Card(
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp, 0.dp),
        colors = CardDefaults.cardColors(
            containerColor = LivriaWhite,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        )
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 18.dp)
            ) {
                Spacer(Modifier.weight(1f))
                Text(
                    text = "ORDERS",
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
                    onClick = {
                        viewModel.onSearchClicked()
                    },
                    modifier = Modifier
                        .size(24.dp)
                        .background(Color.Transparent)
                        .weight(1f)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = "Filters",
                        tint = LivriaOrange,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { viewModel.onSearchEntered(it) },
                    label = {
                        Text(
                            text = "Enter an Order Code or Customer Name",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 14.sp
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium.copy(
                        color = LivriaBlack
                    ),
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 30.dp, vertical = 15.dp)
                        .weight(4f)
                )
                Box(
                    Modifier.weight(1f)
                ){
                    IconButton(
                        onClick = {
                            viewModel.onSearchClicked()
                        },
                        modifier = Modifier
                            .size(48.dp)
                            .background(Color.Transparent)
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
}