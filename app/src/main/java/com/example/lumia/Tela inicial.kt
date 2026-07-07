package com.example.lumia

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.BiasAlignment
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
fun TelaInicial(
    onNavigateToSons: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. Imagem de fundo
        Image(
            painter = painterResource(id = R.drawable.tela_login),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            // Usamos Crop para manter a proporção da imagem
            contentScale = ContentScale.Crop 
        )

        // 2. Botão posicionado proporcionalmente
        // Alignment(0f, 0.42f) -> 0f é centralizado horizontalmente
        // 0.42f é o ajuste vertical. 
        // No Bias: -1f é o topo, 0f é o centro, 1f é o rodapé.
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = BiasAlignment(0f, 0.32f) // Ajuste esse 0.42f se precisar subir ou descer
        ) {
            Button(
                onClick = onNavigateToSons,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFACD5D8) 
                ),
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .width(220.dp)
                    .height(58.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.btn_start),
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun TelaInicialPreview() {
    LumiaTheme {
        TelaInicial(onNavigateToSons = {})
    }
}