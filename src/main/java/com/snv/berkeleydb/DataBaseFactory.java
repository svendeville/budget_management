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
import com.sleepycat.bind.tuple.LongBinding;
import com.sleepycat.je.*;
import com.sleepycat.je.Transaction.State;
import com.snv.exceptions.BudgetDataBaseException;
import com.snv.exceptions.KeyAlreadyExistException;
import com.snv.exceptions.NoDataFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public abstract class DataBaseFactory<T> {

    private final StoredClassCatalog catalog  = CatalogDataBase.getInstance().getCatalog();
    private Database                 db;
    private final DatabaseConfig     dbConfig = CatalogDataBase.getInstance().getDbConfig();
    private EntryBinding<T>          entryBinding;
    private final Environment        env      = CatalogDataBase.getInstance().getEnv();
    private EntryBinding<Long>       keyBinding;

    private boolean saveEntity(final Long primaryKey, final T entity, final boolean isInsert) {
        OperationStatus status;
        final Transaction txn = this.env.beginTransaction(null, null);
        final DatabaseEntry key = new DatabaseEntry();
        this.keyBinding.objectToEntry(primaryKey, key);
        final DatabaseEntry data = new DatabaseEntry();
        this.entryBinding.objectToEntry(entity, data);
        try {
            status = Boolean.FALSE.equals(isInsert) ? this.db.put(txn, key, data) : this.db.putNoOverwrite(txn, key, data);
        } catch (final DatabaseException e) {
            txn.abort();
            final String msg = String.format("Erreur lors de l'insertion en Base de données : %s", this.db.getDatabaseName());
            log.error(msg, e);
            throw new BudgetDataBaseException(msg, e);
        } finally {
            if (txn.getState() != State.ABORTED) {
                txn.commit();
            }
        }
        if (OperationStatus.KEYEXIST == status) {
            final String msg = String.format("La cle %s est déjà utilisée.", primaryKey.toString());
            log.error(msg);
            throw new KeyAlreadyExistException(msg);
        }
        return OperationStatus.SUCCESS == status;
    }

    protected void close() {
        try {
            this.db.close();
            this.db = null;
        } catch (final DatabaseException e) {
            final String msg = String.format("Erreur lors de la fermeture de la Base de données : %s", this.db.getDatabaseName());
            log.error(msg, e);
            throw new BudgetDataBaseException(msg, e);
        }
    }

    protected boolean deleteEntity(final Long primaryKey) {
        final DatabaseEntry key = new DatabaseEntry();
        this.keyBinding.objectToEntry(primaryKey, key);
        final OperationStatus status = this.db.delete(null, key);
        return OperationStatus.SUCCESS == status;
    }

    protected T get(final Long primaryKey) {
        final DatabaseEntry key = new DatabaseEntry();
        this.keyBinding.objectToEntry(primaryKey, key);
        final DatabaseEntry data = new DatabaseEntry();
        final OperationStatus status = this.db.get(null, key, data, LockMode.DEFAULT);
        if (OperationStatus.NOTFOUND == status) {
            final String msg = String.format("Aucune données pour la clé : %s trouvé dans la base : %s", primaryKey,
                    this.db.getDatabaseName());
            log.info(msg);
            throw new NoDataFoundException(msg);
        }
        return this.entryBinding.entryToObject(data);
    }

    protected List<T> getAll() {
        final DatabaseEntry priKey = new DatabaseEntry();
        final DatabaseEntry priData = new DatabaseEntry();
        final List<T> result = new ArrayList<>();
        try (Cursor cursor = this.db.openCursor(null, null)) {
            while (cursor.getNext(priKey, priData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
                final T data = this.entryBinding.entryToObject(priData);
                result.add(data);
            }
        }
        return result;
    }

    protected boolean insertEntity(final Long primaryKey, final T entity) {
        return this.saveEntity(primaryKey, entity, true);
    }

    protected boolean isOpen() {
        return this.db != null;
    }

    protected void open(final DataBaseNamesEnum dbName) {
        final Transaction txn = this.env.beginTransaction(null, null);
        try {
            this.entryBinding = new SerialBinding<>(this.catalog, null);
            this.keyBinding = new LongBinding();
            this.db = this.env.openDatabase(txn, dbName.name(), this.dbConfig);
        } catch (final RuntimeException e) {
            txn.abort();
            final String msg = String.format("Erreur lors de l'ouverture de la Base de données : %s", dbName.name());
            log.error(msg, e);
            throw new BudgetDataBaseException(msg, e);
        } finally {
            if (txn.getState() != State.ABORTED) {
                txn.commit();
            }
        }
    }

    protected boolean updateEntity(final Long primaryKey, final T entity) {
        return this.saveEntity(primaryKey, entity, false);
    }
}
