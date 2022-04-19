package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.HttpModule
import com.jraska.rx.codelab.server.RxServerFactory
import io.reactivex.schedulers.TestScheduler
import io.reactivex.subjects.PublishSubject
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task8Testing {
  private val rxServer = RxServerFactory.create()
  private val httpBinApi = HttpModule.httpBinApi()

  @Test
  fun testObserver_onColdObservable() {
    val request = httpBinApi.getRequest()
    request.test()
      .assertSubscribed()
      .assertValueCount(1)
      .assertValue { it.url.contains("show_env") }
      .assertComplete()
    // TODO: Subscribe with test() method to request and assert values count, value has "show_env" in url and no errors were thrown
  }

  @Test
  fun testSubscriber_onHotFlowable() {
    val logObservable = rxServer.debugLogsHot()
    logObservable.test()
      .assertSubscribed()
      .awaitCount(5)
      .assertNoErrors()
      .assertNotComplete()
    // TODO: Subscribe with test() method to rxServer.debugLogsHot, wait for 5 values(awaitCount), assert no errors and stream not completed
  }

  @Test
  fun testScheduler_advancingTime() {
    val testScheduler = TestScheduler()

    val subject = PublishSubject.create<String>()
    val bufferedObservable = subject.buffer(100, TimeUnit.MILLISECONDS, testScheduler)
    bufferedObservable.subscribe { println(it) }

    subject.onNext("First")
    subject.onNext("Batch")
    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
    subject.onNext("Second")
    subject.onNext("Longer")
    subject.onNext("Batch")
    testScheduler.advanceTimeBy(100, TimeUnit.MILLISECONDS)
    // TODO: Move time of test scheduler so the [First, Batch] and [Second, Longer, Batch] are printed together
  }

  @Test
  fun schedulerProvider_runSynchronouslyInTest() {
    // TODO: Create an instance of IpViewModel and get ip synchronously. Use SchedulerProvider.testSchedulers()
  }
}
