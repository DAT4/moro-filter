package mvvm

@Suppress("UNCHECKED_CAST")
class EventViewModelProviderFactory (private val eventRepository: EventRepository) {
    fun <T> create(modelClass: Class<T>): T {
        return EventViewModel(eventRepository) as T
    }

}