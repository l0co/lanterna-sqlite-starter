package com.lifeinide.lanterna.db.service

import com.lifeinide.lanterna.db.model.MyEntity
import kotlin.reflect.KClass

/**
 * @author Lukasz Frankowski
 */
object MyEntityService: Service<Int, MyEntity>() {

    override val clazz: KClass<MyEntity>
        get() = MyEntity::class

    private var threshold: Double? = null

}
