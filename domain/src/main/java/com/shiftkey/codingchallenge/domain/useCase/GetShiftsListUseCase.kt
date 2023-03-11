package com.shiftkey.codingchallenge.domain.useCase

import com.shiftkey.codingchallenge.data.repository.ShiftsRepository
import com.shiftkey.codingchallenge.domain.base.UseCase
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.model.shift.ShiftsList
import com.shiftkey.codingchallenge.domain.model.shift.toDomain
import com.shiftkey.codingchallenge.domain.util.DispatchersProvider
import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import javax.inject.Inject

class GetShiftsListUseCase @Inject constructor(
    networkErrorHandler: NetworkErrorHandler,
    private val shiftsRepository: ShiftsRepository,
    private val dispatchers: DispatchersProvider
) : UseCase<ShiftsList>(networkErrorHandler) {

    suspend fun invoke(
        params: Params
    ): ViewState<ShiftsList> = withContext(dispatchers.io) {
        safeApiCall {
            shiftsRepository.getShifts(
                params.address, params.startDateTime, params.endDateTime, params.type, params.radius
            ).toDomain()
        }
    }

    data class Params(
        val address: String = DALLAS_ADDRESS,
        val startDateTime: LocalDateTime = LocalDateTime.now(),
        val endDateTime: LocalDateTime = LocalDateTime.now().plusDays(7),
        val type: String = "list",
        val radius: Int = 10
    ) {

    }

    companion object {
        private const val DALLAS_ADDRESS = "Dallas, TX"
    }
}
