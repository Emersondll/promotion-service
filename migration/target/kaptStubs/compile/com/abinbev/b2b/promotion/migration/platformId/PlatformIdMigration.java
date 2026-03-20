package com.abinbev.b2b.promotion.migration.platformId;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0017\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0092\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/platformId/PlatformIdMigration;", "", "promotionPlatformIdMigration", "Lcom/abinbev/b2b/promotion/migration/platformId/PromotionPlatformIdMigration;", "migrationProperties", "Lcom/abinbev/b2b/promotion/migration/MigrationProperties;", "resultCounter", "Lcom/abinbev/b2b/promotion/migration/ResultCounter;", "(Lcom/abinbev/b2b/promotion/migration/platformId/PromotionPlatformIdMigration;Lcom/abinbev/b2b/promotion/migration/MigrationProperties;Lcom/abinbev/b2b/promotion/migration/ResultCounter;)V", "log", "Lorg/slf4j/Logger;", "promotion-migration"})
@org.springframework.boot.autoconfigure.condition.ConditionalOnProperty(prefix = "migration.platform-id", name = {"enabled"}, havingValue = "true")
@org.springframework.stereotype.Component()
public class PlatformIdMigration {
    private final org.slf4j.Logger log = null;
    
    public PlatformIdMigration(@org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.platformId.PromotionPlatformIdMigration promotionPlatformIdMigration, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.MigrationProperties migrationProperties, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.ResultCounter resultCounter) {
        super();
    }
}