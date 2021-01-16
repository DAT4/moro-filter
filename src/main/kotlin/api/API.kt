package api

import api.gql.models.GQLQuery
import api.gql.models.GQLResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface API {
    @POST("/moro/api")
    suspend fun getEvents(@Body query: GQLQuery) : Response<GQLResponse>
}

