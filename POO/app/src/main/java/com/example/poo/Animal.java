package com.example.poo;

open class Animal (val nombre: String){
    open fun emitirSonido(){
        Log.i(tag= "Ejercicio Herencia", msg="El animal hace un sonido generico")
    }
}
