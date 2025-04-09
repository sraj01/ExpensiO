package com.example.myapplication.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import okhttp3.internal.wait


@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFF06402B))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.splash_logo),
            contentDescription = "Profile Image",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Shivam Raj",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Composable
fun DrawerItem(title: String, iconRes: Int,onClick: () -> Unit ,scope: CoroutineScope,
               drawerState: DrawerState) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background( Color.Transparent) // Highlight selected item
            .clickable{ scope.launch { drawerState.close() }
                        onClick() }

            .padding(16.dp)
           ,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = title,
            tint =  Color.Gray,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(title, fontSize = 16.sp,
           color =   Color.Black,

        )
    }
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HomeScreen(navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(250.dp).background(Color(0xFF50BB77))
            ) {
                DrawerHeader()
                DrawerItem("Home", R.drawable.baseline_home_24, { navController.navigate("home") }, scope, drawerState)

                DrawerItem("Settings", R.drawable.baseline_settings_24, { navController.navigate("settings") }, scope, drawerState)

                DrawerItem("Feedback", R.drawable.baseline_add_comment_24, { navController.navigate("about") }, scope, drawerState)

            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = "Hello User", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.weight(2f))
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(
                                painterResource(id = R.drawable.icons8_menu_50),
                                contentDescription = "Menu"
                            )
                        }
                    }
                )
            },
            floatingActionButton = {

                FloatingActionButton(
                    onClick = { navController.navigate("newTransaction") },
                    containerColor = Color(0xFF50BB77) // Custom  Color



                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Expense",
                    )
                }
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF5F5F5))
                    .padding(paddingValues)
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                BalanceCard()
                Spacer(modifier = Modifier.height(16.dp))
                RecentExpensesSection()
            }
        }
    }
}

@Composable
fun BalanceCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4CAF50)) // Green background
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Your Balance",
                fontSize = 16.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "$ 15,500.00",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally)            ) {
                InfoCard("Income", "$ 0.00", "+15%", Color.White, Color(0xFF66BB6A) ) // Green
                InfoCard("Expenses", "$ 0.00", "+22%", Color.White, Color(0xFF81C784)) // Light Green
                InfoCard("Savings", "$ 0.00", "0%", Color.White, Color(0xFFA5D6A7)) // Lighter Green
            }
        }
    }
}

@Composable
fun InfoCard(title: String, amount: String, percentage: String, textColor: Color, bgColor: Color) {
    Card(
        modifier = Modifier
            .width(80.dp)
            .height(100.dp)
            ,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = title, fontSize = 14.sp, color = textColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = amount, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = textColor)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = percentage, fontSize = 12.sp, color = textColor)
        }
    }
}


@Composable
fun SummaryColumn(label: String, amount: String, textColor: Color = Color.Black) {
    Column {
        Text(label, fontSize = 14.sp, color = Color.Gray)
        Text(amount, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = textColor)
    }
}

@Composable
fun CategoryWiseSpending() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Category-wise Spending", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
        ) {
            Text(
                text = "Pie Chart Here",
                modifier = Modifier.align(Alignment.Center),
                color = Color.Gray
            )
        }
    }
}

@Composable
fun RecentTransactions() {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("Recent Transactions", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(5) {
                TransactionRow("Food", "₹500", Icons.Default.Face, Color.Blue)
            }
        }
    }
}


@Composable
fun TransactionRow(category: String, amount: String, icon: Any, tint: Color) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = icon as androidx.compose.ui.graphics.vector.ImageVector, contentDescription = category, tint = tint, modifier = Modifier.size(24.dp))
            Spacer(modifier = Modifier.width(8.dp))
            Text(category, fontSize = 14.sp)
        }
        Text(amount, fontSize = 14.sp, fontWeight = FontWeight.Bold)
    }
}
@Composable
fun RecentExpensesSection() {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(310.dp)
             .padding(16.dp) ,
        colors = CardDefaults.cardColors(containerColor = Color(0xFFcbf5dd) ),
        shape = RoundedCornerShape(12.dp)
    ) {
        // Header Section
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Text(
                text = "Recent Expenses",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)

            )

            // Dropdown for selecting filter
            FilterDropdown()
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Date Section
        Text(
            text = "November 19, 2024",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(start = 8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        var selectedCategory by remember { mutableStateOf("All") }
        val filteredExpenses = dummyExpenses.filter { it.category.toString() == selectedCategory || selectedCategory == "All" }

        CategoryTabs(selectedCategory) { newCategory ->
            selectedCategory = newCategory
        }
        Spacer(modifier = Modifier.height(8.dp))


        // Expenses List
        LazyColumn {
            items(filteredExpenses) { expense ->
                ExpenseItem(expense)
            }
        }
    }
}

