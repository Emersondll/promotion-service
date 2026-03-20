package com.abinbev.b2b.promotion.migration

import org.springframework.stereotype.Component

@Component
class ResultCounter {
    private var updatedCount: Int = 0
    private var createdCount: Int = 0

    fun getUpdatedCount(): Int = updatedCount
    fun getCreatedCount(): Int = createdCount

    fun addUpdated(value: Int) {
        updatedCount += value
    }

    fun addCreated(value: Int) {
        createdCount += value
    }
}