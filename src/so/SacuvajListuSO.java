/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import java.util.List;

/**
 *
 * @author Milos
 */
public class SacuvajListuSO extends OpstaSistemskaOperacija {

    public SacuvajListuSO(DBBroker dBBroker) {
        super(dBBroker);
    }

    @Override
    protected void proveriPreduslove(Object object) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object object) throws Exception {
        List<OpstiDomenskiObjekat> listaOpstihDomenskihObjekata = (List<OpstiDomenskiObjekat>) object;
        for (OpstiDomenskiObjekat opstiDomenskiObjekat : listaOpstihDomenskihObjekata) {
            dBBroker.sacuvaj(opstiDomenskiObjekat);
        }
    }

}
