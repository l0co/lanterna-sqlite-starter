package com.lifeinide.lanterna.db.service

import com.lifeinide.lanterna.db.Db
import com.lifeinide.lanterna.db.model.Model
import javax.persistence.Table
import kotlin.reflect.KClass

/**
 * @author Lukasz Frankowski
 */
abstract class Service<ID, T: Model<ID>> {

    abstract val clazz: KClass<T>

    val table: String
        get() = (clazz.annotations.find { it.annotationClass == Table::class } as Table).name

    fun findById(id: ID): T? {
        return query().sql("select * from $table where id=?", id).first(clazz.java)
    }

    fun findAll(): List<T> {
        return query().sql("select * from $table").results(clazz.java)
    }

    fun count(): Int = Db.query().sql("select count(*) from $table").first(Int::class.java)

    /** Used to iterate through all objects without loading a full dataset to memory **/
    fun seq(startFrom: Int = 0, cnt: Int? = null): Sequence<T> {
        return Sequence {
            var current: Int = startFrom
            val count = if (cnt!=null) startFrom + cnt else count()

            return@Sequence object: Iterator<T> {
                override fun hasNext(): Boolean {
                    return current<count-1;
                }

                override fun next(): T {
                    return Db.query().sql("select * from $table limit 1 offset ${current++}").first(clazz.java)
                }

            }
        }
    }

    fun upsert(t: T): T {
        if (t.idVal()==null)
            query().insert(t)
        else
            query().update(t)
        return t
    }

    fun insert(t: T): T {
        query().insert(t)
        return t
    }

    fun update(t: T): T {
        query().update(t)
        return t
    }

    protected fun limit(limit: Int?): String {
        return limit?.let { "limit $limit" } ?: ""
    }

    protected fun query() = Db.query()

}
