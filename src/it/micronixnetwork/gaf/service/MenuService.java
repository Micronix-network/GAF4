package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.Menu;
import it.micronixnetwork.gaf.exception.ServiceException;

public interface MenuService {
    
    /**
     * Recupera l'albero del menu principale di un applicazione
     * @param refresh se true viene ricaricato il menu dal db
     * @return
     */
    Menu getMenu(boolean refresh) throws ServiceException;
    
    
    /**
     * Salva un menu sul db
     * @param menu il menu da salvare
     */
    void saveMenu(Menu menu) throws ServiceException;;
    
}
