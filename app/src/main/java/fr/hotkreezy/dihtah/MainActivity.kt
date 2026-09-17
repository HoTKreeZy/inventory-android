package fr.hotkreezy.dihtah

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import fr.hotkreezy.dihtah.core.ui.theme.DihtahTheme
import fr.hotkreezy.dihtah.inventory.Inventory

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		setContent {
			DihtahTheme {
				Scaffold(
					modifier = Modifier.fillMaxSize(),
				) { innerPadding ->
					Inventory(modifier = Modifier.padding(innerPadding))
				}
			}
		}
	}
}