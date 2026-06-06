package com.mashakulabukhova.expensesharingsystem.domain.usecase.event

import com.mashakulabukhova.expensesharingsystem.R
import com.mashakulabukhova.expensesharingsystem.domain.entity.Event
import com.mashakulabukhova.expensesharingsystem.domain.entity.User
import com.mashakulabukhova.expensesharingsystem.domain.repository.EventRepository
import com.mashakulabukhova.expensesharingsystem.utils.NetworkResult
import javax.inject.Inject

private val users1 = listOf(
    User(id = "6", username = "anna_koval", email = "anna@ukr.net", status = "active"),
    User(id = "7", username = "serg_ivanov", email = "serg@example.com", status = "active"),
    User(id = "8", username = "olga_smith", email = "olga@gmail.com", status = "offline"),
    User(id = "9", username = "maxim_litvin", email = "maxim@mail.ru", status = "active"),
    User(id = "10", username = "nadia_f", email = "nadia@example.com", status = "away")
)

private val users2 = listOf(
    User(id = "11", username = "pavel_k", email = "pavel@mail.ru", status = "active"),
    User(id = "12", username = "irina_m", email = "irina@example.com", status = "offline")
)

private val users3 = listOf(
    User(id = "13", username = "alex_b", email = "alex@gmail.com", status = "active")
)

//private val eventList1 = listOf(
//    Event(
//        id = "1",
//        title = "Конференция по Kotlin",
//        iconId = 76,
//        creatorId = "user_101",
//        participants = users1,
//        isFinished = false
//    ),
//    Event(
//        id = "2",
//        title = "Вечеринка в честь нового года",
//        iconId = 33,
//        creatorId = "user_102",
//        participants = users2,
//        isFinished = true
//    ),
//    Event(
//        id = "3",
//        title = "Мастер-класс по рисованию",
//        iconId = 65,
//        currency = "RUB",
//        creatorId = "user_103",
//        participants = listOf(),  // пустой список участников
//        isFinished = false
//    ),
//    Event(
//        id = "4",
//        title = "Футбольный матч",
//        iconId = 3,
//        currency = "USD",
//        creatorId = "user_104",
//        participants = users3,
//        isFinished = true
//    ),
//    Event(
//        id = "5",
//        title = "Лекция по истории",
//        iconId = 0,
//        currency = "RUB",
//        creatorId = "user_105",
//        participants = listOf(),
//        isFinished = false
//    ),
//    Event(
//        id = "6",
//        title = "Бизнес-завтрак",
//        iconId = 1,
//        currency = "USD",
//        creatorId = "user_106",
//        participants = users1,
//        isFinished = false
//    ),
//    Event(
//        id = "7",
//        title = "Хакатон",
//        iconId = 0,
//        currency = "USD",
//        creatorId = "user_107",
//        participants = users2,
//        isFinished = true
//    ),
//    Event(
//        id = "8",
//        title = "Выставка современного искусства",
//        iconId = 0,
//        currency = "RUB",
//        creatorId = "user_108",
//        participants = users3,
//        isFinished = false
//    ),
//    Event(
//        id = "9",
//        title = "Йога на свежем воздухе",
//        iconId = 2,
//        currency = "RUB",
//        creatorId = "user_109",
//        participants = listOf(),
//        isFinished = true
//    ),
//    Event(
//        id = "10",
//        title = "Кинопоказ: Новая эра",
//        iconId = 0,
//        currency = "USD",
//        creatorId = "user_110",
//        participants = users1,
//        isFinished = false
//    )
//)

class GetAllEventsUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(userId: String): NetworkResult<List<Event>> {
        return eventRepository.getAllEvents(userId)

//        return NetworkResult.Success(eventList1)
    }
}