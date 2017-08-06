/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.Trener;

/**
 *
 * @author Milos
 */
public class ProveriPodatkeSO extends OpstaSistemskaOperacija {

    private Trener trener;

    public ProveriPodatkeSO(DBBroker dBBroker) {
        super(dBBroker);
    }

    @Override
    protected void proveriPreduslove(Object object) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object object) throws Exception {
        trener = dBBroker.proveriPodatke((Trener) object);
    }

    public Trener getTrener() {
        return trener;
    }

}
