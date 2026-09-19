package fr.hotkreezy.dihtah.inventory.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import fr.hotkreezy.dihtah.inventory.ui.InventoryItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryEditSheet(
	item: InventoryItem?,
	onDismissRequest: () -> Unit,
	onSaveRequested: (name: String, category: String, quantity: Int) -> Unit,
	modifier: Modifier = Modifier,
) {
	ModalBottomSheet(
		onDismissRequest = onDismissRequest,
		modifier = modifier,
	) {
		val nameState = rememberTextFieldState(item?.name ?: "")
		val categoryState = rememberTextFieldState(item?.category ?: "food")
		val quantityState = rememberTextFieldState(item?.quantity?.toString() ?: "1")

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
					onSaveRequested(
						nameState.text.toString(),
						categoryState.text.toString(),
						quantityState.text.toString().toIntOrNull() ?: 1
					)
				},
				modifier = Modifier.fillMaxWidth(),
			) { Text("Save") }
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