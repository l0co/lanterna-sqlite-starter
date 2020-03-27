package com.lifeinide.lanterna.db.service

import com.lifeinide.lanterna.db.model.MyEntity
import kotlin.reflect.KClass

/**
 * The example entity service.
 *
 * @author Lukasz Frankowski
 */
object MyEntityService: Service<Int, MyEntity>() {

    override val clazz: KClass<MyEntity>
        get() = MyEntity::class

}
