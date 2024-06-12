package com.inetkr.base.utils.helper

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.SendChannel
object ObjectEventChannel {
    private val channel = Channel<Any>()

    fun getSendChannel(): SendChannel<Any> {
        return channel
    }

    fun getChannel(): ReceiveChannel<Any> {
        return channel
    }
}
