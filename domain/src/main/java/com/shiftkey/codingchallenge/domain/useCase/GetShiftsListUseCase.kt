package com.shiftkey.codingchallenge.domain.useCase

import com.shiftkey.codingchallenge.data.repository.ShiftsRepository
import com.shiftkey.codingchallenge.domain.base.UseCase
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.ShiftsList
import com.shiftkey.codingchallenge.domain.model.toDomain
import com.shiftkey.codingchallenge.domain.util.DispatchersProvider
import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetShiftsListUseCase @Inject constructor(
    private val networkErrorHandler: NetworkErrorHandler,
    private val shiftsRepository: ShiftsRepository,
    private val dispatchers: DispatchersProvider
) : UseCase<ShiftsList>(networkErrorHandler) {

    suspend fun invoke(
        address: String,
        startDateTime: LocalDateTime,
        endDateTime: LocalDateTime,
        type: String = "list",
        radius: Int = 150
    ): ViewState<ShiftsList> = withContext(dispatchers.io) {
        safeApiCall {
            shiftsRepository.getShifts(
                address, startDateTime, endDateTime, type, radius
            ).toDomain()
        }
    }
}
