package mvvm

import api.Resource
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import models.Event

class EventViewModel(private val eventRepository: EventRepository) {
    private var events: Resource<List<Event>> = Resource.Loading()

    fun filterEvents(filters: List<Filter>): List<Event> {
        return events.data?.applyFilters(filters) ?: emptyList()
    }

    fun loadEvents() = GlobalScope.launch {
        events = Resource.Loading()
        val response = eventRepository.getEvents(load())
        events = response
    }

    private fun load(): List<Filter> {
        val t = System.currentTimeMillis() / 1000
        return listOf(TimeGTFilter(t))
    }
}