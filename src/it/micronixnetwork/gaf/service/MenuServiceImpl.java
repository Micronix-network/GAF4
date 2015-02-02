package it.micronixnetwork.gaf.service;

import it.micronixnetwork.gaf.domain.Menu;
import it.micronixnetwork.gaf.exception.ServiceException;
import it.micronixnetwork.gaf.service.hibernate.HibernateSupport;

import org.springframework.transaction.annotation.Transactional;


@Transactional(rollbackFor = Throwable.class)
public class MenuServiceImpl extends HibernateSupport implements MenuService{
    
    Menu menu;

    @Override
    public Menu getMenu(boolean refresh) throws ServiceException {
	if(menu==null || refresh){
	    menu=(Menu)getCurrentSession().get(Menu.class, 1);
	}
	if(menu==null) throw new ServiceException("Main menu loading problem");
	return menu;
    }

    @Override
    public void saveMenu(Menu menu) throws ServiceException {
	// TODO Auto-generated method stub
    }

}
