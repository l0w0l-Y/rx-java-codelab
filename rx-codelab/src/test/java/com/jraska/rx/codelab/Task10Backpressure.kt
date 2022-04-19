package com.jraska.rx.codelab

import com.jraska.rx.codelab.server.Log
import com.jraska.rx.codelab.server.RxServer
import com.jraska.rx.codelab.server.RxServerFactory
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import org.junit.After
import org.junit.Test

class Task10Backpressure {
  private val rxServer: RxServer = RxServerFactory.create()

  @Test
  fun backpressureFail() {
    rxServer.allLogsHot().observeOn(Schedulers.newThread()).subscribe(reallySlowLogConsumer())
    // TODO: Subscribe to rxServer.allLogsHot on different thread (observeOn), use reallySlowLogConsumer
  }

  @Test
  fun noBackpressure() {
    rxServer.allLogsHot().toObservable().observeOn(Schedulers.newThread()).subscribe(reallySlowLogConsumer())
    // TODO: Modify example above to ignore backpressure and continue forever (toObservable())
  }

  @Test
  fun onBackpressureDrop() {
    rxServer.allLogsHot().onBackpressureDrop(slowLogConsumer()).toObservable().observeOn(Schedulers.newThread())
      .subscribe(reallySlowLogConsumer())
    // TODO: Drop values on backpressure with logging which values are dropped (onBackpressureDrop), use slowLogConsumer
  }

  @Test
  fun buffer_backpressureBatching() {
    rxServer.allLogsHot().buffer(10).observeOn(Schedulers.newThread()).subscribe(batchLogsConsumer())
    // TODO: batch values and process them with batchLogsConsumer()
    // TODO: Experiment with different sizes of buffer
  }

  @Test
  fun onBackpressureBuffer() {
    rxServer.allLogsHot()
      .onBackpressureBuffer(10)
      .observeOn(Schedulers.newThread())
      .subscribe(slowLogConsumer())
    // TODO: Try different sizes of backpressure buffer to better understand how internal buffers work
  }

  private fun slowLogConsumer(): Consumer<Log> {
    return Consumer { log ->
      Thread.sleep(25)
      println(log)
    }
  }

  private fun reallySlowLogConsumer(): Consumer<Log> {
    return Consumer { log ->
      Thread.sleep(100)
      println(log)
    }
  }

  private fun batchLogsConsumer(): Consumer<List<Log>> {
    return Consumer { logs ->
      Thread.sleep(100)
      println(logs)
    }
  }

  @After
  fun after() {
    Thread.sleep(3000)
  }
}
