/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import domen.Igrac;
import domen.Licenca;
import domen.Mesto;
import domen.OpstiDomenskiObjekat;
import domen.Trener;
import domen.Trening;
import domen.VrstaTreninga;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milos
 */
public class DBBroker {

    public List<OpstiDomenskiObjekat> vratiSve(OpstiDomenskiObjekat odo) throws SQLException, IOException {
        List<OpstiDomenskiObjekat> listaOpstihDomenskihObjekata = new ArrayList<>();
        Connection konekcija = Konekcija.vratiObjekat().vratiKonekciju();
        String upit = "SELECT " + odo.vratiSelect() + " FROM " + odo.vratiJoin();
        Statement statement = konekcija.createStatement();
        ResultSet rs = statement.executeQuery(upit);
        listaOpstihDomenskihObjekata = odo.vratiListu(rs);
        statement.close();
        return listaOpstihDomenskihObjekata;
    }

    public void sacuvaj(OpstiDomenskiObjekat odo) throws SQLException, IOException {
        Connection konekcija = Konekcija.vratiObjekat().vratiKonekciju();
        String upit = "INSERT INTO " + odo.vratiNazivTabele() + " VALUES (" + odo.vratiVrednostiZaInsert() + ")";
        System.out.println("\n\n\n ************** \n" + upit);
        Statement statement = konekcija.createStatement();
        statement.executeUpdate(upit);
        statement.close();
    }

    public void izmeni(OpstiDomenskiObjekat odo) throws SQLException, IOException {
        Connection konekcija = Konekcija.vratiObjekat().vratiKonekciju();
        String upit = "UPDATE " + odo.vratiNazivTabele() + " SET " + odo.vratiVrednostiZaUpdate() + " WHERE " + odo.vratiWhere();
        System.out.println("\n\n\n ************** \n" + upit);
        Statement statement = konekcija.createStatement();
        statement.executeUpdate(upit);
        statement.close();
    }

    public Trener proveriPodatke(Trener trener) throws Exception {
        try {
            Connection konekcija = Konekcija.vratiObjekat().vratiKonekciju();
            String upit = "SELECT t.id,jmbg,ime,prezime,ulica,broj,username,pass,m.id as mesto_id,m.ptt AS ptt,m.naziv AS mesto,l.sifra,l.naziv AS licenca "
                    + "FROM trener t INNER JOIN mesto m ON t.mesto_id=m.id INNER JOIN licenca l ON t.licenca_id=l.sifra "
                    + "WHERE t.username=? LIMIT 1";
            PreparedStatement ps = konekcija.prepareStatement(upit);
            ps.setString(1, trener.getUsername());

            ResultSet rs = ps.executeQuery();
            boolean hasRow = rs.next();

            if (hasRow && rs.getString("pass").equals(trener.getPassword())) {
                trener.setTrenerId(rs.getInt("id"));
                trener.setPassword(null);
                trener.setJmbg(rs.getString("jmbg"));
                trener.setIme(rs.getString("ime"));
                trener.setPrezime(rs.getString("prezime"));
                trener.setUlica(rs.getString("ulica"));
                trener.setBroj(rs.getString("broj"));

                Mesto mesto = new Mesto();
                mesto.setId(rs.getInt("mesto_id"));
                mesto.setNaziv(rs.getString("mesto"));
                mesto.setPostanskiBroj(rs.getInt("ptt"));
                trener.setMesto(mesto);

                Licenca licenca = new Licenca();
                licenca.setSifra(rs.getInt("sifra"));
                licenca.setNaziv(rs.getString("licenca"));
                trener.setLicenca(licenca);

                return trener;
            }
            return null;
        } catch (SQLException ex) {
            throw ex;
        }
    }

    public List<Trening> vratiTreningeZaIgraca(Igrac igrac) throws SQLException, IOException {
        List<Trening> lista = new ArrayList<>();
        Connection konekcija = Konekcija.vratiObjekat().vratiKonekciju();
        String upit = "SELECT * FROM trening t JOIN vrsta_treninga vt ON t.id_vrsta_treninga=vt.id  JOIN trener tr ON t.id_trener=tr.id WHERE id_igrac=?";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, igrac.getId());
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int id = rs.getInt("id");
            Date datum = rs.getDate("datum");
            int duzina = rs.getInt("duzina");
            String cilj = rs.getString("cilj");
            String sadrzaj = rs.getString("sadrzaj");

            int vrstaTreningaId = rs.getInt("id_vrsta_treninga");
            String vrstaTreningaNaziv = rs.getString("naziv");

            int idTrener = rs.getInt("id_trener");
            String ime = rs.getString("ime");
            String prezime = rs.getString("prezime");
            lista.add(new Trening(id, datum, duzina, cilj, sadrzaj, new Trener(idTrener, ime, prezime), igrac, new VrstaTreninga(vrstaTreningaId, vrstaTreningaNaziv)));
        }
        ps.close();
        return lista;

    }

    public void commit() {
        try {
            Konekcija.vratiObjekat().vratiKonekciju().commit();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            Konekcija.vratiObjekat().vratiKonekciju().rollback();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
