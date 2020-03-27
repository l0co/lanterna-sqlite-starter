package com.lifeinide.lanterna.db.model

import com.lifeinide.lanterna.db.SqliteBooleanConverter
import com.lifeinide.lanterna.db.SqliteDecimalConverter
import javax.persistence.*

/**
 * The example entity.
 *
 * @author Lukasz Frankowski
 */
@Table(name = "my_entity")
data class MyEntity(
    var str: String,
    @get:Column(name = "int") var int: Int,
    @get:Column(name = "dbl") @get:Convert(converter = SqliteDecimalConverter::class) var dbl: Double,
    @get:Column(name = "bool") @get:Convert(converter = SqliteBooleanConverter::class) var bool: Boolean
): Model<Int>() {

    @get:Id @get:GeneratedValue var id: Int? = null

    override fun idVal(): Int?  = id

}
