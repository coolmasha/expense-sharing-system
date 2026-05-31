package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.EventRequest
import retrofit2.Response
import javax.inject.Inject
import kotlin.uuid.Uuid

class EventRemoteDataSource @Inject constructor(
    private val eventService: EventService
) {
    suspend fun getAllEvents(user_id: String) =
        eventService.getAllEvents(user_id = user_id)
    suspend fun getEvent(event_id: String) = eventService.getEvent(event_id = event_id)
    suspend fun postEvent(body: EventRequestDto) = eventService.postEvent(body = body)
    suspend fun putEvent(event_id: String, body: EventRequestDto)= eventService.putEvent(event_id = event_id, body = body)
    suspend fun patchEvent(event_id: String, body: EventRequestDto) = eventService.patchEvent(event_id = event_id, body = body)
    suspend fun deleteEvent(event_id: String): Response<Void> = eventService.deleteEvent(event_id = event_id)
}