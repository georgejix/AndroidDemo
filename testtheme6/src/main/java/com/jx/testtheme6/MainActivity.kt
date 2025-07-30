package com.jx.testtheme6

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.jx.testtheme6.ui.theme.AndroiddemoTheme

class MainActivity : ComponentActivity() {
    private val TAG = javaClass.simpleName
    var isDarkTheme by mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        updateTheme(resources.configuration.uiMode)
        setContent {
            AndroiddemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        updateTheme(newConfig.uiMode)
    }

    private fun updateTheme(uiMode: Int) {
        if (uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES) {
            // 处于深色模式
            Log.d(TAG, "处于深色模式")
            isDarkTheme = (true)
        } else {
            // 处于浅色模式
            Log.d(TAG, "处于浅色模式")
            isDarkTheme = (false)
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Hello $name!",
            modifier = modifier,
            textAlign = TextAlign.Center,
            color = if (isDarkTheme) Color.White else Color.Red
        )
    }
}