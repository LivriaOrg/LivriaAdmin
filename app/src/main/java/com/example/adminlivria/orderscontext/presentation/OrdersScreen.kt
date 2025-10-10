package com.example.adminlivria.orderscontext.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.adminlivria.common.navigation.NavDestinations
import com.example.adminlivria.common.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.orderscontext.domain.Order
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrdersViewModel = viewModel(factory = OrdersViewModelFactory(LocalContext.current))
) {
    val state = viewModel.state.value
    val search = viewModel.search.value

    LaunchedEffect(Unit) {
        viewModel.loadAllOrders()
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

                val orders = state.data ?: emptyList()
                val totalOrders = orders.size
                val totalRevenue = orders.sumOf { it.total }
                val averageOrderValue = if (orders.isNotEmpty()) totalRevenue / totalOrders else 0.0
                val pendingOrders = orders.count { it.status.equals("pending", ignoreCase = true) }
                val completedOrders = orders.count { it.status.equals("delivered", ignoreCase = true) }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MiniStats(modifier = Modifier.weight(1f), "Total Orders", totalOrders.toString())
                    MiniStats(modifier = Modifier.weight(1f), "Total Revenue", "S/ %.2f".format(totalRevenue))
                    MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "S/ %.2f".format(averageOrderValue))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    MiniStats(modifier = Modifier.weight(1f), "Pending Orders", pendingOrders.toString())
                    MiniStats(modifier = Modifier.weight(1f), "Completed Orders", completedOrders.toString())
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 18.dp),
                    thickness = 2.dp,
                    color = LivriaSoftCyan
                )
            }

            SearchNFilterCard(viewModel, search)

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Encabezados
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("CODE",
                        color = LivriaNavyBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text("NAME",
                        color = LivriaNavyBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text("DATE",
                        color = LivriaNavyBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text("TOTAL",
                        color = LivriaNavyBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                    Text("STATUS",
                        color = LivriaNavyBlue,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center
                    )
                }
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 18.dp),
                    thickness = 2.dp,
                    color = LivriaSoftCyan
                )
                state.data?.let { orders ->
                    // Filas con LazyColumn para scroll
                    LazyColumn {
                        items(orders) { order ->
                            OrderRow(
                                order = order,
                                onClick = {
                                    navController.navigate("${NavDestinations.ORDER_DETAIL_ROUTE}/${order.id}")
                                }
                            )
                        }
                    }
                }
                if (state.isLoading) {
                    Text(
                        text = "Loading orders...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
                if (state.message.isNotEmpty()) {
                    Text(
                        text = state.message,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }

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
            .height(75.dp)
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
                    .padding(top = 18.dp)
            ) {
                Text(
                    text = "ORDERS",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontFamily = AsapCondensedFontFamily,
                        color = LivriaAmber,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        letterSpacing = 2.sp
                    ),
                    textAlign = TextAlign.Center
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 12.dp)
                    .padding(bottom = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { viewModel.onSearchEntered(it) },
                    placeholder = {
                        Text(
                            text = "Enter an Order Code or Customer Name",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 12.sp
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
                        unfocusedTextColor = LivriaBlue,
                        focusedIndicatorColor = LivriaBlue,
                        unfocusedIndicatorColor = LivriaLightGray,
                        focusedLabelColor = LivriaBlue,
                        unfocusedLabelColor = LivriaBlue,
                        cursorColor = LivriaBlue
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 56.dp)
                )

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = { viewModel.onSearchClicked() },
                    modifier = Modifier
                        .size(60.dp)
                        .background(Color.Transparent)
                        .border(
                            width = 1.dp,
                            color = LivriaLightGray,
                            shape = MaterialTheme.shapes.small
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "Search",
                        tint = LivriaBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        }
    }
}

@Composable
fun OrderRow(
    order: Order,
    onClick: () -> Unit
) {
    val formattedDate = order.date
        .atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd/MM/yy"))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 18.dp)
            .border(1.dp, LivriaLightGray, shape = MaterialTheme.shapes.small)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "#" + order.code,
            color = LivriaOrange,
            fontWeight = FontWeight.SemiBold,
            fontSize = 13.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = order.userFullName,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f))
        Text(
            text = formattedDate,
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f))
        Text(
            text = "S/ ${order.total}",
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
            modifier = Modifier.weight(1f))
        Text(
            text = order.status.uppercase(),
            color = if (order.status == "pending") LivriaOrange else LivriaBlue,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
    }
}