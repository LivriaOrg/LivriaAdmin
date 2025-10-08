package com.example.adminlivria.presentation.ordermanagement

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.wear.compose.material.Scaffold
import com.example.adminlivria.presentation.ui.theme.AlexandriaFontFamily
import com.example.adminlivria.presentation.ui.theme.LivriaBlack
import com.example.adminlivria.presentation.ui.theme.LivriaBlue
import com.example.adminlivria.presentation.ui.theme.LivriaLightGray
import com.example.adminlivria.presentation.ui.theme.LivriaWhite

@Composable
fun OrdersScreen(
    navController: NavController,
    viewModel: OrderManagementViewModel = viewModel()
) {
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.fillMaxWidth().padding(paddingValues)
        ) {

            Row {
                MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "chvr")
                MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "chvr")
                MiniStats(modifier = Modifier.weight(1f), "Average Order Value", "chvr")
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
            .height(80.dp)
            .width(120.dp),
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
                    fontSize = 14.sp
                )
            )
            Text(
                text = value,
                textAlign = TextAlign.Center,
                color = LivriaBlack,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontFamily = AlexandriaFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(top = 10.dp)
            )
        }
    }
}