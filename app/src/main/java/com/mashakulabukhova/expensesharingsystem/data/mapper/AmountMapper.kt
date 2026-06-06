package com.mashakulabukhova.expensesharingsystem.data.mapper

import com.mashakulabukhova.expensesharingsystem.data.remote.model.AmountDto
import com.mashakulabukhova.expensesharingsystem.domain.entity.Amount

fun AmountDto.toAmount(): Amount {
    return Amount(
        amount = this.amount
    )
}