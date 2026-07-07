package com.example.lumia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lumia.ui.theme.LumiaTheme

import androidx.compose.runtime.*

@Composable
fun TelaSons(
    viewModel: SoundViewModel,
    onNavigateToPlayer: () -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedSound by viewModel.selectedSound.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()

    val sounds = listOf(
        SoundItem("chuva_suave", R.string.sound_chuva_suave, R.drawable.chuva_suave, R.raw.chuva_suave),
        SoundItem("ondas_mar", R.string.sound_ondas_do_mar, R.drawable.onda, R.raw.ondas_mar),
        SoundItem("lareira", R.string.sound_lareira, R.drawable.lareira, R.raw.lareira),
        SoundItem("chuva_forte", R.string.sound_chuva_forte, R.drawable.chuva_forte, R.raw.chuva_forte),
        SoundItem("secador", R.string.sound_secador, R.drawable.secador_de_cabelo, R.raw.secador),
        SoundItem("floresta", R.string.sound_floresta, R.drawable.floresta, R.raw.floresta)
    )

    TelaSonsContent(
        sounds = sounds,
        selectedSound = selectedSound,
        isPlaying = isPlaying,
        onSoundClick = { sound ->
            viewModel.selectSound(sound)
            onNavigateToPlayer()
        },
        modifier = modifier
    )
}

@Composable
fun TelaSonsContent(
    sounds: List<SoundItem>,
    selectedSound: SoundItem?,
    isPlaying: Boolean,
    onSoundClick: (SoundItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. Imagem de fundo (background_sons agora é a imagem com o logo)
        Image(
            painter = painterResource(id = R.drawable.background_sons),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // 2. Espaçamento Superior
            // Ajuste este valor para a lista começar logo abaixo da linha ondulada da imagem
            Spacer(modifier = Modifier.height(350.dp))

            // 3. Título "Sons" alinhado com a imagem
            Text(
                text = stringResource(id = R.string.sons_header),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(horizontal = 28.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 4. Lista de Sons
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sounds) { sound ->
                    SoundListItem(
                        sound = sound,
                        isSelected = selectedSound?.id == sound.id,
                        isPlaying = isPlaying,
                        onClick = { onSoundClick(sound) }
                    )
                }
            }

            // 5. Barra de Navegação Inferior
            BottomNavigationBar()
        }
    }
}

@Composable
fun SoundListItem(
    sound: SoundItem,
    isSelected: Boolean,
    isPlaying: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Color.White.copy(alpha = 0.2f) else Color.White.copy(alpha = 0.1f),
        shape = RoundedCornerShape(40.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(70.dp)
            .border(
                if (isSelected) 1.dp else 0.5.dp,
                if (isSelected) Color.White.copy(alpha = 0.6f) else Color.White.copy(alpha = 0.3f),
                RoundedCornerShape(40.dp)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Ícone
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .border(1.dp, Color(0x33FFFFFF), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(id = sound.iconRes),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = stringResource(id = sound.titleRes),
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.weight(1f)
            )

            // Play/Pause Button
            Surface(
                onClick = onClick,
                color = Color.Transparent,
                shape = CircleShape,
                modifier = Modifier.size(48.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.circulo),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                    
                    Icon(
                        painter = painterResource(id = if (isSelected && isPlaying) R.drawable.frame_7 else R.drawable.play),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .size(50.dp)
                            .padding(start = if (isSelected && isPlaying) 0.dp else 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(modifier: Modifier = Modifier) {
    Surface(
        color = Color(0xFF19B8C9).copy(alpha = 0.2f),
        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.musica_alt__2_),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
            
            Text(
                text = stringResource(id = R.string.sons_nav_item),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

data class SoundItem(
    val id: String,
    val titleRes: Int,
    val iconRes: Int,
    val audioRes: Int,
    val descriptionRes: Int = R.string.status_tocando
)

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun TelaSonsPreview() {
    val sounds = listOf(
        SoundItem("chuva_suave", R.string.sound_chuva_suave, R.drawable.pour_rain, R.raw.chuva_suave),
        SoundItem("ondas_mar", R.string.sound_ondas_do_mar, R.drawable.onda, R.raw.ondas_mar),
        SoundItem("lareira", R.string.sound_lareira, R.drawable.lareira, R.raw.lareira),
        SoundItem("chuva_forte", R.string.sound_chuva_forte, R.drawable.chuva_leve, R.raw.chuva_forte)
    )
    LumiaTheme {
        TelaSonsContent(
            sounds = sounds,
            selectedSound = sounds[0],
            isPlaying = false,
            onSoundClick = {}
        )
    }
}
