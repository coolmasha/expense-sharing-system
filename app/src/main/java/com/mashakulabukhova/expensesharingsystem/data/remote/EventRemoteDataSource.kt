package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.data.remote.model.request.ParticipantsRequestDto
import retrofit2.Response
import retrofit2.http.Body
import javax.inject.Inject

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

    suspend fun postParticipants(body: ParticipantsRequestDto) = eventService.postParticipants(body = body)
    suspend fun getMyEvents() = eventService.getMyEvents()
    suspend fun getParticipants(event_id: String) = eventService.getParticipants(event_id = event_id)
}