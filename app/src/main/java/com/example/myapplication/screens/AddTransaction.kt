
package com.example.myapplication.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionScreen( navController: NavController,
    onCancel: () -> Unit,
    onSave: (TransactionData) -> Unit
) {
    val dateFormatter = remember {
        SimpleDateFormat("EEE, dd/MM/yyyy", Locale.getDefault())
    }
    val formatted = dateFormatter.format(Date())


    var isExpense by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("Select Category") }
    var paymentMethod by remember { mutableStateOf("Cash") }
    var date by remember { mutableStateOf(Date()) }
    var description by remember { mutableStateOf("") }



        Column(
            modifier =
                Modifier.fillMaxSize()
                .padding(16.dp)
        ) {

            // Top Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCancel) {
                    Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                }
                Spacer(Modifier.weight(1f))
                Text("New Transaction", style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            @Composable
            fun SingleChoiceSegmentedButton(modifier: Modifier) {
                var selectedIndex by remember { mutableIntStateOf(0) }
                val options = listOf("Day", "Month", "Week")

                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = { Text(label) }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Amount field
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                leadingIcon = { Text("$") },
                placeholder = { Text("0.00") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Category row
            ListItem(
                headlineContent = { Text("Category") },
                supportingContent = { Text(category) },
                trailingContent = { Icon(Icons.Default.KeyboardArrowRight, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* TODO: show category picker */ }
            )

            HorizontalDivider()

            // Payment Method row
            ListItem(
                headlineContent = { Text("Payment Method") },
                supportingContent = { Text(paymentMethod) },
                trailingContent = { Icon(Icons.Default.KeyboardArrowRight, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* TODO: show payment method picker */ }
            )

            HorizontalDivider()

            // Date row
            ListItem(
                headlineContent = { Text("Date") },
                supportingContent =  { Text(dateFormatter.format(date)) },
                trailingContent = { Icon(Icons.Default.KeyboardArrowRight, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        // TODO: show date picker
                    }
            )

            HorizontalDivider()

            Spacer(Modifier.height(16.dp))

            // Description field
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                placeholder = { Text("Enter transaction details") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.weight(1f))

            // Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(onClick = onCancel) {
                    Text("Cancel")
                }
                Button(onClick = {
                    onSave(
                        TransactionData(
                            isExpense = isExpense,
                            amount = amount.toDoubleOrNull() ?: 0.0,
                            category = category,
                            paymentMethod = paymentMethod,
                            date = date,
                            description = description
                        )
                    )
                }) {
                    Text("Save")
                }
            }
        }
    }



data class TransactionData(
    val isExpense: Boolean,
    val amount: Double,
    val category: String,
    val paymentMethod: String,
    val date: Date,
    val description: String
)

