package api.gql.models

import models.Event

data class GQLData(val events: List<Event>, val errors: MutableList<GQLError>)