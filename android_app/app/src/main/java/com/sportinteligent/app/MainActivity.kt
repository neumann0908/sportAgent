package com.sportinteligent.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.sportinteligent.app.ui.navigation.MainScreen
import com.sportinteligent.app.ui.screens.ControlViewModel
import com.sportinteligent.app.ui.theme.SportInteligentTheme

class MainActivity : ComponentActivity() {

    private val viewModel: ControlViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SportInteligentTheme {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}
