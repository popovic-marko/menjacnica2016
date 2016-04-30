package menjacnica.gui;

import java.awt.EventQueue;
import java.io.File;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;
import sistemske_operacije.SOUcitajIzFajla;

public class GUIKontroler {

	private static MenjacnicaGUI menjacnicaProzor;
	private static DodajKursGUI dodajKursProzor;
	private static ObrisiKursGUI obrsiKursProzor;
	private static IzvrsiZamenuGUI izvrsiZamenuProzor;
	private static MenjacnicaInterface menjacnica;
	private static Valuta valutaIzmena;
	private static Valuta valutaBrisanje;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					menjacnica = new Menjacnica();
					menjacnicaProzor = new MenjacnicaGUI();
					menjacnicaProzor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void ugasiAplikaciju(){
		int opcija = JOptionPane.showConfirmDialog(menjacnicaProzor.getContentPane(),
				"Da li ZAISTA zelite da izadjete iz apliacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}

	public static void prikaziDodajKursGUI() {
		dodajKursProzor = new DodajKursGUI();
		dodajKursProzor.setLocationRelativeTo(menjacnicaProzor.getContentPane());
		dodajKursProzor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(Valuta v) {
		valutaBrisanje = v;
		obrsiKursProzor = new ObrisiKursGUI();
		obrsiKursProzor.setLocationRelativeTo(menjacnicaProzor.getContentPane());
		obrsiKursProzor.setVisible(true);
	}

	public static void prikaziIzvrsiZamenuGUI(Valuta v){
		valutaIzmena = v;
		izvrsiZamenuProzor = new IzvrsiZamenuGUI(menjacnicaProzor, v);
		izvrsiZamenuProzor.setLocationRelativeTo(menjacnicaProzor.getContentPane());
		izvrsiZamenuProzor.setVisible(true);
	}

	public static void ucitajIzFajla(){
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaProzor.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}

	public static List<Valuta> vratiKursnuListu() {
		return menjacnica.vratiKursnuListu();
	}
	
	public static void sacuvajUFajl(){
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaProzor.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				menjacnica.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaProzor.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void unesiKurs(String naziv, String skrNaziv, int sifra, double prodajni,
									double kupovni, double srednji){

		try {
			Valuta valuta = new Valuta();

			valuta.setNaziv(naziv);
			valuta.setSkraceniNaziv(skrNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);

			menjacnica.dodajValutu(valuta);

			menjacnicaProzor.prikaziSveValute();
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaProzor.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(){
		menjacnica.obrisiValutu(valutaBrisanje);
	}
	
	public static String[] prikaziValutu(){
		String[] niz = new String[6];
		
		niz[0] = valutaBrisanje.getNaziv();
		niz[1] = valutaBrisanje.getSkraceniNaziv();
		niz[2] = "" + valutaBrisanje.getSifra();
		niz[3] = "" + valutaBrisanje.getProdajni();
		niz[4] = "" + valutaBrisanje.getKupovni();
		niz[5] = "" + valutaBrisanje.getSrednji();
		
		return niz;
	}
	
	public static void prikaziSveValute(){
		menjacnicaProzor.prikaziSveValute();
	}
	
	public static String[] prikaziValutuIzmena(){
		String[] niz = new String[3];
		
		niz[0] = "" + valutaIzmena.getProdajni();
		niz[1] = "" + valutaIzmena.getKupovni();
		niz[2] = valutaIzmena.getSkraceniNaziv();
		
		return niz;
	}

	public static double izvrsiZamenu(boolean selektovano, String iznos) throws Exception{
		double konacniIznos = 
				menjacnica.izvrsiTransakciju(valutaIzmena, selektovano, Double.parseDouble(iznos));
		
		return konacniIznos;	
	}
}
