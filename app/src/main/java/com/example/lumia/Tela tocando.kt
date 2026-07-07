package com.example.lumia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun TelaPlayer(
    viewModel: SoundViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Coleta o som selecionado do ViewModel
    val sound by viewModel.selectedSound.collectAsState()
    val isPlaying by viewModel.isPlaying.collectAsState()
    val volume by viewModel.volume.collectAsState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.background_sons),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        sound?.let { item ->
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Espaçamento para o logo e tagline que estão na imagem de fundo
                Spacer(modifier = Modifier.height(460.dp))

                // Ponto indicador (como visto no design)
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .background(Color.White.copy(alpha = 0.5f), CircleShape)
                )

                Spacer(modifier = Modifier.height(40.dp))

                PlayerCard(
                    sound = item,
                    isPlaying = isPlaying,
                    volume = volume,
                    onTogglePlay = { viewModel.togglePlayPause() },
                    onVolumeChange = { viewModel.setVolume(it) },
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                // Barra de Navegação Inferior
                BottomNavigationBarTocando(onClick = onBack)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayerCard(
    sound: SoundItem,
    isPlaying: Boolean,
    volume: Float,
    onTogglePlay: () -> Unit,
    onVolumeChange: (Float) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(210.dp)
    ) {
        // Background do card estilizado (substituindo o bg_player_card ausente)
        Surface(
            color = Color.White.copy(alpha = 0.1f),
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier.fillMaxSize()
                .border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(32.dp))
        ) {}

        // Conteúdo por cima do background
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Ícone do Som (Esquerda)
                Box(
                    modifier = Modifier.size(70.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = sound.iconRes),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = sound.titleRes),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_music_note),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.7f),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = stringResource(id = sound.descriptionRes),
                            color = Color.White.copy(alpha = 0.7f),
                            fontSize = 16.sp
                        )
                    }
                }

                // Botão Play/Pause (Direita)
                Surface(
                    onClick = onTogglePlay,
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
                            painter = painterResource(id = if (isPlaying) R.drawable.frame_7 else R.drawable.play),
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            tint = Color.Unspecified,
                            modifier = Modifier
                                .size(50.dp)
                                .padding(start = if (isPlaying) 0.dp else 2.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Barra de Progresso / Volume
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.volume__2_),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
                
                Slider(
                    value = volume,
                    onValueChange = onVolumeChange,
                    modifier = Modifier.weight(1f),
                    colors = SliderDefaults.colors(
                        thumbColor = Color.White,
                        activeTrackColor = Color(0xFF19B8C9),
                        inactiveTrackColor = Color.White.copy(alpha = 0.2f)
                    )
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBarTocando(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Surface(
        onClick = onClick,
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

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun TelaPlayerPreview() {
    LumiaTheme {
        // Preview simplificado sem ViewModel real
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.background_sons),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(460.dp))
                PlayerCard(
                    sound = SoundItem("chuva", R.string.sound_chuva_suave, R.drawable.chuva_suave, 0),
                    isPlaying = true,
                    volume = 0.75f,
                    onTogglePlay = {},
                    onVolumeChange = {},
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                BottomNavigationBarTocando(onClick = {})
            }
        }
    }
}
