package com.abinbev.b2b.promotion.migration

import com.abinbev.b2b.promotion.migration.config.mock
import com.abinbev.b2b.promotion.migration.config.whenever
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.anyLong
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.internal.stubbing.answers.AnswersWithDelay
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
internal class PaginatedAsyncPlatformIdMigrationTest {
    private val migrationProperties: MigrationProperties = mock()
    private val migrationStrategy: PaginatedMigration = mock()
    private val migration = PaginatedAsyncMigration(migrationProperties, migrationStrategy)

    @Test
    fun `No element found`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(0)

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(0)).getNumberOfThreads()
        verify(migrationProperties, times(0)).getNodePool()
        verify(migrationProperties, times(0)).getPageSize()
        verify(migrationStrategy, times(0)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Empty number of threads`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(1)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(null)

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(0)).getNodePool()
        verify(migrationProperties, times(0)).getPageSize()
        verify(migrationStrategy, times(0)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process pagination with 1000 elements and pool size of 4 and in node 1`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(100)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(2)
        whenever(migrationProperties.getPageSize()).thenReturn(5)
        whenever(migrationProperties.getNode()).thenReturn(1)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(5)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process pagination with 1000 elements and pool size of 4 and in node 2`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(100)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(2)
        whenever(migrationProperties.getPageSize()).thenReturn(5)
        whenever(migrationProperties.getNode()).thenReturn(2)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(5)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process pagination with 10000 elements and pool size of 4 and in node 3`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(10000)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(8)
        whenever(migrationProperties.getPageSize()).thenReturn(100)
        whenever(migrationProperties.getNode()).thenReturn(3)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(25)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process 100 millions of elements with pool size 4 and in node 1 with page size of 1000`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(100000000)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(100)
        whenever(migrationProperties.getPageSize()).thenReturn(1000)
        whenever(migrationProperties.getNode()).thenReturn(1)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(25000)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process 100 millions of elements with pool size 4 and in node 2 with page size of 1000`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(100000000)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(100)
        whenever(migrationProperties.getPageSize()).thenReturn(1000)
        whenever(migrationProperties.getNode()).thenReturn(2)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(25000)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process element with pages lower then threads and page size higher then elements by node`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(1000)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(100)
        whenever(migrationProperties.getPageSize()).thenReturn(500)
        whenever(migrationProperties.getNode()).thenReturn(1)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(1)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Process element with migrate method delaying`() {
        whenever(migrationStrategy.migrate(anyLong(), anyLong())).thenAnswer(
            AnswersWithDelay(
                3000
            ) { null }
        )
        whenever(migrationProperties.getTotalOfElements()).thenReturn(1000)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(10)
        whenever(migrationProperties.getPageSize()).thenReturn(10)
        whenever(migrationProperties.getNode()).thenReturn(1)
        whenever(migrationProperties.getNodePool()).thenReturn(4)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(25)).migrate(anyLong(), anyLong())
    }

    @Test
    fun `Custom test`() {
        whenever(migrationProperties.getTotalOfElements()).thenReturn(181)
        whenever(migrationProperties.getNumberOfThreads()).thenReturn(2)
        whenever(migrationProperties.getPageSize()).thenReturn(5)
        whenever(migrationProperties.getNode()).thenReturn(1)
        whenever(migrationProperties.getNodePool()).thenReturn(1)
        whenever(migrationProperties.getCountry()).thenReturn("MX")

        migration.execute()

        verify(migrationProperties).getTotalOfElements()
        verify(migrationProperties, times(1)).getNumberOfThreads()
        verify(migrationProperties, times(1)).getNode()
        verify(migrationProperties, times(1)).getNodePool()
        verify(migrationProperties, times(2)).getPageSize()
        verify(migrationStrategy, times(37)).migrate(anyLong(), anyLong())


    }
}