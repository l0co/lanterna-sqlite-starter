package com.lifeinide.lanterna.db

import javax.persistence.AttributeConverter

/**
 * Required for **norm** to map `Double` fields.
 *
 * @author Lukasz Frankowski
 */
class SqliteDecimalConverter: AttributeConverter<Double, Number> {

    override fun convertToDatabaseColumn(attribute: Double?): Number = attribute ?: 0.toDouble()

    override fun convertToEntityAttribute(dbData: Number?): Double = dbData?.toDouble() ?: 0.toDouble()

}
