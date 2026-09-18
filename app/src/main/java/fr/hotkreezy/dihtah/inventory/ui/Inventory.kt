@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package fr.hotkreezy.dihtah.inventory.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.hotkreezy.dihtah.core.ui.theme.DihtahTheme

@Preview(showBackground = true)
@Composable
private fun InventoryPreview() {
	DihtahTheme(dynamicColor = false) {
		// Inventory()
	}
}

data class InventoryItem(val name: String, val quantity: Int, val category: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inventory(
	openBottomSheet: Boolean,
	onOpenBottomSheet: (Boolean) -> Unit,
	modifier: Modifier = Modifier,
) {
	val inventory = remember {
		mutableStateListOf(
			InventoryItem("milk", 2, "food"),
			InventoryItem("eggs", 20, "food"),
			InventoryItem("mayo", 4, "food"),
			InventoryItem("chicken", 16, "food"),
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
			ModalBottomSheet(
				onDismissRequest = { onOpenBottomSheet(false); selectedItem = null },
			) {
				val nameState = rememberTextFieldState(selectedItem?.name ?: "")
				val categoryState = rememberTextFieldState(selectedItem?.category ?: "food")
				val quantityState = rememberTextFieldState(
					selectedItem?.quantity?.toString() ?: "1"
				)

				Column(
					modifier = Modifier.safeContentPadding()
				) {
					TextField(
						state = nameState,
						modifier = Modifier.fillMaxWidth(),
						label = "Item",
						keyboardOptions = KeyboardOptions.Default.copy(
							imeAction = ImeAction.Next,
						),
					)
					TextField(
						state = categoryState,
						modifier = Modifier.fillMaxWidth(),
						label = "Category",
						keyboardOptions = KeyboardOptions.Default.copy(
							imeAction = ImeAction.Next,
						),
					)
					TextField(
						state = quantityState,
						modifier = Modifier.fillMaxWidth(),
						label = "Quantity",
						keyboardOptions = KeyboardOptions.Default.copy(
							keyboardType = KeyboardType.Number,
						),
					)
					Button(
						onClick = {
							if (selectedItem == null) {
								inventory.add(
									InventoryItem(
										name = nameState.text.toString(),
										category = categoryState.text.toString(),
										quantity = quantityState.text.toString().toIntOrNull() ?: 1
									)
								)
							} else {
								val index = inventory.indexOf(selectedItem)
								inventory[index] = InventoryItem(
									name = nameState.text.toString(),
									category = categoryState.text.toString(),
									quantity = quantityState.text.toString().toIntOrNull() ?: 1
								)
							}
							onOpenBottomSheet(false)
							selectedItem = null
						},
						modifier = Modifier.fillMaxWidth(),
					) { Text("Save") }
				}
			}
		}
	}
}

@Composable
fun TextField(
	state: TextFieldState,
	label: String,
	modifier: Modifier = Modifier,
	keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
) {
	OutlinedTextField(
		state = state,
		modifier = modifier,
		label = { Text(label) },
		keyboardOptions = keyboardOptions,
		lineLimits = TextFieldLineLimits.SingleLine,
	)
}