package com.statepresenter.buildsrc

import java.util.regex.Pattern

val buildSrcPath = "src/main/kotlin/com/statepresenter/buildsrc"

val pattern: Pattern = Pattern.compile("\\s*const\\s*val\\s*(\\S*)\\s*=\\s*\"(.*)\"")

file("$buildSrcPath/Versions.kt")
    .readLines()
    .forEach { line ->
        val matcher = pattern.matcher(line)
        if (matcher.matches()) {
            project.extra.set(matcher.group(1), matcher.group(2))
        }
    }
