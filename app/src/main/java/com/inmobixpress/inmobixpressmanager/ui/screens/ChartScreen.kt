package com.inmobixpress.inmobixpressmanager.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.inmobixpress.inmobixpressmanager.ui.components.*
import com.inmobixpress.inmobixpressmanager.ui.theme.*
import com.inmobixpress.inmobixpressmanager.ui.utils.toMoneyFormat

val viewData = DonutChartDataCollection(
    listOf(
        DonutChartData(1200.0f, Sapphire, title = "Food & Groceries"),
        DonutChartData(1500.0f, RobingEggBlue, title = "Rent"),
        DonutChartData(300.0f, MetallicYellow, title = "Gas"),
        DonutChartData(700.0f, OxfordBlue, title = "Online Purchases"),
        DonutChartData(300.0f, VividOrange, title = "Clothing")
    )
)

@Composable
fun ChartScreen() {
    Scaffold(
        topBar = {
            Text("Graficos",
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp))
        }
    ) { paddingValues ->
        DonutChart(Modifier.padding(paddingValues), data = viewData) { selected ->
            AnimatedContent(targetState = selected) {
                val amount = it?.amount ?: viewData.totalAmount
                val text = it?.title ?: "Total"

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("$${amount.toMoneyFormat(true)}",
                        style = moneyAmountStyle, color = PetroleumGray)
                    Text(text, style = itemTextStyle, color = PetroleumLightGray)
                }
            }
        }
    }
}