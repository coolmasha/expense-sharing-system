package com.mashakulabukhova.expensesharingsystem.data.repository

import com.mashakulabukhova.expensesharingsystem.data.mapper.toEvent
import com.mashakulabukhova.expensesharingsystem.data.mapper.toEventRequestDto
import com.mashakulabukhova.expensesharingsystem.data.remote.EventRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.remote.model.EventDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.EventRequest
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : EventRepository {
    override suspend fun getAllEvents(user_id: String): NetworkResult<List<Event>> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.getAllEvents(user_id) },
            mapper = { eventDtoList: List<EventDto> -> eventDtoList.map { it.toEvent() } }
        )
    }

    override suspend fun getEvent(event_id: String): NetworkResult<Event> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.getEvent(event_id) },
            mapper = { eventDto: EventDto -> eventDto.toEvent() }
        )
    }

    override suspend fun postEvent(body: EventRequest): NetworkResult<Event> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.postEvent(body.toEventRequestDto()) },
            mapper = { eventDto: EventDto -> eventDto.toEvent() }
        )
    }

    override suspend fun putEvent(
        event_id: String,
        body: EventRequest
    ): NetworkResult<Event> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.putEvent(event_id, body.toEventRequestDto()) },
            mapper = { eventDto: EventDto -> eventDto.toEvent() }
        )
    }

    override suspend fun patchEvent(
        event_id: String,
        body: EventRequest
    ): NetworkResult<Event> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.putEvent(event_id, body.toEventRequestDto()) },
            mapper = { eventDto: EventDto -> eventDto.toEvent() }
        )
    }

    override suspend fun deleteEvent(
        event_id: String
    ): NetworkResult<Unit> {
        return baseApiResponse.safeApiCallNoBody(
            api = { eventRemoteDataSource.deleteEvent(event_id) }
        )
    }
}