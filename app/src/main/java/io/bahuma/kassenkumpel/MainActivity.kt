package io.bahuma.kassenkumpel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Euro
import androidx.compose.material.icons.filled.FolderCopy
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.bahuma.kassenkumpel.core.controller.ProvideSnackbarController
import io.bahuma.kassenkumpel.feature_pointofsale.presentation.pointofsale.components.PointOfSalesScreen
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_category.components.AddEditCategoryScreen
import io.bahuma.kassenkumpel.feature_products.presentation.add_edit_product.components.AddEditProductScreen
import io.bahuma.kassenkumpel.feature_products.presentation.categories.components.CategoriesScreen
import io.bahuma.kassenkumpel.feature_products.presentation.products.components.ProductsScreen
import io.bahuma.kassenkumpel.feature_transactions.presentation.transactions.components.TransactionsScreen
import io.bahuma.kassenkumpel.ui.theme.KassenKumpelTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            val drawerState = rememberDrawerState(DrawerValue.Closed)

            val snackbarHostState = remember { SnackbarHostState() }

            val scope = rememberCoroutineScope()

            KassenKumpelTheme {
                ModalNavigationDrawer(
                    drawerState = drawerState, drawerContent = {
                        ModalDrawerSheet {
                            Text("KassenKumpel 1.0", modifier = Modifier.padding(16.dp))

                            HorizontalDivider()

                            topLevelRoutes.forEach { topLevelRoute ->
                                NavigationDrawerItem(label = { Text(topLevelRoute.name) },
                                    selected = currentDestination?.hierarchy?.any {
                                        it.hasRoute(
                                            topLevelRoute.route::class
                                        )
                                    } == true,
                                    icon = {
                                        Icon(
                                            imageVector = topLevelRoute.icon,
                                            contentDescription = topLevelRoute.name
                                        )
                                    },
                                    onClick = {
                                        navController.navigate(topLevelRoute.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }

                                        scope.launch {
                                            drawerState.close()
                                        }
                                    })
                            }
                        }
                    }, gesturesEnabled = false
                ) {
                    ProvideSnackbarController(snackbarHostState, scope) {
                        Scaffold(
                            topBar = {
                                TopAppBar(
                                    colors = topAppBarColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        titleContentColor = MaterialTheme.colorScheme.primary
                                    ),
                                    title = {
                                        Text(text = topLevelRoutes.firstOrNull { topLevelRoute ->
                                            currentDestination?.hierarchy?.any {
                                                it.hasRoute(
                                                    topLevelRoute.route::class
                                                )
                                            } == true
                                        }?.name ?: "KassenKumpel")
                                    },
                                    navigationIcon = {
                                        IconButton(onClick = {
                                            scope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }) {
                                            Icon(
                                                imageVector = Icons.Filled.Menu,
                                                contentDescription = "Menü"
                                            )
                                        }
                                    }
                                )
                            },
                            snackbarHost = {
                                SnackbarHost(hostState = snackbarHostState) {
                                    Snackbar(
                                        snackbarData = it,
                                        modifier = Modifier.widthIn(0.dp, 250.dp)
                                    )
                                }
                            }
                        ) { contentPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = PointOfSaleScreen,
                                modifier = Modifier.padding(contentPadding)
                            ) {
                                composable<PointOfSaleScreen> {
                                    PointOfSalesScreen(navController)
                                }

                                composable<ProductsScreen> {
                                    ProductsScreen(navController)
                                }

                                composable<AddEditProductsScreen> {
                                    AddEditProductScreen(navController)
                                }

                                composable<CategoriesScreen> {
                                    CategoriesScreen(navController)
                                }

                                composable<AddEditCategoryScreen> {
                                    AddEditCategoryScreen(navController)
                                }

                                composable<TransactionsScreen> {
                                    TransactionsScreen()
                                }
                            }
                        }
                    }

                }
            }
        }
    }
}

@Serializable
object PointOfSaleScreen

@Serializable
object ProductsScreen

@Serializable
data class AddEditProductsScreen(
    val productId: Int?
)

@Serializable
object CategoriesScreen

@Serializable
data class AddEditCategoryScreen(
    val categoryId: Int?
)

@Serializable
object TransactionsScreen

data class TopLevelRoute<T : Any>(val name: String, val route: T, val icon: ImageVector)

val topLevelRoutes = listOf(
    TopLevelRoute("Kasse", PointOfSaleScreen, Icons.Default.ShoppingCart),
    TopLevelRoute("Produkte", ProductsScreen, Icons.Default.Category),
    TopLevelRoute("Kategorien", CategoriesScreen, Icons.Default.FolderCopy),
    TopLevelRoute("Transaktionen", TransactionsScreen, Icons.Default.Euro)
)