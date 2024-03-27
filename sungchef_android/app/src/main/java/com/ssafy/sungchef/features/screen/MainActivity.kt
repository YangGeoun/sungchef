package com.ssafy.sungchef.features.screen

import android.content.Context
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.datastore.preferences.preferencesDataStore
import com.ssafy.sungchef.features.navigation.NavGraph
import com.ssafy.sungchef.features.ui.theme.SungchefTheme
import dagger.hilt.android.AndroidEntryPoint


private const val TAG = "MainActivity_성식당"
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel : MainViewModel by viewModels()
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SungchefTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavGraph(){
                        requestedOrientation = if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
                            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
                        }else {
                            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                        }
                    }
                }
            }
        }
    }
}
