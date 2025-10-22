package com.example.cibercheck.entity

data class Curso(
    val periodoId: String,
    val nombre: String,
    val enCurso: Boolean,
    val tiempo: String? = null
)
