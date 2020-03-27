package common.db

import javax.persistence.AttributeConverter

/**
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
