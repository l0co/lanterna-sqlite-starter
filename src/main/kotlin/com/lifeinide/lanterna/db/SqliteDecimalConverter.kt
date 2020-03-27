package common.db

import javax.persistence.AttributeConverter

/**
 * @author Lukasz Frankowski
 */
class SqliteDecimalConverter: AttributeConverter<Double, Number> {

    override fun convertToDatabaseColumn(attribute: Double?): Number = attribute ?: 0.toDouble()

    override fun convertToEntityAttribute(dbData: Number?): Double = dbData?.toDouble() ?: 0.toDouble()

}
