package models

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.*

data class Event(
    val title: String,
    val genre: String,
    val image: String,
    val link: String,
    val category: List<String>,
    val price: Int,
    val text: String,
    val tickets: String,
    var time: Long,
    val location: Location
) {
    fun getDate(): String = SimpleDateFormat("dd/MM/yyyy").format(Date(time * 1000))
    fun getTimeToString(): String = SimpleDateFormat("HH:mm").format(Date(time * 1000))
    fun getLocalDate() = this.time.toLocalDate()
    private fun Long.toLocalDate(): LocalDate {
        return Instant.ofEpochMilli(this * 1000)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    }
}

