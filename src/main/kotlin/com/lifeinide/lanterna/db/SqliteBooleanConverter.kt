package com.lifeinide.lanterna.db

import javax.persistence.AttributeConverter

/**
 * Required for **norm** to map `Boolean` fields.
 *
 * @author Lukasz Frankowski
 */
class SqliteBooleanConverter: AttributeConverter<Boolean, Int> {

    override fun convertToDatabaseColumn(attribute: Boolean?): Int {
        return if(attribute!=null && attribute) 1; else 0
    }

    override fun convertToEntityAttribute(dbData: Int?): Boolean {
        return dbData==1
    }

}
