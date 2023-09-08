package com.example.remindersystem.ui.form

import androidx.navigation.NavController
import com.example.remindersystem.model.Reminder
import com.example.remindersystem.network.image.ImageSearchService
import com.example.remindersystem.repository.ReminderRepository
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class NewReminderFormViewModelTest {

    @Mock
    private lateinit var repository: ReminderRepository

    @Mock
    private lateinit var imageSearchService: ImageSearchService

    @Mock
    private lateinit var navController: NavController

    private lateinit var viewModel: NewReminderFormViewModel
    private val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        viewModel = NewReminderFormViewModel(repository, imageSearchService, navController)
    }

    @Test
    fun givenEmptyFields_whenAddNewReminder_thenShowToast() = runTest {
        viewModel.name.value = ""
        viewModel.date.value = ""

        val viewModelSpy = spy(viewModel)
        viewModelSpy.addNewReminder()

        verify(viewModelSpy).handleEvent(NewReminderFormEvent.ShowToast("Fields must not be empty"))

    }

    @Test
    fun givenValidFields_whenAddNewReminder_thenSearchForImage() = runTest {
        val name = "Reminder 1"
        val date = "10/09/2023"
        viewModel.name.value = name
        viewModel.date.value = date

        `when`(imageSearchService.searchForImageUrl(anyString())).thenReturn("http://www.example.google.com/teste_image.jpg")
        viewModel.addNewReminder()

        verify(imageSearchService).searchForImageUrl(anyString())
    }

    @Test
    fun givenValidFields_whenAddNewReminder_thenInsertReminder() = runTest {
        val name = "Reminder 1"
        val date = "10/09/2023"
        val imageUrl = "http://www.example.google.com/teste_image.jpg"
        viewModel.name.value = name
        viewModel.date.value = date
        val reminder = Reminder(
            name = name,
            date = LocalDate.parse(date, dateFormatter),
            imageUrl = imageUrl
        )

        `when`(imageSearchService.searchForImageUrl(anyString())).thenReturn(imageUrl)
        viewModel.addNewReminder()

        verify(repository).insertReminder(reminder)
    }

    @Test
    fun givenValidFields_whenAddNewReminder_thenNavigate() = runTest {
        val name = "Reminder 1"
        val date = "10/09/2023"
        val imageUrl = "http://www.example.google.com/teste_image.jpg"
        viewModel.name.value = name
        viewModel.date.value = date

        `when`(imageSearchService.searchForImageUrl(anyString())).thenReturn(imageUrl)

        viewModel.addNewReminder()

        verify(navController).navigate(NewReminderFormFragmentDirections.actionNewReminderFormFragmentToReminderListFragment())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun givenEvent_whenHandleEvent_theEventIsEmitted() = runTest {
        val event = NewReminderFormEvent.ShowToast("Test Event")
        var eventEmmited = false

        val job = launch(UnconfinedTestDispatcher(testScheduler)) {
            viewModel.events.collect{
                if (it == event){
                    eventEmmited = true
                }
            }
        }
        viewModel.handleEvent(event)
        job.cancel()

        assertTrue(eventEmmited)
    }

    @OptIn(DelicateCoroutinesApi::class)
    @After
    fun close() {
        Dispatchers.shutdown()
    }
}
