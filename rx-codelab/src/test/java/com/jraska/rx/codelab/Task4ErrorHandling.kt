package com.jraska.rx.codelab

import com.jraska.rx.codelab.http.HttpModule
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test

class Task4ErrorHandling {
  private val httpBinApi = HttpModule.httpBinApi()

  @Before
  fun before() {
    RxLogging.enableObservableSubscribeLogging()
  }

  @Test
  fun printErrorMessage() {
    // TODO: Subscribe and incoming error message - httpBinApi.failingGet(), subscribe() with 2 parameters
    httpBinApi.failingGet().subscribe(System.out::println, System.out::println)
  }

  @Test
  fun onErrorReturnItem_emitCustomItemOnError() {
    // TODO: When an error happens, emit syntheticBody(), httpBinApi.failingGet()
    httpBinApi.failingGet().onErrorReturnItem(syntheticBody()).map { it.string() }.subscribe(System.out::println)
  }

  @Test
  fun onErrorResumeNext_subscribeToExtraObservableOnError() {
    // TODO: When an error happens, subscribe to extra observable - httpBinApi.backupGet()
    httpBinApi.failingGet().onErrorResumeNext(httpBinApi.backupGet()).map { it.string() }.subscribe(System.out::println)
  }

  @Test
  fun retry_retryOnError() {
    // TODO: httpBinApi.flakyGet is a bit flakey and often fails, use retry to make it always complete
    httpBinApi.flakyGet().retry().subscribe(System.out::println)
  }

  companion object {

    internal fun syntheticBody(): ResponseBody {
      return ResponseBody.create(MediaType.get("application/json"), "{}")
    }
  }
}
