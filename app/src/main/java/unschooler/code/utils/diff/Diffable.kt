package unschooler.code.utils.diff

import java.io.Serializable

interface Diffable : Serializable {

    fun getKey(): String

    fun getContent(): String?
}


class EmptyDiffable : Diffable {

    override fun getKey(): String = "-1"

    override fun getContent(): String? = ""
}