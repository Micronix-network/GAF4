package it.micronixnetwork.gaf.service.layout;

import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.CardConfService;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;

import org.springframework.core.io.Resource;
import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public interface LayoutConfigLoader {

    public static final String PARKING_ZONE = "cards_parking";

    /**
     * Carica la configurazione dei domini dal DB
     * @throws LayoutLoaderException
     */
    public  void load() throws LayoutLoaderException;
    
    /**
     * Ricarica la configurazione di un doinio
     * @param domain l'id univoco di un dominio
     * @throws LayoutLoaderException
     */
    public  void reloadDomain(String domain) throws LayoutLoaderException;

    /**
     * Salva l'xml di un layout su filesystem o sul db
     * @throws ServiceException
     */
    public  void save() throws LayoutLoaderException;

    /**
     * Restituisce la configurazione di un layout
     * @param layoutName il nome di un layout
     * @return la configurazioneo o null
     */
    public  LayoutConfig getLayoutConfig(String layoutName);

    /**
     * Rimuove una CARD da una zona
     * @param layout il nome del layout contenente la zona
     * @param zone il nome della zona
     * @param card il nome della card
     */
    public  void removeCardFromZone(String layout, String zone, String card);

    /**
     * Ritorna la lista ti tutte le carte configurate in un layout
     * @param layoutName il nome del layout
     * @return la lista degli stati di configurazione delle CARD piazzate
     */
    public  List<CardStatus> getLayOutPlacedCARD(String layoutName);

    /**
     * individua l'esistenza della configurazione di una zona di piazzamento
     * @param layout il nome del layout
     * @param zone il nome della zona che si vuole verificare
     * @return true se la zona è configurata false altrimenti
     */
    public  boolean existZone(String layout, String zone);

    /**
     * Ritorna la lista delle configurazioni delle CARD di una zona
     * @param layout il nome del layout
     * @param zone il nome della zona
     * @return la lista degli status delle CARD una lista vuota altrimenti 
     */
    public  List<CardStatus> getZoneCARDS(String layout, String zone);

    /**
     * Aggiunge una configurazione di una CARD ad una zona di un layout, se il layout o la zona non esistono
     * vengono create al volo.
     * @param layout il nome del layout
     * @param zone il nome della zona
     * @param card lo status della card da aggiungere
     * @param createZone stabilisce se abilitare o nemo la creazione automatica di una zona non esistente
     */
    public abstract void addCARDConfiguration(String layout, String zone, CardStatus card, boolean createZone);

    /**
     * Aggiunge una zona ad un layout se il layout non esiste lo crea in automatico
     * @param layout il nome del layout
     * @param zone il nome della zona
     */
    public  void addZone(String layout, String zone);

    /**
     * Rimuove tutte le CARD da una zona
     * @param layout il nome del layout che contiene la zona
     * @param zone il nome della zona da svuotare
     */
    public  void clearZone(String layout, String zone);

    /**
     * Recupera la configurazione di una zona di un layout specificato come parametro
     * @param layout il nome del layout
     * @param zone il nome della zona
     * @return la configurazione se la zona esiste null alrimenti
     */
    public  ZoneConf getLayoutZone(String layout, String zone);

    /**
     * Recupera lo stato di una CARD in un layout indipendentemente dalla zona in cui è configurato
     * @param layoutName il nome del layout
     * @param cardName il nome della CARD
     * @return lo stato della CARD, se esiste null altrimenti
     */
    public  CardStatus getLayOutCARD(String layoutName, String cardName);

    /**
     * Controlla la presenza della configurazione di una card in un layout
     * @param layout il nome del layout
     * @param cardName il nome univoco della card
     * @return true se la CARD esiste false altrimenti
     */
    public  boolean existCardInLayout(String layout, String cardName);


    public Collection<String> getLayoutNames();

    /**
     * Restituisce la mappa delle CARD presenti in un layout.
     * @param layout il nome del layout;
     * @return la mappa null altrimenti;
     */
    public  HashMap<String, CardStatus> getLayoutCardMap(String layout);


    public void setLayouts(Hashtable<String, LayoutConfig> layouts);
    
    public Hashtable<String, LayoutConfig> getLayouts();
    
    public boolean isLoaded();

}