package com.abinbev.b2b.promotion.migration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\b\n\u0002\b\u0003\b\u0087\b\u0018\u00002\u00020\u0001:\u0001%BM\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u000e\u001a\u00020\u0003H\u00c2\u0003J\t\u0010\u000f\u001a\u00020\u0005H\u00c2\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c2\u0003J\t\u0010\u0011\u001a\u00020\u0003H\u00c2\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c2\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u00c2\u0003\u00a2\u0006\u0002\u0010\u0014J\t\u0010\u0015\u001a\u00020\u000bH\u00c2\u0003JV\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\n\u001a\u00020\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0017J\u0013\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\u0006\u0010\u001b\u001a\u00020\u0005J\u0006\u0010\u001c\u001a\u00020\u0003J\u0006\u0010\u001d\u001a\u00020\u0003J\r\u0010\u001e\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0014J\u0006\u0010\u001f\u001a\u00020\u0003J\u0006\u0010 \u001a\u00020\u0005J\u0006\u0010!\u001a\u00020\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0012\u0010\t\u001a\u0004\u0018\u00010\u0003X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006&"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/MigrationProperties;", "", "totalOfElements", "", "country", "", "node", "nodePool", "pageSize", "threadsAmount", "database", "Lcom/abinbev/b2b/promotion/migration/MigrationProperties$Database;", "(JLjava/lang/String;JJJLjava/lang/Long;Lcom/abinbev/b2b/promotion/migration/MigrationProperties$Database;)V", "Ljava/lang/Long;", "component1", "component2", "component3", "component4", "component5", "component6", "()Ljava/lang/Long;", "component7", "copy", "(JLjava/lang/String;JJJLjava/lang/Long;Lcom/abinbev/b2b/promotion/migration/MigrationProperties$Database;)Lcom/abinbev/b2b/promotion/migration/MigrationProperties;", "equals", "", "other", "getCountry", "getNode", "getNodePool", "getNumberOfThreads", "getPageSize", "getPromotionDatabase", "getTotalOfElements", "hashCode", "", "toString", "Database", "promotion-migration"})
@org.springframework.boot.context.properties.ConfigurationProperties(value = "migration")
@org.springframework.boot.context.properties.ConstructorBinding()
public final class MigrationProperties {
    private final long totalOfElements = 0L;
    private final java.lang.String country = null;
    private final long node = 0L;
    private final long nodePool = 0L;
    private final long pageSize = 0L;
    private final java.lang.Long threadsAmount = null;
    private final com.abinbev.b2b.promotion.migration.MigrationProperties.Database database = null;
    
    @org.jetbrains.annotations.NotNull()
    public final com.abinbev.b2b.promotion.migration.MigrationProperties copy(long totalOfElements, @org.jetbrains.annotations.NotNull()
    java.lang.String country, long node, long nodePool, long pageSize, @org.jetbrains.annotations.Nullable()
    java.lang.Long threadsAmount, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.MigrationProperties.Database database) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    public MigrationProperties() {
        super();
    }
    
    public MigrationProperties(long totalOfElements, @org.jetbrains.annotations.NotNull()
    java.lang.String country, long node, long nodePool, long pageSize, @org.jetbrains.annotations.Nullable()
    java.lang.Long threadsAmount, @org.jetbrains.annotations.NotNull()
    com.abinbev.b2b.promotion.migration.MigrationProperties.Database database) {
        super();
    }
    
    private final long component1() {
        return 0L;
    }
    
    private final java.lang.String component2() {
        return null;
    }
    
    private final long component3() {
        return 0L;
    }
    
    private final long component4() {
        return 0L;
    }
    
    private final long component5() {
        return 0L;
    }
    
    private final java.lang.Long component6() {
        return null;
    }
    
    private final com.abinbev.b2b.promotion.migration.MigrationProperties.Database component7() {
        return null;
    }
    
    public final long getTotalOfElements() {
        return 0L;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getCountry() {
        return null;
    }
    
    public final long getNode() {
        return 0L;
    }
    
    public final long getNodePool() {
        return 0L;
    }
    
    public final long getPageSize() {
        return 0L;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Long getNumberOfThreads() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getPromotionDatabase() {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u000f"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/MigrationProperties$Database;", "", "promotion", "", "(Ljava/lang/String;)V", "getPromotion", "()Ljava/lang/String;", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "promotion-migration"})
    public static final class Database {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String promotion = null;
        
        @org.jetbrains.annotations.NotNull()
        public final com.abinbev.b2b.promotion.migration.MigrationProperties.Database copy(@org.jetbrains.annotations.NotNull()
        java.lang.String promotion) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public Database() {
            super();
        }
        
        public Database(@org.jetbrains.annotations.NotNull()
        java.lang.String promotion) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getPromotion() {
            return null;
        }
    }
}