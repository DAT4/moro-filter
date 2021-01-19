import kotlinx.coroutines.awaitAll
import mvvm.*
import okhttp3.internal.wait

suspend fun main() {

    val filters = listOf(
        PriceLTFilter(50),
        PlaceFilter("LOPPEN"),
        PlaceFilter("HUSET")
    )

    val viewModel = EventViewModel(EventRepository())

    val job = viewModel.loadEvents()
    job.join()
    val events = viewModel.filterEvents(filters)
    events.forEach {
        println(it.title)
        println(it.location.place)
        println(it.price)
    }

}

