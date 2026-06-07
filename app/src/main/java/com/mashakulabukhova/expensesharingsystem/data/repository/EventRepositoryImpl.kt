package com.mashakulabukhova.expensesharingsystem.data.repository

import android.util.Log
import com.mashakulabukhova.expensesharingsystem.data.mapper.toEvent
import com.mashakulabukhova.expensesharingsystem.data.mapper.toEventRequestDto
import com.mashakulabukhova.expensesharingsystem.data.mapper.toParticipantsRequestDto
import com.mashakulabukhova.expensesharingsystem.data.mapper.toUser
import com.mashakulabukhova.expensesharingsystem.data.remote.EventRemoteDataSource
import com.mashakulabukhova.expensesharingsystem.data.model.EventDto
import com.mashakulabukhova.expensesharingsystem.data.model.UserDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.entity.request.EventRequest
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.BaseApiResponse
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val eventRemoteDataSource: EventRemoteDataSource,
    private val baseApiResponse: BaseApiResponse
) : EventRepository {

    override suspend fun createEventWithoutBody(body: EventRequest): NetworkResult<Unit> {
        val eventResult = baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.postEvent(body.toEventRequestDto()) },
            mapper = { eventDto: EventDto -> eventDto.toEvent() }
        )

        Log.d("CreateEvent", "eventResult = $eventResult")

        return when (eventResult) {
            is NetworkResult.Error -> {
                Log.d("CreateEvent", "Error here")
                NetworkResult.Error(code = eventResult.code, message = "Не удалось создать событие")
            }

            is NetworkResult.Loading -> {
                Log.d("CreateEvent", "Loading here")
                NetworkResult.Loading
            }

            is NetworkResult.Success -> {
                Log.d("CreateEvent", "Success here")
                val eventId = eventResult.result.id
                var allParticipantsSuccess = true

                for (user in body.participants) {
                    Log.d("CreateEvent", "User: $user")
                    val participantsResult = baseApiResponse.safeApiCallIgnoreBody(
                        api = {eventRemoteDataSource.postParticipants(user.toParticipantsRequestDto(eventId))}
                    )

                    Log.d("CreateEvent", "ParticipantsResult: $participantsResult")
                    if (participantsResult is NetworkResult.Error) {
                        allParticipantsSuccess = false
                        break
                    }
                }

                if (allParticipantsSuccess) {
                    NetworkResult.Success(Unit)
                } else {
                    NetworkResult.Error(
                        message = "Событие создано, но не удалось добавить участников"
                    )
                }
            }
        }
    }

    override suspend fun getMyEvents(): NetworkResult<List<Event>> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.getMyEvents() },
            mapper = { eventDtoList: List<EventDto> -> eventDtoList.map { it.toEvent() } }
        )
    }

    override suspend fun getParticipants(event_id: String): NetworkResult<List<User>> {
        return baseApiResponse.safeApiCall(
            api = { eventRemoteDataSource.getParticipants(event_id) },
            mapper = { userDtoList: List<UserDto> -> userDtoList.map { it.toUser() } }
        )
    }

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