package com.github.aleksandrsl.intellijluau.util

sealed class Version : Comparable<Version> {
    data class Semantic(
        val major: Int, val minor: Int, val patch: Int
    ) : Version() {
        override fun toString(): String = "$major.$minor.$patch"
        override fun compareTo(other: Version): Int {
            return when (other) {
                is Semantic -> compareValuesBy(this, other, { it.major }, { it.minor }, { it.patch })
                Latest -> -1
            }
        }

        companion object {
            fun parse(version: String): Semantic {
                val parts = version.split('.')
                if (parts.size != 3) throw MalformedSemanticVersionException(version)
                return try {
                    Semantic(
                        parts[0].toInt(), parts[1].toInt(), parts[2].toInt()
                    )
                } catch (e: NumberFormatException) {
                    throw MalformedSemanticVersionException(version)
                }
            }
        }
    }

    data object Latest : Version() {
        override fun toString(): String = "Latest"
        override fun compareTo(other: Version): Int {
            return when (other) {
                is Latest -> -1
                else -> 1
            }
        }
    }

    companion object {
        fun parse(version: String): Version = if (version == Latest.toString()) Latest else Semantic.parse(version)
    }
}

class MalformedSemanticVersionException(version: String) : Exception("Malformed semantic version: $version")
