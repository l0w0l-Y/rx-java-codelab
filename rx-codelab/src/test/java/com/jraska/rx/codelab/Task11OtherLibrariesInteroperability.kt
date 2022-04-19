package com.jraska.rx.codelab

import hu.akarnokd.rxjava.interop.RxJavaInterop
import io.reactivex.BackpressureStrategy
import io.reactivex.Observable
import org.junit.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class Task11OtherLibrariesInteroperability {

  // TODO: Uncomment Gradle dependencies for this test

  @Test
  fun givenRxJava1and2() {
    val observableV1 =
      RxJavaInterop.toV1Observable(Observable.just("Hello World!").toFlowable(BackpressureStrategy.DROP))
    val observableV2 = RxJavaInterop.toV2Observable(observableV1)
    observableV1.subscribe(System.out::println)
    observableV2.subscribe(System.out::println)
    // TODO: Create RxJava 2 observable and convert it to RxJava 1 observable and vice versa
    // TODO: Create RxJava 2 PublishProcessor and convert it to RxJava 1 Subject and back again
  }

  @Test
  fun reactiveStreams_reactor_rxjava() {
    val flux = Flux.from(Observable.just("Hello World").toFlowable(BackpressureStrategy.DROP))
    flux.subscribe(System.out::println)
    val mono = Mono.from(Observable.just("Hello World").toFlowable(BackpressureStrategy.DROP))
    mono.subscribe(System.out::println)
    // TODO: Create RxJava 2 Observable and convert it to Reactor Flux, subscribe
    // TODO: Create RxJava 2 Single and convert it to Reactor Mono, subscribe
  }
}
