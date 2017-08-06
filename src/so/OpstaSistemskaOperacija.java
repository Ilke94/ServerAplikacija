/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;

/**
 *
 * @author Milos
 */
public abstract class OpstaSistemskaOperacija {

    protected DBBroker dBBroker;

    public OpstaSistemskaOperacija(DBBroker dBBroker) {
        this.dBBroker = dBBroker;
    }

    public void izvrsi(Object object) throws Exception {
        try {
            proveriPreduslove(object);
            izvrsiOperaciju(object);
            commit();
        } catch (Exception e) {
            rollback();
            throw e;
        }

    }

    protected abstract void proveriPreduslove(Object object) throws Exception;

    protected abstract void izvrsiOperaciju(Object object) throws Exception;

    private void commit() {
        dBBroker.commit();
    }

    private void rollback() {
        dBBroker.rollback();
    }
}
