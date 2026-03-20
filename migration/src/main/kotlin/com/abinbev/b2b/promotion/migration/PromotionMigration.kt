package com.abinbev.b2b.promotion.migration

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
@EnableConfigurationProperties(
        MigrationProperties::class
)
class PromotionMigration

private val log: Logger = LoggerFactory.getLogger(PromotionMigration::class.java)

fun main(args: Array<String>) {
    log.info("Starting promotion migration")
    runApplication<PromotionMigration>(*args)
    log.info("Finished promotion migration")
}