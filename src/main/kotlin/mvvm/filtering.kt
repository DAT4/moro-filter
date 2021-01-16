package mvvm

import models.Event
import java.awt.geom.Area
import kotlin.reflect.KClass

sealed class Filter {
    abstract val graphQLName: String
    abstract fun isMatching(event: Event): Boolean
}

sealed class ExclusiveFilter : Filter()
sealed class InclusiveFilter : Filter() {
    abstract val value: String
}


data class TimeGTFilter(val start: Long) : ExclusiveFilter() {
    override val graphQLName = "timestampGT"
    override fun isMatching(event: Event) = this.start <= event.time
    override fun toString() = "$graphQLName: $start"
}

data class TimeLTFilter(val end: Long) : ExclusiveFilter() {
    override val graphQLName = "timestampLT"
    override fun isMatching(event: Event) = this.end >= event.time
    override fun toString() = "$graphQLName: $end"
}

data class PriceGTFilter(val start: Long) : ExclusiveFilter() {
    override val graphQLName = "priceGT"
    override fun isMatching(event: Event) = this.start <= event.price
    override fun toString() = "$graphQLName: $start"
}

data class PriceLTFilter(val end: Long) : ExclusiveFilter() {
    override val graphQLName = "priceGT"
    override fun isMatching(event: Event) = this.end >= event.price
    override fun toString() = "$graphQLName: $end"
}

data class TitleFilter(val string: String) : ExclusiveFilter() {
    override val graphQLName = "title"
    override fun isMatching(event: Event) = event.title.contains(this.string, true)
    override fun toString() = "$graphQLName: \"$string\" "
}

data class PlaceFilter(val place: String) : InclusiveFilter() {
    override val graphQLName = "place"
    override fun isMatching(event: Event) = event.location.place.contains(place, true)
    override fun toString() = "$graphQLName: \"$place\" "
    override val value = "\"$place\" "
}

data class AreaFilter(val area: String) : InclusiveFilter() {
    override val graphQLName = "area"
    override fun isMatching(event: Event) = event.location.area.contains(area, true)
    override fun toString() = "$graphQLName: \"$area\" "
    override val value = "\"$area\" "
}

data class CategoryFilter(val category: String) : InclusiveFilter() {
    override val graphQLName = "category"
    override fun isMatching(event: Event) = event.category.contains(category)
    override fun toString() = "$graphQLName: \"$category\" "
    override val value = "\"$category\" "
}

data class GenreFilter(val genre: String) : InclusiveFilter() {
    override val graphQLName = "genre"
    override fun isMatching(event: Event) = event.genre.contains(genre, true)
    override fun toString() = "$graphQLName: \"$genre\" "
    override val value = "\"$genre\" "
}

fun List<Event>.applyFilters(filters: List<Filter>) = filter { event -> filters.all { it.isMatching(event) } }
fun List<Filter>.stringify(): String {
    return this
        .groupBy { it.javaClass.kotlin }.also { println(it) }
        .map { it.stringify() }
        .joinToString(", ")
        .dropLast(1)
}


fun Map.Entry<KClass<Filter>, List<Filter>>.stringify(): String {
    return when (this.value.first()) {
        is InclusiveFilter -> "${this.value.first().graphQLName}: ${this.value.map { (it as InclusiveFilter).value }}"
        is ExclusiveFilter -> this.value[0].toString()
    }
}
