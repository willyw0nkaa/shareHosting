package com.example.pemesanantiket

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

// Warna warna
private val BiruHeader = Color(0xFF1D5FD6)
private val AbuLatar = Color(0xFFF1F3F7)
private val AbuTombol = Color(0xFFE7EAF0)
private val MerahLatar = Color(0xFFFBE7E8)
private val MerahTeks = Color(0xFFC53030)
private val BiruLatar = Color(0xFFE6EEFD)
private val HijauLatar = Color(0xFFE3F6E8)
private val HijauTeks = Color(0xFF2F9E44)


@Composable
fun TicketOrderScreen() {
    // 1. Harga Tiket
    var hargaTiket by rememberSaveable { mutableStateOf(50000) }
    // 2. Jumlah Tiket
    var jumlahTiket by rememberSaveable { mutableStateOf(1) }
    // 3. Nama Pembeli Tiket
    var namaPembeli by rememberSaveable { mutableStateOf("") }

    // Status pemesanan
    var statusKode by rememberSaveable { mutableStateOf("idle") }

    LaunchedEffect(statusKode) {
        if (statusKode == "proses") {
            delay(5000)
            statusKode = "sukses"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AbuLatar)
    ) {
        // Header biru, mirip judul di mockup
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(BiruHeader)
                .padding(horizontal = 20.dp, vertical = 20.dp)
        ) {
            Text(
                text = "Pemesanan Tiket",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Surface(
            color = Color.White,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(20.dp)) {

                NamaInput(nama = namaPembeli, onNamaChange = { namaPembeli = it })
                Spacer(modifier = Modifier.height(20.dp))

                JumlahTiketCounter(
                    jumlah = jumlahTiket,
                    onTambah = { jumlahTiket++ },
                    onKurang = { if (jumlahTiket > 1) jumlahTiket-- }
                )
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        statusKode = if (namaPembeli.isBlank()) "kosong" else "proses"
                    },
                    enabled = statusKode != "proses",
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BiruHeader,
                        disabledContainerColor = AbuTombol
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text("Pesan Tiket", fontWeight = FontWeight.SemiBold)
                }
                Spacer(modifier = Modifier.height(16.dp))

                StatusBox(statusKode = statusKode)
            }
        }
    }
}

@Composable
fun NamaInput(nama: String, onNamaChange: (String) -> Unit) {
    Column {
        Text(text = "Nama", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        OutlinedTextField(
            value = nama,
            onValueChange = onNamaChange,
            placeholder = { Text("Masukkan nama Anda", color = Color.Gray) },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFFD9DEE6),
                focusedBorderColor = BiruHeader
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun JumlahTiketCounter(
    jumlah: Int,
    onTambah: () -> Unit,
    onKurang: () -> Unit
) {
    Column {
        Text(text = "Jumlah Tiket", fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            BulatKontrol(simbol = "−", onClick = onKurang)
            Text(
                text = "$jumlah",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            BulatKontrol(simbol = "+", onClick = onTambah)
        }
    }
}

@Composable
fun BulatKontrol(simbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(42.dp)
            .background(AbuTombol, shape = RoundedCornerShape(10.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(text = simbol, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun StatusBox(statusKode: String) {
    val (latar, tulisan, pesan) = when (statusKode) {
        "kosong" -> Triple(MerahLatar, MerahTeks, "Nama masih kosong")
        "proses" -> Triple(BiruLatar, BiruHeader, "Memproses pesanan.........")
        "sukses" -> Triple(HijauLatar, HijauTeks, "Tiket telah dipesan")
        else -> Triple(AbuLatar, Color.DarkGray, "Silakan pesan tiket")
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(latar, shape = RoundedCornerShape(8.dp))
            .padding(14.dp)
    ) {
        when (statusKode) {
            "proses" -> {
                CircularProgressIndicator(
                    color = BiruHeader,
                    strokeWidth = 2.dp,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
            "sukses" -> {
                BulatTanda(warna = HijauTeks, simbol = "✓")
                Spacer(modifier = Modifier.width(8.dp))
            }
            "kosong" -> {
                BulatTanda(warna = MerahTeks, simbol = "!")
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Text(text = "Status: ", fontWeight = FontWeight.SemiBold, color = tulisan)
        Text(text = pesan, color = tulisan)
    }
}

@Composable
fun BulatTanda(warna: Color, simbol: String) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(warna, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(text = simbol, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}