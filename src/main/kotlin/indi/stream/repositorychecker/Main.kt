package indi.stream.repositorychecker

import java.io.File

fun main() {
    File("E:\\ACodeSpace\\IDEA\\RepositoryChecker").repository.run {
        println("Repository: $directory")
        println("Branch: $branch")
        println("ObjectDatabase: $objectDatabase")
        println("WorkTree: $workTree")
    }
}