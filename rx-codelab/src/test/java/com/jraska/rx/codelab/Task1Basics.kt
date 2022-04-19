package com.jraska.rx.codelab

import io.reactivex.Observable
import org.junit.Test
import java.text.SimpleDateFormat

class Task1Basics {
  @Test
  fun dummyObservable() {
    // TODO:  Create Observable with single String value, subscribe to it and print it to console (Observable.just)
    Observable.just("Hello World").subscribe { println(it) }
  }

  @Test
  fun methodIntoObservable() {
    Observable.fromCallable { System.currentTimeMillis() }
      .map { SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(it) }
      .subscribe { println(it) }
    // TODO:  Create Observable getting current time, subscribe to it and print value to console (Observable.fromCallable)
  }

  @Test
  fun helloOperator() {
    Observable.range(1, 10)
      .filter { isOdd(it) }
      .subscribe { print("$it ") }
    // TODO:  Create Observable with ints 1 .. 10 subscribe to it and print only odd values (Observable.range, observable.filter)
  }

  @Test
  fun receivingError() {
    Observable.error<String>(Exception("Error message"))
      .subscribe(System.out::println, System.out::println)
    // TODO:  Create Observable which emits an error and print the console (Observable.error), subscribe with onError handling
  }

  companion object {
    fun isOdd(value: Int): Boolean {
      return value % 2 == 1
    }
  }
}
