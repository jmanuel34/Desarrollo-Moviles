package com.example.test

import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btnClick = findViewById<Button>(R.id.btn_inc)
        val tv_saludo = findViewById<TextView>(R.id.tv_saludo)
        val btn_reiniciar = findViewById<Button>(R.id.btn_reiniciar)
        val tv_result = findViewById<TextView>(R.id.txt_resul)


        var numberPicker = findViewById<NumberPicker>(R.id.numberPicker)
        var pasos=1

        numberPicker.minValue = 0
        numberPicker.maxValue = 40
        numberPicker.value=numberPicker.maxValue
        numberPicker.wrapSelectorWheel = true
        btnPrincipal.setOnClickListener {
            numberPicker.value--
            tv_saludo.text = numberPicker.value.toString()
            tv_resul.text=

        }
        btn_reiniciar.setOnClickListener {
            numberPicker.value = numberPicker.maxValue
            tv_saludo.text = numberPicker.value.toString()
            var resultado  = numberPicker.value.toInt()/pasos
            txt_result.text="texto"
            resultado.toString()
        }



    }
}