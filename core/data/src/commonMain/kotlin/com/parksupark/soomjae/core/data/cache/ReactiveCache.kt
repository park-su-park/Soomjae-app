package com.parksupark.soomjae.core.data.cache

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ReactiveCache<K, V> {
    private val state = mutableMapOf<K, V>()
    private val _updates = MutableStateFlow<Map<K, V>>(emptyMap())
    val updates = _updates.asStateFlow()

    operator fun get(key: K): V? = state[key]

    fun update(
        key: K,
        value: V,
    ) {
        state[key] = value
        _updates.update { it + (key to value) }
    }

    fun remove(key: K) {
        state.remove(key)
        _updates.update { it - key }
    }

    fun clear() {
        state.clear()
        _updates.value = emptyMap()
    }
}
