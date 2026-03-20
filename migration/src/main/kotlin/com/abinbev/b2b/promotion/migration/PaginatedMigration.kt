package com.abinbev.b2b.promotion.migration

import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit
import java.util.*

interface PaginatedMigration {
    fun migrate(offset: Long, limit: Long)

    fun now(): Date = Date.from(OffsetDateTime.now().toInstant().minus(1, ChronoUnit.DAYS))
}