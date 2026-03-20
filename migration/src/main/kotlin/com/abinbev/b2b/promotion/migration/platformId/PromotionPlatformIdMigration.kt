package com.abinbev.b2b.promotion.migration.platformId

import com.abinbev.b2b.commons.platformId.core.PlatformIdEncoderDecoder
import com.abinbev.b2b.commons.platformId.core.vo.PromotionPlatformId
import com.abinbev.b2b.promotion.migration.MigrationProperties
import com.abinbev.b2b.promotion.migration.PaginatedMigration
import com.abinbev.b2b.promotion.migration.ResultCounter
import org.bson.Document
import org.slf4j.LoggerFactory
import org.springframework.data.mongodb.core.BulkOperations
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.stereotype.Component

@Component
class PromotionPlatformIdMigration(
        private val migrationProperties: MigrationProperties,
        private val database: MongoOperations,
        private val resultCounter: ResultCounter
) : PaginatedMigration {
    private val logger = LoggerFactory.getLogger(PromotionPlatformIdMigration::class.java)

    override fun migrate(offset: Long, limit: Long) {
        logger.info(
                "Starting promotion migration with offset {} and limit {}",
                offset,
                limit
        )
        val promotions =
                promotionCollection()
                        .find()
                        .skip(offset.toInt())
                        .limit(limit.toInt())
                        .cursor()
        val promotionBulkOperations: BulkOperations = database.bulkOps(
                BulkOperations.BulkMode.UNORDERED,
                migrationProperties.getPromotionDatabase()
        )

        while (promotions.hasNext()) {
            val promotion: Document = promotions.next()
            val vendorPromotionId: String? =
                    promotion.getNullableString(VENDOR_PROMOTION_ID_FIELD)
            val vendorId: String? = promotion.getNullableString(VENDOR_ID_FIELD)

            if (vendorId.isNullOrEmpty() || vendorPromotionId.isNullOrEmpty()) {
                logger.warn("Empty required attribute for promotion {}", promotion)
                continue
            }
            val platformIdEncoderDecoder = PlatformIdEncoderDecoder()
            val promotionPlatformId = PromotionPlatformId(vendorId, vendorPromotionId)
            val promotionPlatformIdEncoded =
                    platformIdEncoderDecoder.encodePlatformId(promotionPlatformId)
            val promotionUpdate = Update()
                    .set(PROMOTION_PLATFORM_ID_FIELD, promotionPlatformIdEncoded)

            promotionBulkOperations.updateMulti(
                    byVendorIdAndVendorPromotionId(
                            vendorId,
                            vendorPromotionId
                    ), promotionUpdate
            )

        }

        try {
            logger.info(
                    "Update promotions elements with offset {} and limit {}",
                    offset,
                    limit
            )
            val promotionResult =
                    promotionBulkOperations.execute()
            logger.info(
                    "{} promotions updated",
                    promotionResult.modifiedCount
            )
            resultCounter.addUpdated(promotionResult.modifiedCount)
            resultCounter.addCreated(promotionResult.upserts.size)
        } catch (ex: Exception) {
            logger.error(
                    "Failed during update of promotions with offset {} and limit {}",
                    offset,
                    limit,
                    ex
            )
            return
        }

        logger.info(
                "Ended promotion migration with offset {} and limit {} ",
                offset,
                limit
        )
    }

    private fun promotionCollection() =
            database.getCollection(migrationProperties.getPromotionDatabase())

    fun byVendorIdAndVendorPromotionId(vendorId: String, vendorPromotionId: String): Query =
            Query().addCriteria(Criteria.where(VENDOR_ID_FIELD).`is`(vendorId))
                    .addCriteria(
                            Criteria.where(VENDOR_PROMOTION_ID_FIELD)
                                    .`is`(vendorPromotionId)
                    )

    companion object {
        private const val PROMOTION_PLATFORM_ID_FIELD = "promotionPlatformId"
        private const val VENDOR_PROMOTION_ID_FIELD = "vendorPromotionId"
        private const val VENDOR_ID_FIELD = "vendorId"
    }
}