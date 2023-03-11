package com.shiftkey.codingchallenge.shifts.viewModel

import com.shiftkey.codingchallenge.core.test.CoroutineRule
import com.shiftkey.codingchallenge.domain.base.ViewState
import com.shiftkey.codingchallenge.domain.useCase.GetShiftsListUseCase
import com.shiftkey.codingchallenge.domain.useCase.SaveShiftDetailsUseCase
import com.shiftkey.codingchallenge.shifts.list.ShiftsListViewModel
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ShiftsListViewModelTest {

    @get:Rule
    val coroutinesTestRule = CoroutineRule()

    private val nowDate = LocalDate.of(2023, 3, 11)
    private val now = LocalDateTime.of(2023, 3, 11, 18, 0, 0, 0)
    private val newDate = nowDate.plusDays(3)

    private val address = "Dallas, TX"
    private val start = now
    private val end = start.plusDays(7)
    private val type = "list"
    private val radius = 10

    private val startRequestParams = GetShiftsListUseCase.Params(
        address, start, end, type, radius
    )

    private val errorMessage = "error"
    private val getShiftsListUseCase = mockk<GetShiftsListUseCase> {
        coEvery { this@mockk.invoke(any()) } returns expectedGetShiftsListUseCaseOutput
    }
    private val saveShiftDetailsUseCase = mockk<SaveShiftDetailsUseCase>()

    private lateinit var viewModel: ShiftsListViewModel

    private fun createViewModel() {
        viewModel = ShiftsListViewModel(
            getShiftsListUseCase,
            saveShiftDetailsUseCase
        )
    }

    @Before
    fun setup() {
        mockkStatic(LocalDateTime::class)
        mockkStatic(LocalDate::class)
        every { LocalDateTime.now() } returns now
        every { LocalDate.now() } returns nowDate
    }

    @After
    fun clear() {
        unmockkAll()
    }

    @Test
    fun `should fetch shifts list for current week on start`() = coroutinesTestRule.runTest {
        createViewModel()
        assert(viewModel.state.value.isLoading)
        val job = launch {
            viewModel.state.collect { }
        }
        coVerify { getShiftsListUseCase.invoke(startRequestParams) }
        assertEquals(expectedShiftsScreenState.copy(selectedDate = nowDate), viewModel.state.value)
        job.cancelAndJoin()
    }

    @Test
    fun `should reload data`() = coroutinesTestRule.runTest {
        createViewModel()
        val job = launch {
            viewModel.state.collect { }
        }
        coVerify { getShiftsListUseCase.invoke(startRequestParams) }
        viewModel.reload()
        coVerify { getShiftsListUseCase.invoke(startRequestParams) }
        job.cancelAndJoin()
    }

    @Test
    fun `should show error`() = coroutinesTestRule.runTest {
        coEvery { getShiftsListUseCase.invoke(any()) } returns ViewState.Error(errorMessage)
        createViewModel()
        val job = launch {
            viewModel.state.collect { }
        }
        assertEquals(true, viewModel.state.value.isError)
        assertEquals(errorMessage, viewModel.state.value.errorMessage)
        job.cancelAndJoin()
    }

    @Test
    fun `should change selected day`() = coroutinesTestRule.runTest {
        createViewModel()
        val job = launch {
            viewModel.state.collect { }
        }
        viewModel.selectDate(newDate)
        assertEquals(newDate, viewModel.state.value.selectedDate)
        job.cancelAndJoin()
    }

    @Test
    fun `should scroll to date`() = coroutinesTestRule.runTest {
        createViewModel()
        val job = launch {
            viewModel.state.collect { }
        }
        viewModel.scrollToDay(newDate)
        assertEquals(newDate, viewModel.state.value.scrollToDate)
        job.cancelAndJoin()
    }
}
