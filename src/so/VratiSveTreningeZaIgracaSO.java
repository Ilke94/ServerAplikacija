/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package so;

import db.DBBroker;
import domen.Igrac;
import domen.Trening;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Milos
 */
public class VratiSveTreningeZaIgracaSO extends OpstaSistemskaOperacija {

    private List<Trening> listaTreningaZaIgraca;

    public VratiSveTreningeZaIgracaSO(DBBroker dBBroker) {
        super(dBBroker);
        listaTreningaZaIgraca = new ArrayList<>();
    }

    @Override
    protected void proveriPreduslove(Object object) throws Exception {
    }

    @Override
    protected void izvrsiOperaciju(Object object) throws Exception {
        listaTreningaZaIgraca = dBBroker.vratiTreningeZaIgraca((Igrac) object);
    }

    public List<Trening> getListaTreningaZaIgraca() {
        return listaTreningaZaIgraca;
    }

}
