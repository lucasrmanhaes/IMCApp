package br.com.lucas.imcapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.lucas.imcapp.ui.theme.IMCAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IMCAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    IMCScreen()
                }
            }
        }
    }
}

@Composable
fun IMCScreen() {

    var imageBmi = R.drawable.bmi
    var colorUI = colorResource(id = R.color.colorUI)
    var colorBanner = colorResource(id = R.color.vermelho_fiap)
    var colorText = colorResource(id = R.color.white)

    var colorCardGreen = colorResource(id = R.color.colorGreen)
    var colorCardOrange = colorResource(id = R.color.colorOrange)
    var colorCardRed = colorResource(id = R.color.colorRed)

    var peso = remember{ mutableStateOf("") }
    var altura = remember{ mutableStateOf("") }
    var imc = remember{ mutableStateOf(0.0) }
    var corImc = 0

    fun colorRow(cor: Int): Color{
        var color = Color.Unspecified
        if(cor == 1){
           color = colorCardGreen
        }
        else if(cor == 2){
            return colorCardOrange
        }
        else if(cor == 3){
            return colorCardRed
        }
        return color
    }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(color = colorUI)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // ---- header ---------
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(color = colorBanner)) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(painter = painterResource(id = imageBmi), contentDescription = "", modifier = Modifier.size(70.dp))
                Spacer(modifier = Modifier.height(12.dp))
                Text(text="CALCULADORA IMC", fontSize = 20.sp, color = colorText)
            }
            // --- formulário
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)) {
                Card(modifier = Modifier
                    .height(380.dp)
                    .fillMaxWidth()
                    .offset(y = -40.dp), colors = CardDefaults.cardColors(containerColor = colorUI),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                ) {
                    Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 32.dp)
                    ) {
                        Text(text = "Seus dados", modifier = Modifier.fillMaxWidth(), fontSize = 24.sp, fontWeight = FontWeight.Bold,
                            color = colorResource(id = R.color.vermelho_fiap),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Seu peso",
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = R.color.vermelho_fiap)
                        )
                        OutlinedTextField(
                            value = peso.value,
                            onValueChange = {it -> peso.value = it},
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Seu peso em Kg.")
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.vermelho_fiap),
                                focusedBorderColor = colorResource(id = R.color.vermelho_fiap)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Sua altura",
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = R.color.vermelho_fiap)
                        )
                        OutlinedTextField(
                            value = altura.value,
                            onValueChange = {novoValor -> altura.value = novoValor},
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Sua altura em cm."
                                )
                            },
                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.vermelho_fiap),
                                focusedBorderColor = colorResource(id = R.color.vermelho_fiap)
                            ),
                            shape = RoundedCornerShape(16.dp),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )
                        Spacer(modifier = Modifier.height(34.dp))
                        Button(
                            onClick = {
                                imc.value = calcularIMC(peso.value.toDouble(), altura.value.toDouble());
                                corImc = cardColor(imc.value)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.vermelho_fiap))
                        ) {
                            Text(text = "CALCULAR", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 14.sp
                            )
                        }
                    }
                }
            }

            // -- Card Resultado
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(160.dp)
                .clip(shape = RoundedCornerShape(8.dp))
                .background(colorRow(cardColor(imc.value)))
                ) {
                Column(modifier = Modifier
                    .height(190.dp)
                    .width(180.dp)
                    .padding(12.dp),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Resultado", fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.offset(x = -16.dp))
                }
                Column(modifier = Modifier
                    .height(190.dp)
                    .width(180.dp)
                    .padding(12.dp),
                    verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End
                    ) {
                    Text(text = String.format("%.1f",imc.value), fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White, modifier = Modifier.padding(end = 16.dp))
                    Text(text = classificacaoImc(imc.value), color = Color.White)
                }
            }
        }
    }
}

