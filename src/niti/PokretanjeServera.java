/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package niti;

import forme.GlavnaServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Milos
 */
public class PokretanjeServera extends Thread {

    GlavnaServerskaForma gsf;

    public PokretanjeServera(GlavnaServerskaForma gsf) {
        this.gsf = gsf;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            gsf.srediFormuPoslePokretanjaServera();
            System.out.println("Server program je pokrenut!\nCekam klijenta.");
            ZaustavljanjeServera zs = new ZaustavljanjeServera(this, ss);
            zs.start();
            while (!isInterrupted()) {
                Socket socket = ss.accept();
                System.out.println("Klijent se povezao sa serverom");
                ObradaZahtevaKlijenta ozk = new ObradaZahtevaKlijenta(socket);
                ozk.start();
            }
        } catch (IOException ex) {
            gsf.srediFormuNakonZaustavljanjaServera();
            System.out.println("Server je zaustavljen!");
        }
    }

}
