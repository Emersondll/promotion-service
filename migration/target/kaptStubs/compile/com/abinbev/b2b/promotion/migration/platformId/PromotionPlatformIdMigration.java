package com.abinbev.b2b.promotion.migration.platformId;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0017\u0018\u0000 \u00192\u00020\u0001:\u0001\u0019B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0014H\u0016J,\u0010\u0016\u001a&\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00180\u0018 \u000b*\u0012\u0012\f\u0012\n \u000b*\u0004\u0018\u00010\u00180\u0018\u0018\u00010\u00170\u0017H\u0012R\u000e\u0010\u0004\u001a\u00020\u0005X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u000b*\u0004\u0018\u00010\n0\nX\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0092\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001a"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/platformId/PromotionPlatformIdMigration;", "Lcom/abinbev/b2b/promotion/migration/PaginatedMigration;", "migrationProperties", "Lcom/abinbev/b2b/promotion/migration/MigrationProperties;", "database", "Lorg/springframework/data/mongodb/core/MongoOperations;", "resultCounter", "Lcom/abinbev/b2b/promotion/migration/ResultCounter;", "(Lcom/abinbev/b2b/promotion/migration/MigrationProperties;Lorg/springframework/data/mongodb/core/MongoOperations;Lcom/abinbev/b2b/promotion/migration/ResultCounter;)V", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "byVendorIdAndVendorPromotionId", "Lorg/springframework/data/mongodb/core/query/Query;", "vendorId", "", "vendorPromotionId", "migrate", "", "offset", "", "limit", "promotionCollection", "Lcom/mongodb/client/MongoCollection;", "Lorg/bson/Document;", "Companion", "promotion-migration"})
@org.springframework.stereotype.Component()
public class PromotionPlatformIdMigration implements com.abinbev.b2b.promotion.migration.PaginatedMigration {
    private final com.abinbev.b2b.promotion.migration.MigrationProperties migrationProperties = null;
    private final org.springframework.data.mongodb.core.MongoOperations database = null;
    private final com.abinbev.b2b.promotion.migration.ResultCounter resultCounter = null;
    private final org.slf4j.Logger logger = null;
    @org.jetbrains.annotations.NotNull()
    public static final com.abinbev.b2b.promotion.migration.platformId.PromotionPlatformIdMigration.Companion Companion = null;
    private static final java.lang.String PROMOTION_PLATFORM_ID_FIELD = "promotionPlatformId";
    private static final java.lang.String VENDOR_PROMOTION_ID_FIELD = "vendorPromotionId";
    private static final java.lang.String VENDOR_ID_FIELD = "vendorId";
    
    public PromotionPlatformIdMigration(@org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.MigrationProperties migrationProperties, @org.jetbrains.annotations.NotNull()
    org.springframework.data.mongodb.core.MongoOperations database, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.ResultCounter resultCounter) {
        super();
    }
    
    @java.lang.Override()
    public void migrate(long offset, long limit) {
    }
    
    private com.mongodb.client.MongoCollection<org.bson.Document> promotionCollection() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public org.springframework.data.mongodb.core.query.Query byVendorIdAndVendorPromotionId(@org.jetbrains.annotations.NotNull()
    java.lang.String vendorId, @org.jetbrains.annotations.NotNull()
    java.lang.String vendorPromotionId) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.Date now() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/platformId/PromotionPlatformIdMigration$Companion;", "", "()V", "PROMOTION_PLATFORM_ID_FIELD", "", "VENDOR_ID_FIELD", "VENDOR_PROMOTION_ID_FIELD", "promotion-migration"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}