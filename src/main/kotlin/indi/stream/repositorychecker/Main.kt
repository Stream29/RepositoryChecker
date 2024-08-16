package indi.stream.repositorychecker

import indi.stream.repositorychecker.api.RepositoryChecker
import indi.stream.repositorychecker.api.checkNEUQACMStudents

fun main() {
    Students.registerStudents()
    Curriculum.registerCurriculum()

    RepositoryChecker(
        Config.getRepositoryConfig()
    ).checkNEUQACMStudents()
        .groupBy { it.weekName }
        .asSequence()
        .forEach {
            println("${it.key}完成情况检查：")
            it.value.forEach { message ->
                println("${message.studentName}：${message.message}")
            }
        }
}