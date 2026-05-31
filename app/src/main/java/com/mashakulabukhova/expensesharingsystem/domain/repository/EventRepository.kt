package com.mashakulabukhova.expensesharingsystem.domain.repository

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.EventRequest
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import kotlinx.coroutines.flow.Flow


interface EventRepository {

    suspend fun getAllEvents(user_id: String): NetworkResult<List<Event>>
    suspend fun getEvent(event_id: String): NetworkResult<Event>
    suspend fun postEvent(body: EventRequest): NetworkResult<Event>
    suspend fun putEvent(event_id: String, body: EventRequest): NetworkResult<Event>
    suspend fun patchEvent(event_id: String, body: EventRequest): NetworkResult<Event>
    suspend fun deleteEvent(event_id: String): NetworkResult<Unit>
}