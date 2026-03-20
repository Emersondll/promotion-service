package com.abinbev.b2b.promotion.migration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("migration")
data class MigrationProperties(
    private val totalOfElements: Long = 0,
    private val country: String = "",
    private val node: Long = 1,
    private val nodePool: Long = 1,
    private val pageSize: Long = 1,
    private val threadsAmount: Long? = null,
    private val database: Database = Database()
) {
    init {
        if (node < 1 || nodePool < 1 || country.isBlank()) {
            throw Exception("Invalid arguments for migration properties")
        }
    }

    fun getTotalOfElements(): Long = this.totalOfElements
    fun getCountry(): String = this.country
    fun getNode(): Long = this.node
    fun getNodePool(): Long = this.nodePool
    fun getPageSize(): Long = this.pageSize
    fun getNumberOfThreads(): Long? = this.threadsAmount

    fun getPromotionDatabase(): String = country.plus(database.promotion)


    data class Database(
        val promotion: String = ""
    )
}