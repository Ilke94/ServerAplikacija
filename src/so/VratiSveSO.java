/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milos
 */
public class VratiSveSO extends OpstaSistemskaOperacija {

    private List<OpstiDomenskiObjekat> listaOpstihDomenskihObjekata;

    public VratiSveSO(DBBroker dBBroker) {
        super(dBBroker);
        listaOpstihDomenskihObjekata = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslove(Object object) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object object) throws Exception {
        listaOpstihDomenskihObjekata = dBBroker.vratiSve((OpstiDomenskiObjekat) object);
    }

    public List<OpstiDomenskiObjekat> getListaOpstihDomenskihObjekata() {
        return listaOpstihDomenskihObjekata;
    }

}
