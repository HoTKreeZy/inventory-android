package fr.hotkreezy.dihtah.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.clearText
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Badge
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
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
		for (item in inventory) {
			ListItem(
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
	}
}

