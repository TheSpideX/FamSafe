package com.spidex.safe

data class ListData(
    val dataList :  List<PersonData>
)

data class PersonData(
    val name: String,
    val location : String,
    val battery : Int,
    val distance : Int,
    val network : String,
    val mobile : String,
    val icon : Int,
    val cond : Int,
    val time : String
)