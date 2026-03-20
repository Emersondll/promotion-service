package com.abinbev.b2b.promotion.migration.config

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.MongoDBContainer

class DBInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
    private val container: MongoDBContainer = MongoDBContainer("mongo:4.2.18")

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        container.start()
        val values: TestPropertyValues =
            TestPropertyValues.of("spring.data.mongodb.uri=" + container.replicaSetUrl)
        values.applyTo(applicationContext)
    }
}