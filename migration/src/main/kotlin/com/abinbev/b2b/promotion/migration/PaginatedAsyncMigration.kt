package com.abinbev.b2b.promotion.migration

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.slf4j.LoggerFactory
import kotlin.math.ceil

open class PaginatedAsyncMigration(
    private val migrationProperties: MigrationProperties,
    private val migrationStrategy: PaginatedMigration
) : PaginatedMigration {
    private val logger = LoggerFactory.getLogger(PaginatedAsyncMigration::class.java)

    fun execute() {
        logger.info("Migrating with properties {}", migrationProperties)
        val totalOfElements = migrationProperties.getTotalOfElements()
        logger.info("Total of elements found to migrate {}", totalOfElements)
        if (totalOfElements == 0L) {
            logger.error("No elements found to migrate")
            return
        }
        val numberOfThreads = migrationProperties.getNumberOfThreads() ?: run {
            logger.error("Number of threads not defined")
            return
        }
        val totalElementsByNode = totalOfElements / migrationProperties.getNodePool()
        val nodeOffset = (migrationProperties.getNode() - 1) * totalElementsByNode
        val pages = ceil(
            totalElementsByNode.toDouble() / migrationProperties.getPageSize().toDouble()
        ).toLong()
        val pageSize = migrationProperties.getPageSize().let {
            if (it > totalElementsByNode) totalElementsByNode else it
        }

        logger.info(
            "Total of pages {} and elements by node {} to migrate with {} threads and initial offset {} with page size {}",
            pages,
            totalElementsByNode,
            numberOfThreads,
            nodeOffset,
            pageSize
        )

        if (pages <= numberOfThreads) {
            processAsync(nodeOffset, pages, pageSize)
        } else {
            var step = 0L
            var offset = nodeOffset
            while (step <= pages) {
                val nextStep = step + numberOfThreads

                offset = if (nextStep > pages) {
                    val difference = nextStep - pages
                    processAsync(offset, numberOfThreads - difference, pageSize)
                } else {
                    processAsync(offset, numberOfThreads, pageSize)
                }

                step = nextStep
            }
        }
    }

    private fun processAsync(nodeOffset: Long, limit: Long, pageSize: Long): Long {
        var lastOffset: Long
        runBlocking {
            logger.info(
                "Migration starting for offset {} and limit page {}",
                nodeOffset,
                limit
            )
            lastOffset = migrateAsync(nodeOffset, limit, pageSize)
        }
        logger.info(
            "Migration concluded for offset {} and limit page {}",
            nodeOffset,
            limit
        )
        return lastOffset
    }

    private suspend fun migrateAsync(nodeOffset: Long, limit: Long, pageSize: Long): Long {
        var lastOffset = 0L
        withContext(Dispatchers.Default) {
            for (i in 0 until limit) {
                val offSet = if (i == 0L) nodeOffset else (i * pageSize) + nodeOffset
                launch(Dispatchers.Default) { migrate(offSet, pageSize) }
                lastOffset = offSet
            }
        }
        return lastOffset + pageSize
    }

    override fun migrate(offset: Long, limit: Long) {
        migrationStrategy.migrate(offset, limit)
    }

}