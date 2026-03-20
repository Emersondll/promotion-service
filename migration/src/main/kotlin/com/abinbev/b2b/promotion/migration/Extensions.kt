package com.abinbev.b2b.promotion.migration.platformId

import org.bson.Document
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger("Extensions")

fun Document.getNullableString(field: String): String? = try {
    this.getString(field)
} catch (ex: Exception) {
    logger.error("Failed to get id data from {}", this, ex)
    null
}
