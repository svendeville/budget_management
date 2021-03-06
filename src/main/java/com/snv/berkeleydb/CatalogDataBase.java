/*
 * @2016 Sylvain Vendeville.
 * This file is part of Budget Managment.
 *
 * Budget Management is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Budget Management is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with MesComptes. If not, see <http://www.gnu.org/licenses/>.
 */
package com.snv.berkeleydb;

import com.sleepycat.bind.EntryBinding;
import com.sleepycat.bind.serial.SerialBinding;
import com.sleepycat.bind.serial.StoredClassCatalog;
import com.sleepycat.bind.tuple.StringBinding;
import com.sleepycat.je.*;
import com.sleepycat.je.Transaction.State;
import com.snv.exceptions.BudgetDataBaseException;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public final class CatalogDataBase {

    private static final boolean   CREATE = Boolean.TRUE;
    private static final String    DB_DIR = System.getProperty("user.dir") + "/datas";
    private static CatalogDataBase instance;

    static {
        File file = new File(DB_DIR);
        if (!file.exists() || !file.isDirectory()) {
            file.mkdir();
        }
    }

    private StoredClassCatalog      catalog;
    private Database                catalogDb;
    private final DatabaseConfig    dbConfig  = new DatabaseConfig();
    private EntryBinding<Long>      entryBinding;
    private Environment             env;
    private final EnvironmentConfig envConfig = new EnvironmentConfig();
    private EntryBinding<String>    keyBinding;
    private Database                sequenceDb;

    private CatalogDataBase() {
        if (CREATE) {
            this.envConfig.setAllowCreate(true);
        }
        this.envConfig.setAllowCreate(true);
        this.envConfig.setTransactional(true);
        this.env = new Environment(new File(DB_DIR), this.envConfig);
        this.dbConfig.setAllowCreate(Boolean.TRUE);
        this.dbConfig.setTransactional(Boolean.TRUE);
        final Transaction txn = this.env.beginTransaction(null, null);
        try {
            this.catalogDb = this.env.openDatabase(txn, "catalog", this.dbConfig);
            this.catalog = new StoredClassCatalog(this.catalogDb);
            this.keyBinding = new StringBinding();
            this.entryBinding = new SerialBinding<>(this.catalog, null);
            this.sequenceDb = this.env.openDatabase(txn, "sequences", this.dbConfig);
        } catch (final DatabaseException e) {
            txn.abort();
            log.error("Erreur lors de l'ouverture de la Base de données : catalog", e);
            throw new BudgetDataBaseException(e);
        } finally {
            if (!State.ABORTED.equals(txn.getState())) {
                txn.commit();
            }
        }
    }

    public static CatalogDataBase getInstance() {
        if (instance == null) {
            instance = new CatalogDataBase();
        }
        return instance;
    }

    public StoredClassCatalog getCatalog() {
        return this.catalog;
    }

    public Database getCatalogDb() {
        return this.catalogDb;
    }

    public DatabaseConfig getDbConfig() {
        return this.dbConfig;
    }

    public Environment getEnv() {
        return this.env;
    }

    public EnvironmentConfig getEnvConfig() {
        return this.envConfig;
    }

    public Long getNextSequence(final String dbName) {
        Long result = 1L;
        final DatabaseEntry key = new DatabaseEntry();
        this.keyBinding.objectToEntry(dbName, key);
        final DatabaseEntry sequence = new DatabaseEntry();
        final OperationStatus status = this.sequenceDb.get(null, key, sequence, LockMode.DEFAULT);
        if (OperationStatus.SUCCESS == status) {
            result = this.entryBinding.entryToObject(sequence) + 1L;
        }
        if (Boolean.FALSE.equals(this.updateSequence(dbName, result))) {
            throw new BudgetDataBaseException("Erreur lors de l'insertion en Base de données : sequenceDb");
        }
        return result;
    }

    public void setCatalogDb(final Database catalogDb) {
        this.catalogDb = catalogDb;
    }

    public void setEnv(final Environment env) {
        this.env = env;
    }

    private boolean updateSequence(final String dbName, final Long newSequence) {
        OperationStatus status;
        final Transaction txn = this.env.beginTransaction(null, null);
        final DatabaseEntry key = new DatabaseEntry();
        this.keyBinding.objectToEntry(dbName, key);
        final DatabaseEntry data = new DatabaseEntry();
        this.entryBinding.objectToEntry(newSequence, data);
        try {
            status = this.sequenceDb.put(txn, key, data);
        } catch (final DatabaseException e) {
            txn.abort();
            throw new BudgetDataBaseException(e);
        } finally {
            if (txn.getState() != State.ABORTED) {
                txn.commit();
            }
        }
        return OperationStatus.SUCCESS == status;
    }
}
