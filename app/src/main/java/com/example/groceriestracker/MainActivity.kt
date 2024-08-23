package com.example.groceriestracker

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.navigation.compose.rememberNavController
import com.example.groceriestracker.ui.pages.TopNavHost
import com.example.groceriestracker.ui.theme.GroceriesTrackerTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.groceriestracker.database.AppDatabase
import com.example.groceriestracker.models.UpcAssociation
import com.example.groceriestracker.repository.ItemRepository
import com.example.groceriestracker.models.ProcessedItem
import com.example.groceriestracker.repository.SettingsRepository
import com.example.groceriestracker.repository.SettingsRepository.Settings.UseDynamic
import com.example.groceriestracker.repository.UpcAssociationRepository
import com.example.groceriestracker.ui.components.frontpane.FrontPane
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            val settingsRepo = SettingsRepository(dataStore)
            GroceriesTrackerApp(widthSizeClass, settingsRepo)
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
    settingsRepo: SettingsRepository
) {
    val coroutineScope = rememberCoroutineScope()
    val useDynamicTheme = remember { mutableStateOf(false) }
    LaunchedEffect(coroutineScope) {
        useDynamicTheme.value = with(settingsRepo) {
            UseDynamic.getValue()
        } ?: false
    }
    GroceriesTrackerTheme(useDynamic = useDynamicTheme.value) {
        val navController = rememberNavController()
        //TopNavHost(navController = navController)
        val isExpandedScreen = widthSizeClass == WindowWidthSizeClass.Expanded
        //val sizeAwareDrawerState = rememberSizeAwareDrawerState(isExpandedScreen)
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        /* App Bars and stuff */
        val frontPane = FrontPane(navBackStackEntry, navController)
        /*
        val bottomNavBar = BottomNavBar(navBackStackEntry, navController)
        val topAppBar = TopAppBar(navigateUp = navController::navigateUp)
        val floatingActionButton = DynamicFab() /*do nothing on click*/
         */

        val appDatabase = AppDatabase.getDatabase(LocalContext.current)

        val itemDao = appDatabase.itemDao()
        val itemRepository = ItemRepository(itemDao)
        val allItems by itemRepository.processedItems.observeAsState(emptyList())

        val upcAssociationDao = appDatabase.upcAssociationDao()
        val upcAssociationRepository = UpcAssociationRepository(upcAssociationDao)
        // TODO might cause excessive reloads but necessary for getUpcAssociation, for some reason
        // it's null if you use value so you have to use observeasstate but that can only be called from a composable so
        val allUpcsList by upcAssociationRepository.allUpcs.observeAsState(emptyList())

        fun incrementItemQuantity(id: Int, amountToIncrement: Double) {
            val item = allItems.find { item ->
                item.databaseEntryUID == id
            }
            item?.incrementQuantity(amountToIncrement)
            coroutineScope.launch {
                item?.saveToDatabase()
            }
        }

        fun getUpcAssociation(upc: String): UpcAssociation? {
            Log.d(
                "MainActivity", "searching for upc ${upc} in list ${
                    allUpcsList.joinToString { upcAssociation ->
                        upcAssociation.upc!!
                    }
                }"
            )
            return allUpcsList.find { upcAssociation ->
                upcAssociation.upc == upc
            }
            //return upcAssociationRepository.getUpcAssociation(upc)
        }

        fun addUpcAssociation(upcAssociation: UpcAssociation) {
            coroutineScope.launch {
                upcAssociationRepository.insert(upcAssociation)
            }
        }

        fun searchItems(query: String): List<ProcessedItem> {
            val realQuery = query.lowercase() // TODO
            return allItems.filter { item ->
                item.matchQuery(realQuery)
            }
        }

        /*
        fun createItem() {
            val newGrapesItem = Item(
                name = stringResource(R.string.label_item_grapes), unit = stringResource(R.string.label_unit_oz),
                iconId = "grape", statusEvents = listOf(ItemStatus(1723339395, 10.0))
            )
            val newBreadItem = Item(
                name = stringResource(R.string.label_item_bread), unit = "loaf",
                iconId = "bread", statusEvents = listOf(ItemStatus(1723166595, 1.0))
            )
            val newToothpasteItem = Item(
                name = stringResource(R.string.label_item_toothpaste), unit = "tube",
                iconId = "toothpaste", statusEvents = listOf(ItemStatus(Date().time, 15.0))
            )
            coroutineScope.launch {
                itemRepository.insert(newGrapesItem)
                itemRepository.insert(newBreadItem)
                itemRepository.insert(newToothpasteItem)
            }
        }*/



        Scaffold(
            topBar = {
            },
            bottomBar = {
                frontPane.BottomNavBar()
            },
            floatingActionButton = {
                frontPane.FloatingActionButton()
            }
        ) { innerPadding ->
            Box() {
                frontPane.TopAppBar()
                Column(modifier = Modifier.padding(innerPadding).zIndex(0f)) {
                    Spacer(modifier = Modifier.height(64.dp + 8.dp /*TODO fix magic number - this is height of top app bar + 8.dp*/))
                    TopNavHost(
                        navController = navController,
                        allItems = allItems,
                        frontPane = frontPane,
                        settingsRepo = settingsRepo,
                        getUpcAssociation = ::getUpcAssociation,
                        addUpcAssociation = ::addUpcAssociation,
                        incrementItemQuantity = ::incrementItemQuantity,
                        searchItems = ::searchItems
                    )
                }
            }
        }
    }
}