package com.example.adminlivria.orderscontext.presentation.orderdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.adminlivria.common.ui.theme.AsapCondensedFontFamily
import com.example.adminlivria.common.ui.theme.LivriaAmber
import com.example.adminlivria.common.ui.theme.LivriaBlack
import com.example.adminlivria.common.ui.theme.LivriaBlue
import com.example.adminlivria.common.ui.theme.LivriaLightGray
import com.example.adminlivria.common.ui.theme.LivriaNavyBlue
import com.example.adminlivria.common.ui.theme.LivriaOrange
import com.example.adminlivria.common.ui.theme.LivriaSoftCyan
import com.example.adminlivria.common.ui.theme.LivriaWhite
import com.example.adminlivria.orderscontext.domain.Item
import com.example.adminlivria.orderscontext.domain.Order
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@Composable
fun OrderDetailScreen(
    orderId: Int,
    viewModel: OrderDetailViewModel = viewModel(
        factory = OrderDetailViewModelFactory(LocalContext.current, orderId)
    )
) {
    val state = viewModel.state.collectAsState().value

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 10.dp)
        ) {
            when {
                state.isLoading -> {
                    Text("Loading order details...", textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }

                state.message.isNotEmpty() -> {
                    Text(state.message, color = Color.Red, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
                }

                state.data != null -> {
                    val order = state.data!!

                    // --- ORDER DETAILS ---
                    SectionTitle("ORDER DETAILS")
                    DetailRow("ORDER CODE", "#${order.code}", LivriaOrange)
                    DetailRow("ORDER DATE", order.date
                        .atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("MMMM d, yyyy")), LivriaBlue
                    )
                    DetailRow("ORDER STATUS", order.status.capitalize(), LivriaSoftCyan)
                    DetailRow("ORDER VALUE", "S/ ${order.total}", LivriaOrange)

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- CUSTOMER INFORMATION ---
                    SectionTitle("CUSTOMER INFORMATION")
                    DetailRow("CUSTOMER NAME", order.userFullName, LivriaBlue)
                    DetailRow("CUSTOMER EMAIL", order.userEmail, LivriaSoftCyan)
                    DetailRow("RECIPIENT NAME", order.recipientName, LivriaOrange)
                    DetailRow("RECIPIENT PHONE", order.userPhone, LivriaBlue)

                    Spacer(modifier = Modifier.height(16.dp))

                    // --- ORDER ITEMS ---
                    SectionTitle("ORDER ITEMS")
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(order.items) { item ->
                            OrderItemCard(item)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))

                    // --- UPDATE STATUS ---
                    SectionTitle("UPDATE STATUS")

                    var expanded by remember { mutableStateOf(false) }
                    val statusOptions = listOf("Pending", "In Progress", "Delivered")
                    val selectedStatus = remember { mutableStateOf(order.status) }

                    Box {
                        OutlinedButton(
                            onClick = { expanded = true },
                            border = BorderStroke(1.dp, LivriaBlue)
                        ) {
                            Text(
                                text = selectedStatus.value.uppercase(),
                                color = LivriaBlue,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            statusOptions.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(
                                        text = option
                                    ) },
                                    onClick = {
                                        expanded = false
                                        selectedStatus.value = option
                                        viewModel.updateOrderStatus(order.id, option.lowercase())
                                    }
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        color = LivriaAmber,
        fontFamily = AsapCondensedFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        letterSpacing = 2.sp,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

@Composable
fun DetailRow(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            color = color,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = value,
            color = LivriaBlack,
            fontSize = 14.sp,
            modifier = Modifier.padding(top = 2.dp)
        )
    }
}

@Composable
fun OrderItemCard(item: Item) {
    Card(
        modifier = Modifier
            .width(180.dp)
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = LivriaWhite),
        border = BorderStroke(1.dp, LivriaLightGray)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            AsyncImage(
                model = item.bookCover,
                contentDescription = item.bookTitle,
                modifier = Modifier
                    .height(100.dp)
                    .padding(10.dp)
            )
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = item.bookTitle,
                    color = LivriaBlack,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                Text(
                    text = "S/ ${item.bookPrice}",
                    color = LivriaBlue,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}
