package indi.stream.repositorychecker.api

import java.io.File

fun RepositoryChecker.checkSubfolder(checker: FolderChecker) =
    check {
        it.listFiles()
            .orEmpty()
            .asSequence()
            .map(checker)
            .filterNotNull()
            .flatMap(List<String>::asSequence)
            .toList()
            .let(List<String>::orNull)
    }

private fun <T> List<T>.orNull(): List<T>? = ifEmpty { null }

// The receiver should be a directory, otherwise NullPointerException will be thrown
// Without this file itself
val File.subFilesWithoutFolder: Sequence<File>
    get() = this.listFiles()!!.asSequence().flatMap { if (it.isDirectory) it.subFilesWithoutFolder else sequenceOf(it) }

// The receiver should be a directory, otherwise NullPointerException will be thrown
// Including this file itself
val File.subFilesWithFolder: Sequence<File>
    get() = sequenceOf(this) +
            this.listFiles().orEmpty().asSequence().flatMap { it.subFilesWithFolder }