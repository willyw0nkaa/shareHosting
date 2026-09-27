package com.example.pemesanantiket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.pemesanantiket.ui.theme.PemesananTiketTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PemesananTiketTheme {
                Surface {
                    TicketOrderScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketOrderScreenPreview() {
    PemesananTiketTheme {
        Surface {
            TicketOrderScreen()
        }
    }
}