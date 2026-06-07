package com.mashakulabukhova.expensesharingsystem.data.remote

import com.mashakulabukhova.expensesharingsystem.data.model.EventDto
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.EventRequestDto
import com.mashakulabukhova.expensesharingsystem.data.model.request.ParticipantsRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface EventService {

    @POST("events")
    suspend fun postEvent(@Body body: EventRequestDto): Response<EventDto>

    @POST("participants")
    suspend fun postParticipants(@Body body: ParticipantsRequestDto): Response<Unit>

    @GET("events")
    suspend fun getMyEvents(): Response<List<EventDto>>

    @GET("participants/{event_id}")
    suspend fun getParticipants(@Path("event_id") event_id: String): Response<List<UserDto>>


    @GET("events/{user_id}")
    suspend fun getAllEvents(@Path("user_id") user_id: String): Response<List<EventDto>>

    @GET("events/{id}")
    suspend fun getEvent(@Path("id") event_id: String): Response<EventDto>


    @PUT("events/{event_id}")
    suspend fun putEvent(
        @Path("event_id") event_id: String,
        @Body body: EventRequestDto
    ): Response<EventDto>

    @PATCH("events/{event_id}")
    suspend fun patchEvent(
        @Path("event_id") event_id: String,
        @Body body: EventRequestDto
    ): Response<EventDto>

    @DELETE("events/{event_id}")
    suspend fun deleteEvent(@Path("event_id") event_id: String): Response<Void>
}