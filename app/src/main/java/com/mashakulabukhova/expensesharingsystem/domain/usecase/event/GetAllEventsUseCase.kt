package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val eventList1 = listOf(
    Event(
        id = "1",
        title = "Конференция по Kotlin",
        category = "IT",
        creatorId = "user_101",
        isFinished = false
    ),
    Event(
        id = "2",
        title = "Вечеринка в честь нового года",
        category = "Party",
        creatorId = "user_102",
        isFinished = true
    ),
    Event(
        id = "3",
        title = "Мастер-класс по рисованию",
        category = "Art",
        creatorId = "user_103",
        isFinished = false
    ),
    Event(
        id = "4",
        title = "Футбольный матч",
        category = "Sport",
        creatorId = "user_104",
        isFinished = true
    ),
    Event(
        id = "5",
        title = "Лекция по истории",
        category = "Education",
        creatorId = "user_105",
        isFinished = false
    ),
    Event(
        id = "6",
        title = "Бизнес-завтрак",
        category = "Networking",
        creatorId = "user_106",
        isFinished = false
    ),
    Event(
        id = "7",
        title = "Хакатон",
        category = "IT",
        creatorId = "user_107",
        isFinished = true
    ),
    Event(
        id = "8",
        title = "Выставка современного искусства",
        category = "Art",
        creatorId = "user_108",
        isFinished = false
    ),
    Event(
        id = "9",
        title = "Йога на свежем воздухе",
        category = "Health",
        creatorId = "user_109",
        isFinished = true
    ),
    Event(
        id = "10",
        title = "Кинопоказ: Новая эра",
        category = "Cinema",
        creatorId = "user_110",
        isFinished = false
    )
)

class GetAllEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(userId: String): NetworkResult<List<Event>> {
//        return eventRepository.getAllEvents(userId)

        return NetworkResult.Success(eventList1)
    }
}