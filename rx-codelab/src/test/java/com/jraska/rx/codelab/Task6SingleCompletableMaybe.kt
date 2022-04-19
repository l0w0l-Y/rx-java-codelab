package com.jraska.rx.codelab

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Consumer
import org.junit.After
import org.junit.Test
import java.util.concurrent.TimeUnit

class Task6SingleCompletableMaybe {

  @Test
  fun helloSingle() {
    // TODO: Create Single emitting one item and subscribe to it printing onSuccess value,
    // TODO: Convert Single to completable and print message about completion.
    val observables = Single.just("Hello World")
    observables.subscribe(Consumer { println(it) })
    observables.ignoreElement().subscribe{ println("Complete") }
  }

  @Test
  fun maybe() {
    // TODO: Create a Single with one value to emit and convert it to maybe
    Single.just("Hello World!").toMaybe().subscribe(System.out::println, System.err::println)
    Single.error<Exception>(Exception("Error World!")).toMaybe().subscribe(System.out::println, System.err::println)
  }

  @Test
  fun transformObservableToCompletable() {
    // TODO: Create Observable emitting values 1 .. 10 and make it completable (ignoreElements), subscribe and print
    Observable.range(1,10).ignoreElements().subscribe{ println("Completed") }
  }

  @Test
  fun intervalRange_firstOrError_observableToSingle() {
    // TODO: Create Observable emitting 5 items each 10 ms (intervalRange)
    // TODO: Get first element (firstOrError)
    // TODO: Play around with skip operator, implement error handling for skip(5)
    Observable.intervalRange(1,5,0, 10, TimeUnit.MILLISECONDS).subscribe(System.out::println)
  }

  @After
  fun after() {
    // to see easily time dependent operations, because we are in unit tests
    Thread.sleep(100)
  }
}
