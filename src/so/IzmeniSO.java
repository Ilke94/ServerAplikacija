/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;

/**
 *
 * @author Milos
 */
public class IzmeniSO extends OpstaSistemskaOperacija {

    public IzmeniSO(DBBroker dBBroker) {
        super(dBBroker);
    }

    @Override
    protected void proveriPreduslove(Object object) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object object) throws Exception {
        dBBroker.izmeni((OpstiDomenskiObjekat) object);
    }

}
