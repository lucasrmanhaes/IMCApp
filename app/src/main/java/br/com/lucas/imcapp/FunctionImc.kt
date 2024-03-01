package br.com.lucas.imcapp
import android.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.core.graphics.toColor
import kotlin.math.pow

fun calcularIMC(peso: Double, altura: Double): Double{
    return peso/(altura/100).pow(2.0)
}

fun classificacaoImc(imc: Double): String{
    if(imc == 0.0){
        return ""
    }
    else if(imc < 18.5){
        return "Abaixo do peso"
    }
    else if(imc > 18.5 && imc < 25){
        return "Peso ideal"
    }
    else if(imc >= 25 && imc < 30){
        return "Levemente acima do peso"
    }
    else if(imc >= 30 && imc < 35){
        return "Obesidade grau I"
    }
    else if(imc >= 35 && imc < 40){
        return "Obesidade grau II"
    }
    else{
        return "Obesidade grau III"
    }
}

fun cardColor(imc: Double): Int {
    var cor = 0
    if (imc > 18.5 && imc < 25) {
        cor = 1
    }
    else if(imc >= 25 && imc < 30){
        cor = 2
    }
    else if(imc >= 30){
        cor = 3
    }
    return cor
}
