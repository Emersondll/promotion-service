package com.abinbev.b2b.promotion.migration.platformId

import com.abinbev.b2b.promotion.migration.MigrationProperties
import com.abinbev.b2b.promotion.migration.PaginatedAsyncMigration
import com.abinbev.b2b.promotion.migration.ResultCounter
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Component

@Component
@ConditionalOnProperty(prefix = "migration.platform-id", name = ["enabled"], havingValue = "true")
class PlatformIdMigration(
        promotionPlatformIdMigration: PromotionPlatformIdMigration,
        migrationProperties: MigrationProperties,
        resultCounter: ResultCounter
) {
    private val log: Logger = LoggerFactory.getLogger(PlatformIdMigration::class.java)

    init {
        log.info("Starting platformId migration")

        PaginatedAsyncMigration(migrationProperties, promotionPlatformIdMigration).execute()

        log.info(
                "PlatformId migration ended with {} created and {} updated",
                resultCounter.getCreatedCount(),
                resultCounter.getUpdatedCount()
        )
    }
}