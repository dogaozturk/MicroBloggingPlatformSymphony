package uk.co.mycompany.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.doga.microbloggingplatformsymphony.domain.Status
import com.doga.microbloggingplatformsymphony.domain.model.Author
import com.doga.microbloggingplatformsymphony.domain.usecase.AuthorUseCase
import com.doga.microbloggingplatformsymphony.ui.author.AuthorViewModel
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.spy
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Unit test for [AuthorViewModel].
 */
class AuthorViewModelTest {

    @Mock
    private lateinit var mockUseCase: AuthorUseCase

    private lateinit var mainActivityViewModel: AuthorViewModel

    @Captor
    private lateinit var argumentCaptor: ArgumentCaptor<PagedList<Author>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainActivityViewModel = AuthorViewModel(mockUseCase)
    }

    @Test
    fun showDataFromApi() {
        Mockito.`when`(mockUseCase.getAuthors(10))

        val testObserver = createObserver()

        mainActivityViewModel.authors.observeForever(testObserver)

        Mockito.verify(testObserver, Mockito.times(3))
            .onChanged(argumentCaptor.capture())

        val values = argumentCaptor.allValues

        Assert.assertEquals(Status.SUCCESS, values[0])
    }


    fun <T> LiveData<T>.getOrAwaitValue(
        time: Long = 2,
        timeUnit: TimeUnit = TimeUnit.SECONDS
    ): T {
        var data: T? = null
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(o: T?) {
                data = o
                latch.countDown()
                this@getOrAwaitValue.removeObserver(this)
            }
        }

        this.observeForever(observer)

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            throw TimeoutException("LiveData value was never set.")
        }

        @Suppress("UNCHECKED_CAST")
        return data as T
    }

    private fun createObserver(): Observer<PagedList<Author>> =
        spy(Observer { })
}