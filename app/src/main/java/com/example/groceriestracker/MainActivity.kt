package com.example.groceriestracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Checklist
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.ui.TopLevelDestinations
import com.example.groceriestracker.ui.TopNavHost
import com.example.groceriestracker.ui.theme.GroceriesTrackerTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.groceriestracker.database.AppDatabase
import com.example.groceriestracker.database.Item
import com.example.groceriestracker.database.ItemStatus
import com.example.groceriestracker.database.UpcAssociation
import com.example.groceriestracker.repository.ItemRepository
import com.example.groceriestracker.repository.UpcAssociationRepository
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            GroceriesTrackerApp(widthSizeClass)
        }
    }
}

/**
 * Determine the drawer state to pass to the modal drawer.
 * from https://github.com/android/compose-samples/blob/main/JetNews/app/src/main/java/com/example/jetnews/ui/JetnewsApp.kt#L39
 */
@Composable
private fun rememberSizeAwareDrawerState(isExpandedScreen: Boolean): DrawerState {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    return if (!isExpandedScreen) {
        // If we want to allow showing the drawer, we use a real, remembered drawer
        // state defined above
        drawerState
    } else {
        // If we don't want to allow the drawer to be shown, we provide a drawer state
        // that is locked closed. This is intentionally not remembered, because we
        // don't want to keep track of any changes and always keep it closed
        DrawerState(DrawerValue.Closed)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroceriesTrackerApp(
    widthSizeClass: WindowWidthSizeClass,
) {
    GroceriesTrackerTheme {
        val navController = rememberNavController()
        //TopNavHost(navController = navController)
        val coroutineScope = rememberCoroutineScope()
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        //val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route ?: TopLevelDestinations.HOME_ROUTE

        val appDatabase = AppDatabase.getDatabase(LocalContext.current)

        val itemDao = appDatabase.itemDao()
        val itemRepository = ItemRepository(itemDao)
        val allItems by itemRepository.processedItems.observeAsState(emptyList())

        val upcAssociationDao = appDatabase.upcAssociationDao()
        val upcAssociationRepository = UpcAssociationRepository(upcAssociationDao)

        fun incrementItemQuantity(id: Int, amountToIncrement: Double){
            val item = allItems.find {
                item -> item.databaseEntryUID == id
            }
            item?.incrementQuantity(amountToIncrement)
            coroutineScope.launch {
                item?.saveToDatabase()
            }
        }

        fun getUpcAssociation(upc: String) : UpcAssociation? {
            return upcAssociationRepository.getUpcAssociation(upc)
        }

        fun addUpcAssociation(upcAssociation: UpcAssociation){
            coroutineScope.launch {
                upcAssociationRepository.insert(upcAssociation)
            }
        }

        fun createItem() {
            val newGrapesItem = Item(
                name = "Grapes", amount = 10.0, unit = "oz",
                iconId = "grape", statusEvents = listOf(ItemStatus(1723339395, 10.0))
            )
            val newBreadItem = Item(
                name = "Bread", amount = 1.0, unit = "loaf",
                iconId = "bread", statusEvents = listOf(ItemStatus(1723166595, 1.0))
            )
            val newToothpasteItem = Item(
                name = "Toothpase", amount = 15.0, unit = "tube",
                iconId = "toothpaste", statusEvents = listOf(ItemStatus(Date().time, 15.0))
            )
            coroutineScope.launch {
                itemRepository.insert(newGrapesItem)
                itemRepository.insert(newBreadItem)
                itemRepository.insert(newToothpasteItem)
            }
        }



        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text("Groceries Tracker")
                    },
                    actions = {
                        IconButton(onClick = {

                        }) {
                            Icon(Icons.Rounded.Receipt, contentDescription = "Open shopping list")
                        }
                    }
                )
            },
            bottomBar = {
                NavigationBar() {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                Icons.Rounded.Home,
                                contentDescription = "Home"

                            )
                        },
                        label = { Text("Home") },
                        selected = currentRoute == TopLevelDestinations.HOME_ROUTE,
                        onClick = {
                            navController.navigate(TopLevelDestinations.HOME_ROUTE)
                        }
                    )
                    NavigationBarItem(
                        icon = {
                            Icon(
                                Icons.Rounded.Checklist,
                                contentDescription = "Check"

                            )
                        },
                        label = { Text("Check") },
                        selected = currentRoute == TopLevelDestinations.CHECK_ROUTE,
                        onClick = {
                            navController.navigate(TopLevelDestinations.CHECK_ROUTE)
                        }
                    )
                }
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { createItem() }) {
                    Icon(Icons.Rounded.Add, contentDescription = "Add")
                }
            }
        ) { innerPadding ->
            TopNavHost(navController, innerPadding, allItems, ::getUpcAssociation, ::addUpcAssociation, ::incrementItemQuantity)
        }
    }
}


/*
class MainActivity : AppCompatActivity() {

private lateinit var binding: ActivityMainBinding

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    setSupportActionBar(binding.toolbar)

    val navView: BottomNavigationView = binding.navView

    val navController = findNavController(R.id.nav_host_fragment_activity_main)
    // Passing each menu ID as a set of Ids because each
    // menu should be considered as top level destinations.
    val appBarConfiguration = AppBarConfiguration(
        setOf(
            R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
        )
    )
    setupActionBarWithNavController(navController, appBarConfiguration)
    navView.setupWithNavController(navController)
}

override fun onSupportNavigateUp(): Boolean {
    val navController = findNavController(R.id.nav_host_fragment_activity_main)
    return navController.navigateUp() || super.onSupportNavigateUp()
}


}
 */