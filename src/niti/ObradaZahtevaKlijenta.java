/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import domen.DatabaseParameters;
import domen.Igrac;
import domen.OpstiDomenskiObjekat;
import domen.Trener;
import domen.Trening;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import logika.Kontroler;
import pomoc.Operacija;
import pomoc.StatusOdgovora;
import forme.GlavnaServerskaForma;
import transfer.KlijentskiZahtev;
import transfer.ServerskiOdgovor;

/**
 *
 * @author Milos
 */
public class ObradaZahtevaKlijenta extends Thread {

    Socket socket;
    Trener ulogovanTrener;

    public ObradaZahtevaKlijenta(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            obradiKlijenta(socket);
        } catch (IOException | ClassNotFoundException ex) {
            //Logger.getLogger(ObradaZahtevaKlijenta.class.getName()).log(Level.SEVERE, null, ex);
            Kontroler.izbaciIzListeUlogovanih(ulogovanTrener);
        }
    }

    private void obradiKlijenta(Socket socket) throws IOException, ClassNotFoundException {
        while (true) {
            System.out.println("Cekam zahtev klijenta");
            ObjectInputStream inSocket = new ObjectInputStream(socket.getInputStream());
            Object object = inSocket.readObject();
            KlijentskiZahtev kz = (KlijentskiZahtev) object;

            ServerskiOdgovor so = obradiZahtev(kz);
            ObjectOutputStream outSocket = new ObjectOutputStream(socket.getOutputStream());
            outSocket.writeObject(so);
            outSocket.flush();
        }
    }

    private ServerskiOdgovor obradiZahtev(KlijentskiZahtev kz) throws IOException {
        ServerskiOdgovor so = new ServerskiOdgovor();

        int akcija = kz.getAkcija();
        switch (akcija) {
            case Operacija.VRATI_SVE_ZA_ODO: {
                try {
                    List<OpstiDomenskiObjekat> lista = Kontroler.vratiSve((OpstiDomenskiObjekat) kz.getParametar());
                    so.setOdgovor(lista);
                    so.setStatus(StatusOdgovora.OK);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                }
            }
            return so;

            case Operacija.SACUVAJ_ODO:
                try {
                    Kontroler.sacuvaj((OpstiDomenskiObjekat) kz.getParametar());
                    so.setStatus(StatusOdgovora.OK);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                }
                return so;

            case Operacija.IZMENI_ODO:
                try {
                    Kontroler.izmeni((OpstiDomenskiObjekat) kz.getParametar());
                    so.setStatus(StatusOdgovora.OK);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                }
                return so;

            case Operacija.PROVERI_PODATKE_O_TRENERU:
                if (!Kontroler.daLiJeUlogovan((Trener) kz.getParametar())) {
                    try {
                        Trener trener = Kontroler.proveriPodatke((Trener) kz.getParametar());
                        so.setStatus(StatusOdgovora.OK);
                        so.setOdgovor(trener);

                        if (trener != null) {
                            Kontroler.dodajUListuUlogovanih(trener);
                            ulogovanTrener = trener;
                        }

                    } catch (Exception ex) {
                        Logger.getLogger(GlavnaServerskaForma.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    so.setStatus(StatusOdgovora.ERROR);
                    so.setPoruka("Dati trener je već prijavljen na sistem");
                }
                return so;

            case Operacija.SACUVAJ_LISTU_ODO:
                try {
                    Kontroler.sacuvajListu((List<OpstiDomenskiObjekat>) kz.getParametar());
                    so.setStatus(StatusOdgovora.OK);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                    Logger.getLogger(GlavnaServerskaForma.class.getName()).log(Level.SEVERE, null, ex);
                }
                return so;

            case Operacija.VRATI_PARAMETRE:
                try {
                    DatabaseParameters databaseParameters = Kontroler.vratiParametre();
                    so.setStatus(StatusOdgovora.OK);
                    so.setOdgovor(databaseParameters);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                    Logger.getLogger(GlavnaServerskaForma.class.getName()).log(Level.SEVERE, null, ex);
                }
                return so;

            case Operacija.SACUVAJ_PARAMETRE:
                try {
                    Kontroler.sacuvajParametre((DatabaseParameters) kz.getParametar());
                    so.setStatus(StatusOdgovora.OK);

                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                    Logger.getLogger(GlavnaServerskaForma.class.getName()).log(Level.SEVERE, null, ex);
                }
                return so;

            case Operacija.VRATI_SVE_TRENINGE_ZA_IGRACA:
                try {
                    List<Trening> lista = Kontroler.vratiTreningeZaIgraca((Igrac) kz.getParametar());
                    so.setStatus(StatusOdgovora.OK);
                    so.setOdgovor(lista);
                } catch (Exception ex) {
                    so.setStatus(StatusOdgovora.ERROR);
                    Logger.getLogger(GlavnaServerskaForma.class.getName()).log(Level.SEVERE, null, ex);
                }
                return so;

            default:
                return so;
        }
    }

}
