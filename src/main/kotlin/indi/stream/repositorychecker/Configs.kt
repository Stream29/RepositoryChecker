package indi.stream.repositorychecker

import org.yaml.snakeyaml.Yaml

object Config {
    private val map =
        Yaml().load<Map<String, String>>("E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\config.yml")
    val url: String
        get() = map["url"]!!
    val path: String
        get() = map["path"]!!
}

object Curriculum {
    private val map =
        Yaml().load<Map<String, Map<String, Map<String, String>>>>("E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\curriculum.yml")

    fun getRequirements(curriculum: String, week: String): Map<String, String> {
        return map[curriculum]!![week]!!
    }
}

object Students {
    private val map =
        Yaml().load<Map<String, List<String>>>("E:\\ACodeSpace\\IDEA\\RepositoryChecker\\src\\main\\resources\\configs\\students.yml")

    fun getStudent(curriculum: String): List<String> {
        return map[curriculum]!!
    }
}