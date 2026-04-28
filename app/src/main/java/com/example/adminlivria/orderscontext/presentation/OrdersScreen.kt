@file:OptIn(androidx.compose.foundation.ExperimentalFoundationApi::class)

package com.example.adminlivria.orderscontext.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
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
import com.example.adminlivria.common.ui.theme.*
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
        // UNIFICAMOS TODO EN UNA LAZYCOLUMN
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(LivriaWhite)
        ) {

            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 10.dp)
                ) {
                    Text(
                        "Order Management",
                        textAlign = TextAlign.Center,
                        color = LivriaOrange,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp
                        ),
                        modifier = Modifier.fillMaxWidth().padding(6.dp)
                    )
                    Text(
                        "Manage and analyze all Livria orders",
                        textAlign = TextAlign.Center,
                        color = LivriaBlack,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 18.dp)
                    )

                    val orders = state.data ?: emptyList()
                    val totalOrders = orders.size
                    val totalRevenue = orders.sumOf { it.total }
                    val averageOrderValue = if (orders.isNotEmpty()) totalRevenue / totalOrders else 0.0
                    val pendingOrders = orders.count { it.status.equals("pending", ignoreCase = true) }
                    val completedOrders = orders.count { it.status.equals("delivered", ignoreCase = true) }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        MiniStats(modifier = Modifier.weight(1f), "Total Orders", totalOrders.toString())
                        MiniStats(modifier = Modifier.weight(1f), "Total Revenue", "S/ %.2f".format(totalRevenue))
                        MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "S/ %.2f".format(averageOrderValue))
                    }

                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                        MiniStats(modifier = Modifier.weight(1f), "Pending Orders", pendingOrders.toString())
                        MiniStats(modifier = Modifier.weight(1f), "Completed Orders", completedOrders.toString())
                    }

                    HorizontalDivider(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                        thickness = 2.dp,
                        color = LivriaSoftCyan
                    )
                }
            }

            stickyHeader {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = LivriaWhite,
                    shadowElevation = 2.dp
                ) {
                    Column(modifier = Modifier.padding(top = 8.dp)) {
                        SearchNFilterCard(viewModel, search)

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            val headerStyle = MaterialTheme.typography.labelMedium.copy(
                                color = LivriaNavyBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                            Text("CODE", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = headerStyle)
                            Text("NAME", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = headerStyle)
                            Text("DATE", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = headerStyle)
                            Text("TOTAL", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = headerStyle)
                            Text("STATUS", modifier = Modifier.weight(1f), textAlign = TextAlign.Center, style = headerStyle)
                        }
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                            thickness = 2.dp,
                            color = LivriaSoftCyan
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }

            state.data?.let { orders ->
                items(orders) { order ->
                    OrderRow(
                        order = order,
                        onClick = {
                            navController.navigate("${NavDestinations.ORDER_DETAIL_ROUTE}/${order.id}")
                        }
                    )
                }
            }

            item {
                if (state.isLoading) {
                    Box(Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator(color = LivriaBlue)
                    }
                }
                if (state.message.isNotEmpty()) {
                    Text(
                        text = state.message,
                        modifier = Modifier.fillMaxWidth().padding(24.dp),
                        textAlign = TextAlign.Center,
                        color = Color.Red
                    )
                }
            }
        }
    }
}


@Composable
fun MiniStats(modifier: Modifier = Modifier, title: String, value: String) {
    OutlinedCard(
        modifier = modifier.padding(6.dp).height(75.dp),
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        border = BorderStroke(1.dp, LivriaLightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = title, textAlign = TextAlign.Center, color = LivriaBlue,
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold, fontSize = 11.sp))
            Text(text = value, textAlign = TextAlign.Center, color = LivriaBlack,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = 11.sp),
                modifier = Modifier.padding(top = 10.dp))
        }
    }
}

@Composable
fun SearchNFilterCard(viewModel: OrdersViewModel, search: String) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(Modifier.padding(bottom = 10.dp)) {
            Text(
                text = "ORDERS",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = AsapCondensedFontFamily, color = LivriaAmber,
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp, letterSpacing = 2.sp
                ),
                modifier = Modifier.padding(start = 24.dp, top = 18.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = search,
                    onValueChange = { viewModel.onSearchEntered(it) },
                    placeholder = { Text("Enter Code or Customer Name", fontSize = 12.sp) },
                    modifier = Modifier.weight(1f).heightIn(min = 56.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = LivriaBlue, unfocusedBorderColor = LivriaLightGray
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = { viewModel.onSearchClicked() },
                    modifier = Modifier.size(56.dp).border(1.dp, LivriaLightGray, MaterialTheme.shapes.small)
                ) {
                    Icon(painterResource(id = R.drawable.ic_search), contentDescription = null, tint = LivriaBlue)
                }
            }
        }
    }
}

@Composable
fun OrderRow(order: Order, onClick: () -> Unit) {
    val formattedDate = order.date.atZone(ZoneId.systemDefault())
        .format(DateTimeFormatter.ofPattern("dd/MM/yy"))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 18.dp)
            .border(1.dp, LivriaLightGray, shape = MaterialTheme.shapes.small)
            .clickable { onClick() }
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("#${order.code}", color = LivriaOrange, fontWeight = FontWeight.SemiBold, fontSize = 12.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(order.userFullName, fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(formattedDate, fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text("S/ ${order.total}", fontSize = 11.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
        Text(order.status.uppercase(), color = if (order.status == "pending") LivriaOrange else LivriaBlue,
            fontWeight = FontWeight.Bold, fontSize = 10.sp, modifier = Modifier.weight(1f), textAlign = TextAlign.Center)
    }
}