/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logika;

import db.DBBroker;
import domen.DatabaseParameters;
import domen.Igrac;
import domen.Trener;
import domen.Trening;
import java.io.IOException;
import java.util.List;
import db.DBUtil;
import domen.OpstiDomenskiObjekat;
import java.util.ArrayList;
import so.IzmeniSO;
import so.ProveriPodatkeSO;
import so.SacuvajListuSO;
import so.SacuvajSO;
import so.VratiSveSO;
import so.VratiSveTreningeZaIgracaSO;

/**
 *
 * @author Milos
 */
public class Kontroler {

    private static List<Trener> listaUlogovanih = new ArrayList<>();

    public static List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat odo) throws Exception {
        VratiSveSO so = new VratiSveSO(new DBBroker());
        so.izvrsi(odo);
        return so.getListaOpstihDomenskihObjekata();
    }

    public static void sacuvaj(OpstiDomenskiObjekat odo) throws Exception {
        SacuvajSO so = new SacuvajSO(new DBBroker());
        so.izvrsi(odo);
    }

    public static void izmeni(OpstiDomenskiObjekat odo) throws Exception {
        IzmeniSO so = new IzmeniSO(new DBBroker());
        so.izvrsi(odo);
    }

    public static Trener proveriPodatke(Trener trener) throws Exception {
        ProveriPodatkeSO so = new ProveriPodatkeSO(new DBBroker());
        so.izvrsi(trener);
        return so.getTrener();
    }

    public static boolean daLiJeUlogovan(Trener trener) {
        return listaUlogovanih.contains(trener);
    }

    public static void dodajUListuUlogovanih(Trener trener) {
        listaUlogovanih.add(trener);
    }

    public static DatabaseParameters vratiParametre() throws IOException {
        DBUtil dBUtil = new DBUtil();
        String url = dBUtil.vratiUrl();
        String username = dBUtil.vratiKorisnika();
        String password = dBUtil.vratiSifru();

        return new DatabaseParameters(url, username, password);
    }

    public static void sacuvajParametre(DatabaseParameters databaseParameters) throws IOException {
        DBUtil dBUtil = new DBUtil();
        dBUtil.postaviParametre(
                databaseParameters.getURL(),
                databaseParameters.getUsername(),
                databaseParameters.getPassword()
        );
    }

    public static List<Trening> vratiTreningeZaIgraca(Igrac igrac) throws Exception {

        try {
            VratiSveTreningeZaIgracaSO so = new VratiSveTreningeZaIgracaSO(new DBBroker());
            so.izvrsi(igrac);
            return so.getListaTreningaZaIgraca();
        } catch (Exception ex) {
            throw new Exception("Greska kod izvrsavanja metode vratiSveVrsteTreningaZaIgraca()");
        }
    }

    public static void sacuvajListu(List<OpstiDomenskiObjekat> listaOpstihDomenskiObjekata) throws Exception {
        SacuvajListuSO so = new SacuvajListuSO(new DBBroker());
        so.izvrsi(listaOpstihDomenskiObjekata);
    }

    public static void izbaciIzListeUlogovanih(Trener ulogovanTrener) {
        listaUlogovanih.remove(ulogovanTrener);
    }

}
