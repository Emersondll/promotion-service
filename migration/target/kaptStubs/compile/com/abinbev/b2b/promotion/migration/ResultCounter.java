package com.abinbev.b2b.promotion.migration;

import java.lang.System;

@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\b\u0017\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0016J\u0010\u0010\t\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0004H\u0016J\b\u0010\n\u001a\u00020\u0004H\u0016J\b\u0010\u000b\u001a\u00020\u0004H\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0092\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0092\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/abinbev/b2b/promotion/migration/ResultCounter;", "", "()V", "createdCount", "", "updatedCount", "addCreated", "", "value", "addUpdated", "getCreatedCount", "getUpdatedCount", "promotion-migration"})
@org.springframework.stereotype.Component()
public class ResultCounter {
    private int updatedCount = 0;
    private int createdCount = 0;
    
    public ResultCounter() {
        super();
    }
    
    public int getUpdatedCount() {
        return 0;
    }
    
    public int getCreatedCount() {
        return 0;
    }
    
    public void addUpdated(int value) {
    }
    
    public void addCreated(int value) {
    }
}