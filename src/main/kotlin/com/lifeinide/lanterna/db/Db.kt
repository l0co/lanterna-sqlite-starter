package com.lifeinide.lanterna.db

import com.dieselpoint.norm.Database
import com.dieselpoint.norm.Query
import com.dieselpoint.norm.Transaction
import com.lifeinide.lanterna.db.model.MyEntity
import com.lifeinide.lanterna.db.service.MyEntityService

/**
 * @author Lukasz Frankowski
 */
object Db: Database() {

    val currentTransaction: ThreadLocal<Transaction> = ThreadLocal()
    var empty: Boolean = false
        get() = field

    init {
        setJdbcUrl("jdbc:sqlite:db.sqlite")
        if (!updateDb()) {
            createDb()
            empty = true
        }
    }

    fun createDb() {
        try {
            arrayOf(
                """CREATE TABLE my_entity (id INTEGER PRIMARY KEY AUTOINCREMENT, str TEXT NOT NULL, 
                    int INTEGER NOT NULL, dbl DECIMAL(2) NOT NULL, bool BOOLEAN NOT NULL)""".trimIndent()
            ).forEach {
                sql(it).execute()
            }
        } catch (e: Exception) {
            // indexes exist
        }
    }

    fun populateTestData() {
        MyEntityService.insert(MyEntity("one", 1, 1.1, true))
        MyEntityService.insert(MyEntity("two", 2, 2.1, true))
        MyEntityService.insert(MyEntity("three", 3, 3.1, false))
    }

    /**
     * Executed the database update if the db version doesn't match.
     *
     * @return False if database is emtpy and needs to be created
     */
    fun updateDb(): Boolean {
        val version =
            try { sql("select version from version").first(Int::class.java) }
            catch (e: java.lang.Exception) { null }

        when (version) {

            1 -> {
                // TODO do some update job and increment version as follows:
                // sql("update version set version=2").execute()
            }

            null -> {
                sql("create table version (version INTEGER NOT NULL)").execute()
                sql("insert into version (version) values (1)").execute()
                updateDb()
                return false
            }

        }

        return true
    }

    fun query(): Query {
        val q = Query(Db)
        if (currentTransaction.get()!=null)
            return q.transaction(currentTransaction.get())
        return q
    }

    fun doTransactionally(f: () -> Unit) {
        // use current transaction
        if (currentTransaction.get()!=null) {
            f()
            return
        }

        // use new transaction
        var rolledBack = false
        currentTransaction.set(Db.startTransaction())
        try {
            f()
        } catch (e: Exception) {
            rolledBack = true
            currentTransaction.get().rollback()
            throw e
        } finally {
            if (!rolledBack)
                currentTransaction.get().commit()
            currentTransaction.remove()
        }
    }


}
