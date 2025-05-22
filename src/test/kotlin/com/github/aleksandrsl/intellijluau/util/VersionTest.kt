package com.github.aleksandrsl.intellijluau.util

import org.junit.Assert.*
import org.junit.Test

class VersionTest {
    @Test
    fun testVersionComparison() {
        assertTrue(Version.parse("1.0.0") < Version.parse("2.0.0"))
        assertTrue(Version.parse("1.0.0") < Version.parse("1.1.0"))
        assertTrue(Version.parse("1.0.0") < Version.parse("1.0.1"))
        assertTrue(Version.parse("1.0.0") == Version.parse("1.0.0"))
        assertTrue(Version.parse("2.0.0") > Version.parse("1.0.0"))
        assertTrue(Version.parse("1.1.0") > Version.parse("1.0.0"))
        assertTrue(Version.parse("1.0.1") > Version.parse("1.0.0"))
        assertTrue(Version.Latest > Version.parse("1.0.0"))
    }

    @Test
    fun testVersionSorting() {
        val versions = listOf(
            Version.Latest,
            Version.parse("2.0.0"),
            Version.parse("1.0.0"),
            Version.parse("1.1.0"),
            Version.parse("1.0.1")
        )
        val sortedVersions = versions.sorted()
        assertEquals(
            listOf(
                Version.parse("1.0.0"),
                Version.parse("1.0.1"),
                Version.parse("1.1.0"),
                Version.parse("2.0.0"),
                Version.Latest,
            ),
            sortedVersions
        )
    }


}
