package com.abinbev.b2b.promotion.migration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H&J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/PaginatedMigration;", "", "migrate", "", "offset", "", "limit", "now", "Ljava/util/Date;", "promotion-migration"})
public abstract interface PaginatedMigration {
    
    public abstract void migrate(long offset, long limit);
    
    @org.jetbrains.annotations.NotNull()
    public abstract java.util.Date now();
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 3)
    public final class DefaultImpls {
        
        @org.jetbrains.annotations.NotNull()
        public static java.util.Date now(@org.jetbrains.annotations.NotNull()
        com.abinbev.b2b.promotion.migration.PaginatedMigration $this) {
            return null;
        }
    }
}