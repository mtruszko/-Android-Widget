package com.example.maro.proj5and


object EventBus {

    val callbacks = mutableListOf<EventCallback>()

    fun register(eventCallback: EventCallback) = callbacks.add(eventCallback)

    fun unregister(eventCallback: EventCallback) = callbacks.remove(eventCallback)

    fun send(event: Event) = callbacks.forEach { it.onEventReceived(event) }
}

enum class Event {
    PAUSE,
    NEXT
}

interface EventCallback {
    fun onEventReceived(event: Event)
}