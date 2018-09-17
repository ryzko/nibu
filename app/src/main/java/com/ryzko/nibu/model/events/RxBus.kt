package com.ryzko.nibu.model.events

import android.util.Log
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


/**
 * Simple Rx Event RxBus
 */
object RxBus {

    /**
     * Used to hold all subscriptions for RxBus events and unsubscribe properly when needed.
     */
    private val subscriptionsMap: HashMap<Any, CompositeDisposable?> by lazy {
        HashMap<Any, CompositeDisposable?>()
    }

    /**
     * Avoid using this property directly, exposed only because it's used in inline fun
     */
    val bus = PublishSubject.create<Any>().toSerialized()

    /**
     * Sends an event to RxBus. Can be called from any thread
     */
    fun send(event: Any) {
        bus.onNext(event)
    }

    /**
     * Subscribes for events of certain type T. Can be called from any thread
     */
    inline fun <reified T : Any> observe(): Observable<T> = bus.ofType(T::class.java)

    /**
     * Unregisters subscriber from RxBus events.
     * Calls unsubscribe method of your subscriptions
     * @param subscriber subscriber to unregister
     */
    fun unregister(subscriber: Any) {
        val compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            Log.w("RxBus: ", "Trying to unregister subscriber that wasn't registered")
        } else {
            compositeSubscription.clear()
            subscriptionsMap.remove(subscriber)
            Log.w("RxBus: ", "Unregisters subscriber!")
        }
        Log.i("RxBus: ", "Num of subscribed objects: ${subscriptionsMap.size} ")
    }

    internal fun register(subscriber: Any, subscription: Disposable) {
        var compositeSubscription = subscriptionsMap[subscriber]
        if (compositeSubscription == null) {
            compositeSubscription = CompositeDisposable()
        }
        compositeSubscription.add(subscription)
        subscriptionsMap[subscriber] = compositeSubscription
    }
}

/**
 * Registers the subscription to correctly unregister it later to avoid memory leaks
 * @param subscriber subscriber object that owns this subscription
 */
fun Disposable.registerInBus(subscriber: Any) {
    RxBus.register(subscriber, this)
}