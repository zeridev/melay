package eu.dezeekees.melay.app.presentation.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import eu.dezeekees.melay.app.presentation.viewmodel.MainScreenViewmodel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainScreen(
    viewmodel: MainScreenViewmodel = koinViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
        ) {
            Text("Main Screen")
        }
    }
}