package mvvm

import api.gql.models.GQLQuery
import api.gql.models.GQLResponse
import api.Resource
import api.RetrofitInstance
import models.Event
import mvvm.EventsCache.cacheList
import api.gql.events
import retrofit2.Response

class EventRepository() {

    suspend fun getEvents(filters: List<Filter>): Resource<List<Event>> {
        return if (cacheList.isEmpty()) {
            handleGetEvents(RetrofitInstance.api.getEvents(makeQuery(filters)))
        } else {
            Resource.Success(cacheList)
        }
    }

    private fun makeQuery(filter: List<Filter>) = GQLQuery(
        "${
            events(filter) {
                title
                genre
                image
                category
                link
                tickets
                other
                price
                text
                time
                location {
                    area
                    place
                    address {
                        city
                        street
                        no
                        state
                        zip
                    }
                    coordinates {
                        longitude
                        latitude
                    }
                }
            }
        }"
    )

    private fun handleGetEvents(response: Response<GQLResponse>): Resource<List<Event>> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                println("HER ER LISTEN ${resultResponse.data.events}")
                return Resource.Success(resultResponse.data.events)
            }
        }
        return Resource.Error(response.message())
    }
}