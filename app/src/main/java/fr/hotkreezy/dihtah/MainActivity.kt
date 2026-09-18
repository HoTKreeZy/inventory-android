@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package fr.hotkreezy.dihtah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import fr.hotkreezy.dihtah.core.ui.theme.DihtahTheme
import fr.hotkreezy.dihtah.inventory.Inventory

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			DihtahTheme {
				var openBottomSheet by remember { mutableStateOf(false) }

				Scaffold(
					modifier = Modifier.fillMaxSize(),
					topBar = {
						TopAppBar(
							title = { Text("Inventory") }
						)
					},
					bottomBar = {
						BottomAppBar {
							NavigationBar {
								NavigationBarItem(
									onClick = { },
									selected = true,
									label = { Text("Home") },
									icon = {
										Icon(Icons.Default.Home, null)
									}
								)
							}
						}
					},
					floatingActionButton = {
						FloatingActionButton(
							onClick = { openBottomSheet = true }
						) {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "Create a new item."
							)
						}
					}
				) { innerPadding ->
					Inventory(
						openBottomSheet = openBottomSheet,
						onOpenBottomSheet = { openBottomSheet = it },
						modifier = Modifier.padding(innerPadding)
					)
				}
			}
		}
	}
}