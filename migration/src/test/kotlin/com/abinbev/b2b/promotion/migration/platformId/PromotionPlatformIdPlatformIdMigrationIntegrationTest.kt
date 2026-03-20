package com.abinbev.b2b.promotion.migration.platformId

import com.abinbev.b2b.promotion.migration.config.DBInitializer
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension

@SpringBootTest(
    properties = [
        "MIGRATION_ENTITY = PROMOTION",
        "MIGRATION_COUNTRY = MX",
        "MIGRATION_NODE = 1"
    ]
)
@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@ContextConfiguration(initializers = [DBInitializer::class])
internal class PromotionPlatformIdPlatformIdMigrationIntegrationTest {
    @Autowired
    lateinit var applicationContext: ConfigurableApplicationContext

    @BeforeEach
    fun setup() {
        TestPropertyValues.of("migration.platform-id.entity=" + "PROMOTION")
            .applyTo(applicationContext)
    }

    @Test
    fun `Should migrate 1 million of registers`() {
        println("Will be do later, just for example")
    }
}