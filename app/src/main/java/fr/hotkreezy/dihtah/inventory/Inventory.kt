@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package fr.hotkreezy.dihtah.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.hotkreezy.dihtah.core.ui.theme.DihtahTheme

@Preview(showBackground = true)
@Composable
private fun InventoryPreview() {
	DihtahTheme(dynamicColor = false) {
		Inventory()
	}
}

data class InventoryItem(val name: String, val quantity: Int, val category: String)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Inventory(
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

	val textFieldState = rememberTextFieldState()
	var openBottomSheet by remember { mutableStateOf(false) }
	var selectedItem by remember { mutableStateOf<InventoryItem?>(null) }
	val bottomSheetState = rememberBottomSheetState(SheetValue.Hidden)

	Column(
		modifier = modifier.fillMaxSize(),
	) {
		OutlinedTextField(
			state = textFieldState,
			modifier = Modifier.fillMaxWidth(),
			label = { Text("Item") },
			keyboardOptions = KeyboardOptions.Default.copy(
				imeAction = ImeAction.Done,
			),
			onKeyboardAction = {
				inventory.add(
					InventoryItem(textFieldState.text.toString(), 1, "food")
				)
				textFieldState.clearText()
			},
			lineLimits = TextFieldLineLimits.SingleLine,
		)
		inventory.forEach { item ->
			ListItem(
				onClick = {
					openBottomSheet = true
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
				onDismissRequest = { openBottomSheet = false },
				sheetState = bottomSheetState,
			) {
				val focusManager = LocalFocusManager.current
				val nameState = rememberTextFieldState(selectedItem!!.name)
				val categoryState = rememberTextFieldState(selectedItem!!.category)
				val quantityState = rememberTextFieldState(selectedItem!!.quantity.toString())
				
				Column(
					modifier = Modifier.safeContentPadding()
				) {
					OutlinedTextField(
						state = nameState,
						modifier = Modifier.fillMaxWidth(),
						label = { Text("Item") },
						keyboardOptions = KeyboardOptions.Default.copy(
							imeAction = ImeAction.Done,
						),
						onKeyboardAction = { focusManager.clearFocus() },
						lineLimits = TextFieldLineLimits.SingleLine,
					)
					OutlinedTextField(
						state = categoryState,
						modifier = Modifier.fillMaxWidth(),
						label = { Text("Category") },
						keyboardOptions = KeyboardOptions.Default.copy(
							imeAction = ImeAction.Done,
						),
						onKeyboardAction = { focusManager.clearFocus() },
						lineLimits = TextFieldLineLimits.SingleLine,
					)
					OutlinedTextField(
						state = quantityState,
						modifier = Modifier.fillMaxWidth(),
						label = { Text("Quantity") },
						keyboardOptions = KeyboardOptions.Default.copy(
							imeAction = ImeAction.Done,
							keyboardType = KeyboardType.Number,
						),
						onKeyboardAction = { focusManager.clearFocus() },
						lineLimits = TextFieldLineLimits.SingleLine,
					)
					Button(
						onClick = {
							val index = inventory.indexOf(selectedItem)
							if (index != -1) {
								inventory[index] = InventoryItem(
									name = nameState.text.toString(),
									category = categoryState.text.toString(),
									quantity = quantityState.text.toString().toIntOrNull() ?: 0
								)
							}
							openBottomSheet = false
						},
						modifier = Modifier.fillMaxWidth(),
					) { Text("Save") }
				}
			}
		}
	}
}