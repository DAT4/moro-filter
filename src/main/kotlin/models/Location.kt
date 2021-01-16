package models

data class Location(
    val area: String,
    val place: String,
    val address: Address,
    val coordinates: Coordinates,
)