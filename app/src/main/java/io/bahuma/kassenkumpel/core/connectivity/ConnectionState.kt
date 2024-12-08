package io.bahuma.kassenkumpel.core.connectivity

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}