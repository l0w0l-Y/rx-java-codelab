package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.HttpBinApi
import com.jraska.rx.codelab.http.HttpModule
import com.jraska.rx.codelab.http.RequestInfo
import com.jraska.rx.codelab.server.RxServer
import com.jraska.rx.codelab.server.RxServerFactory
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task7HotObservables {

  private val rxServer: RxServer = RxServerFactory.create()
  private val httpBinApi: HttpBinApi = HttpModule.httpBinApi()

  @Before
  fun before() {
    RxLogging.enableObservableSubscribeLogging()
  }

  @Test
  fun coldObservable() {
    val getRequest = httpBinApi.getRequest().share()
      .subscribeOn(Schedulers.io())
    getRequest.delaySubscription(250, TimeUnit.MILLISECONDS).subscribe(System.out::println)
    getRequest.subscribe(System.out::println)
    // TODO: Subscribe twice to getRequest and print its values, how many http requests it triggers?
    // TODO: Delay first subscription by 250 ms - delaySubscription()
    // TODO: Modify getRequest to be able to perform only one http request - share()
  }

  @Test
  fun hotObservable() {
    // TODO: Subscribe twice to rxServer.debugLogsHot and print the logs
    // TODO: Delay first subscription by 250ms - delaySubscription(), how is this different than cold observable
    rxServer.debugLogsHot().subscribe(System.out::println)
    rxServer.debugLogsHot().delaySubscription(250, TimeUnit.MILLISECONDS).subscribe(System.out::println)
  }

  @Test
  fun createHotObservableThroughSubject() {
    val getRequest = httpBinApi.getRequest()
    val publishSubject = PublishSubject.create<RequestInfo>()
    publishSubject.subscribe(System.out::println)
    publishSubject.subscribe(System.out::println)
    getRequest.subscribe(publishSubject::onNext, publishSubject::onError)
    // TODO: Create a PublishSubject<RequestInfo> and subscribe twice to it with printing the result
    // TODO: Subscribe to getRequest and publish its values to subject
  }

  @After
  fun after() {
    Thread.sleep(500)
    HttpModule.awaitNetworkRequests()
  }
}
