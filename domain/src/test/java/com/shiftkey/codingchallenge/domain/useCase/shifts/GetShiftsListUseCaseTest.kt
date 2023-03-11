package com.shiftkey.codingchallenge.domain.useCase.shifts

import com.shiftkey.codingchallenge.core.test.CoroutineRule
import com.shiftkey.codingchallenge.data.repository.ShiftsRepository
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.useCase.GetShiftsListUseCase
import com.shiftkey.codingchallenge.domain.util.DispatchersProvider
import com.shiftkey.codingchallenge.domain.util.NetworkErrorHandler
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneId
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class GetShiftsListUseCaseTest {

    @get:Rule
    val coroutinesTestRule = CoroutineRule()

    private val networkErrorHandler = mockk<NetworkErrorHandler>()
    private val shiftsRepository = mockk<ShiftsRepository> {
        coEvery { getShifts(any(), any(), any(), any(), any()) } returns getShiftsListUseCaseInput
    }
    private val dispatchers = mockk<DispatchersProvider> {
        every { io } returns coroutinesTestRule.dispatcher
    }

    private val useCase = GetShiftsListUseCase(
        networkErrorHandler,
        shiftsRepository,
        dispatchers
    )

    private val address = "someAddress"
    private val start = LocalDateTime.now()
    private val end = start.plusDays(7)
    private val type = "list"
    private val radius = 10

    private val requestParams = GetShiftsListUseCase.Params(
        address, start, end, type, radius
    )

    @Before
    fun setup() {
        mockkStatic(ZoneId::class)
        every { ZoneId.systemDefault() } returns ZoneId.of("UTC")
    }

    @After
    fun clear() {
        unmockkAll()
    }

    @Test
    fun `should fetch shifts list with proper parameters`() = coroutinesTestRule.runTest {
        // When
        useCase.invoke(requestParams)
        // Then
        coVerify(exactly = 1) { shiftsRepository.getShifts(address, start, end, type, radius) }
    }

    @Test
    fun `should properly map api response to domain model`() = coroutinesTestRule.runTest {
        // When
        val data = useCase.invoke(requestParams)
        // Then
        assert(data is ViewState.Success)
        assertEquals(expectedGetShiftsListUseCaseOutput.data, (data as ViewState.Success).data)
    }
}
