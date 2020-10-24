package com.example.bangladeshcovid19.model

data class BdApiResponse(
    val death: Death,
    val positive: Positive,
    val recovered: Recovered,
    val test: Test,
    val updated_on: String
)

data class Death(
    val last24: Int,
    val total: Int
)

data class Positive(
    val last24: Int,
    val total: Int
)

data class Recovered(
    val last24: Int,
    val total: Int
)

data class Test(
    val last24: Int,
    val total: Int
)