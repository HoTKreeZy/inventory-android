@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package fr.hotkreezy.dihtah.inventory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.hotkreezy.dihtah.core.ui.theme.DihtahTheme
import fr.hotkreezy.dihtah.inventory.ui.components.InventoryEditSheet

@Preview(showBackground = true)
@Composable
private fun InventoryPreview() {
	DihtahTheme(dynamicColor = false) {
		// Inventory()
	}
}

data class InventoryItem(
	val id: Int = nextId++,
	val name: String,
	val quantity: Int,
	val category: String,
) {
	companion object {
		private var nextId = 0
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inventory(
	openBottomSheet: Boolean,
	onOpenBottomSheet: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	val inventory = remember {
		mutableStateListOf(
			InventoryItem(name = "milk", quantity = 2, category = "food"),
			InventoryItem(name = "eggs", quantity = 20, category = "food"),
			InventoryItem(name = "mayo", quantity = 4, category = "food"),
			InventoryItem(name = "chicken", quantity = 16, category = "food"),
		)
	}

	var selectedItem by remember { mutableStateOf<InventoryItem?>(null) }

	Column(
		modifier = modifier.fillMaxSize(),
	) {
		inventory.forEach { item ->
			ListItem(
				onClick = {
					onOpenBottomSheet(true)
					selectedItem = item
				},
				modifier = Modifier,
				verticalAlignment = Alignment.CenterVertically,
				trailingContent = {
					Column(
						horizontalAlignment = Alignment.End,
						verticalArrangement = Arrangement.spacedBy(8.dp)
					) {
						Badge(
							containerColor = Color(0xFFFF9800),
							contentColor = Color.White,
						) {
							Text(item.category)
						}
						Text("in stock : ${item.quantity}")
					}
				},
			) {
				Text(item.name)
			}
		}
		if (openBottomSheet) {
			InventoryEditSheet(
				item = selectedItem,
				onDismissRequest = {
					onOpenBottomSheet(false)
					selectedItem = null
				},
				onSaveRequested = { name, category, quantity ->
					if (selectedItem == null) {
						inventory.add(
							InventoryItem(
								name = name,
								quantity = quantity,
								category = category,
							)
						)
					} else {
						val index = inventory.indexOf(selectedItem)
						if (index != -1) {
							inventory[index] = selectedItem!!.copy(
								name = name,
								quantity = quantity,
								category = category
							)
						}
					}
					onOpenBottomSheet(false)
					selectedItem = null
				}
			)
		}
	}
}