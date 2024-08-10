package indi.stream.repositorychecker

import indi.stream.repositorychecker.api.*
import org.yaml.snakeyaml.Yaml

object Config {
    private val map =
        Yaml().load<Map<String, String>>(
            "E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\config.yml".file.readText())

    fun getRepositoryConfig(): RepositoryConfig {
        return RepositoryConfig(map["url"]!!, map["path"]!!)
    }
}

object Curriculum {
    private val map =
        Yaml().load<Map<String, Map<String, Map<String, String>>>>(
            "E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\curriculum.yml".file.readText())

    fun registerCurriculum() {
        map.forEach { (curriculum, weeks) ->
            registerCurriculumWithRequirements(curriculum,
                                               weeks.mapValues { (_, requirementMap) ->
                                                   requirementMap.map { it.toFolderChecker() }
                                               })
        }
    }
}

private fun Map.Entry<String, String>.toFolderChecker(): FolderChecker =
    when (key) {
        "contains" -> findAnyContains(value)
        else -> throw IllegalArgumentException("未知的任务要求$key")
    }

object Students {
    private val map =
        Yaml().load<Map<String, List<String>>>(
            "E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\students.yml".file.readText())

    fun registerStudents() {
        map.forEach { (curriculum, students) ->
            registerCurriculumWithStudent(curriculum, students)
        }
    }
}