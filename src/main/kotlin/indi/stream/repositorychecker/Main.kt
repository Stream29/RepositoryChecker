package indi.stream.repositorychecker

import indi.stream.repositorychecker.api.RepositoryChecker
import indi.stream.repositorychecker.api.checkNEUQACMStudents

fun main() {
    Students.registerStudents()
    Curriculum.registerCurriculum()

    RepositoryChecker(
        Config.getRepositoryConfig()
    ).checkNEUQACMStudents()
        .groupBy { it.studentName }
        .asSequence()
        .filter { (_, value) -> value.count { it.message != "没有问题" } >= 3 }
        .forEach {
            println("${it.key}完成情况检查：")
            it.value.forEach { message ->
                println("${message.weekName}：${message.message}")
            }
        }
}