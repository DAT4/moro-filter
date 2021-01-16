import kotlinx.coroutines.awaitAll
import mvvm.*

fun main() {

    val filters = listOf<Filter>(
        PriceLTFilter(50),
        PlaceFilter("LOPPEN"),
        PlaceFilter("HUSET")
    )

    val viewModel = EventViewModel(EventRepository())
    viewModel.loadEvents()
    Thread.sleep(5000)
    val events = viewModel.filterEvents(filters)
    events.forEach {
        println(it.title)
        println(it.location.place)
        println(it.price)
    }

}

