package com.abinbev.b2b.promotion.migration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0007\b\u0016\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0001\u00a2\u0006\u0002\u0010\u0005J\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\u000b\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\rH\u0016J)\u0010\u000f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rH\u0082@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J \u0010\u0013\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u0011\u001a\u00020\rH\u0002R\u0016\u0010\u0006\u001a\n \b*\u0004\u0018\u00010\u00070\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0014"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/PaginatedAsyncMigration;", "Lcom/abinbev/b2b/promotion/migration/PaginatedMigration;", "migrationProperties", "Lcom/abinbev/b2b/promotion/migration/MigrationProperties;", "migrationStrategy", "(Lcom/abinbev/b2b/promotion/migration/MigrationProperties;Lcom/abinbev/b2b/promotion/migration/PaginatedMigration;)V", "logger", "Lorg/slf4j/Logger;", "kotlin.jvm.PlatformType", "execute", "", "migrate", "offset", "", "limit", "migrateAsync", "nodeOffset", "pageSize", "(JJJLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "processAsync", "promotion-migration"})
public class PaginatedAsyncMigration implements com.abinbev.b2b.promotion.migration.PaginatedMigration {
    private final com.abinbev.b2b.promotion.migration.MigrationProperties migrationProperties = null;
    private final com.abinbev.b2b.promotion.migration.PaginatedMigration migrationStrategy = null;
    private final org.slf4j.Logger logger = null;
    
    public PaginatedAsyncMigration(@org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.MigrationProperties migrationProperties, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.PaginatedMigration migrationStrategy) {
        super();
    }
    
    public final void execute() {
    }
    
    private final long processAsync(long nodeOffset, long limit, long pageSize) {
        return 0L;
    }
    
    private final java.lang.Object migrateAsync(long nodeOffset, long limit, long pageSize, kotlin.coroutines.Continuation<? super java.lang.Long> continuation) {
        return null;
    }
    
    @java.lang.Override()
    public void migrate(long offset, long limit) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public java.util.Date now() {
        return null;
    }
}