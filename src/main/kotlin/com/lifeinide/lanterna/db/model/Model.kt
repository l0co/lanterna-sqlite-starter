package com.lifeinide.lanterna.db.model

/**
 * @author Lukasz Frankowski
 */
abstract class Model<T> {

    abstract fun idVal(): T?

    override fun equals(other: Any?): Boolean {
        if (idVal()==null)
            return super.equals(other)

        if (this === other) return true
        if (other !is Model<*>) return false

        if (idVal() != other.idVal()) return false

        return true
    }

    override fun hashCode(): Int {
        if (idVal() is Int)
            return idVal() as Int;
        else if (idVal() != null)
            return idVal().hashCode()

        return super.hashCode()
    }


}
