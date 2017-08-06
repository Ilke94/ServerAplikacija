/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import pomoc.DBKonstante;

/**
 *
 * @author Milos
 */
public class DBUtil {

    private Properties properties;

    public DBUtil() throws FileNotFoundException, IOException {
        properties = new Properties();
        properties.load(new FileInputStream("database.config"));
    }

    public String vratiUrl() {
        return properties.getProperty(DBKonstante.URL);
    }

    public String vratiKorisnika() {
        return properties.getProperty(DBKonstante.USERNAME);
    }

    public String vratiSifru() {
        return properties.getProperty(DBKonstante.PASSWORD);
    }

    public void postaviParametre(String url, String username, String password) throws FileNotFoundException, IOException {
        properties.setProperty(DBKonstante.URL, url);
        properties.setProperty(DBKonstante.USERNAME, username);
        properties.setProperty(DBKonstante.PASSWORD, password);

        FileOutputStream fos = new FileOutputStream("database.config");
        properties.store(fos, "Database Configuration");
    }

}