// Dummy Data for Recent Transactions
//data class Expense(val category: String, val subText: String, val percentage: String, val amount: String, val icon: Int, val color: Color)

val dummyExpenses = listOf(
    Expense(1, 500.0, ExpenseCategory.FOOD, "Lunch at KFC", "2025-03-24","20%"),
    Expense(2, 150.0, ExpenseCategory.TRANSPORT, "Cab fare", "2025-03-24","60%"),
    Expense(3, 150.0, ExpenseCategory.FOOD, "Cab fare", "2025-03-24","6%"),
    Expense(4, 150.0, ExpenseCategory.SHOPPING, "Cab fare", "2025-03-24","40%"),
    Expense(5, 800.0, ExpenseCategory.SHOPPING, "New Shoes", "2025-03-24","50%")


)

// Single Expense Item
@Composable
fun ExpenseItem(expense: Expense) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Text(
            text = expense.category.getIcon(),
            fontSize = 24.sp,  // Adjust size
            modifier = Modifier.padding(end = 8.dp)



        )


        Spacer(modifier = Modifier.width(12.dp))

        // Category and SubText
        Column(modifier = Modifier.weight(1f)) {
            Text(text = expense.category.toString(), fontWeight = FontWeight.Bold, fontSize = 16.sp)
            if (expense.description.isNotEmpty()) {
                Text(text = expense.description, fontSize = 12.sp, color = Color.Gray)
            }
        }

        // Percentage
        Text(text = expense.percentage, fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.width(16.dp))

        // Amount
        Text(
            text = expense.amount.toString(),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Red
        )
    }
}

// Dropdown for Filtering
@Composable
fun FilterDropdown() {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Daily") }
    val options = listOf("Daily", "Weekly", "Monthly")

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
            .clickable { expanded = true }
            .background(Color(0xFFE0F2F1), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    ) {
        Text(text = selectedOption, fontSize = 14.sp, color = Color(0xFF00796B))

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(text = { Text(option) }, onClick = {
                    selectedOption = option
                    expanded = false
                })
            }
        }
    }
}
@Composable
fun CategoryTabs(selectedCategory: String, onCategorySelected: (String) -> Unit) {
    val categories = listOf("All", "FOOD", "TRANSPORT", "Health", "Shopping")
    var selectedTabIndex by remember { mutableStateOf(0) }


    ScrollableTabRow(
        selectedTabIndex = selectedTabIndex,
        edgePadding = 0.dp,
        containerColor = Color(0xFFcbf5dd
        ),
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                color = Color(0xFF00796B)  // Custom indicator color
            )
        }
    ) {
        categories.forEachIndexed { index, category ->
            Tab(
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    onCategorySelected(category)
                },
                text = {
                    Text(
                        text = category,
                        fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                        color = if (selectedTabIndex == index) Color(0xFF00796B) else Color.Gray
                    )
                }
            )
        }
    }
}
data class Expense(
    val id: Int,
    val amount: Double,
    val category: ExpenseCategory,  // Using enum here
    val description: String,
    val date: String,
    val percentage: String
)
enum class ExpenseCategory(val displayName: String) {
    ALL("ALL"),
    FOOD("Food & Dining"),
    TRANSPORT("Transport"),
    ENTERTAINMENT("Entertainment"),
    SHOPPING("Shopping"),
    UTILITIES("Bills & Utilities"),
    OTHER("Miscellaneous");

    fun getIcon(): String {
        return when (this) {
            FOOD -> "🍔"
            TRANSPORT -> "🚗"
            ENTERTAINMENT -> "🎬"
            SHOPPING -> "🛍️"
            UTILITIES -> "💡"
            OTHER -> "🔹"
            ALL -> TODO("0")
        }
    }
}



