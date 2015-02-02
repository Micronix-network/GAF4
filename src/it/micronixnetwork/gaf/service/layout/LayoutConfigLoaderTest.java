package it.micronixnetwork.gaf.service.layout;

import java.util.Collection;
import java.util.Hashtable;

import it.micronixnetwork.gaf.test.TestRunner;

public class LayoutConfigLoaderTest extends TestRunner {

    private LayoutConfigLoader layoutConfigLoader;

    private LayoutConfigLoader layoutConfigLoaderImport;

    public void setLayoutConfigLoader(LayoutConfigLoader layoutConfigLoader) {
	this.layoutConfigLoader = layoutConfigLoader;
    }

    public void setLayoutConfigLoaderImport(LayoutConfigLoader layoutConfigLoaderImport) {
	this.layoutConfigLoaderImport = layoutConfigLoaderImport;
    }

    @Override
    public boolean runTest() {
	if (layoutConfigLoader == null)
	    return false;

	try {
	    layoutConfigLoader.load();
	    Collection<String> names = layoutConfigLoader.getLayoutNames();
	    if (names == null) {
		info("Lista dei nomi dei layout null;");
		return false;
	    }
	    for (String name : names) {
		info("Layout: " + name);
		LayoutConfig lc = layoutConfigLoader.getLayoutConfig(name);
		info(lc);
	    }

	    layoutConfigLoader.save();

	    layoutConfigLoader.load();
	    names = layoutConfigLoader.getLayoutNames();
	    if (names == null) {
		info("Lista dei nomi dei layout null;");
		return false;
	    }
	    for (String name : names) {
		info("Layout: " + name);
		LayoutConfig lc = layoutConfigLoader.getLayoutConfig(name);
		info(lc);
	    }

	    if (layoutConfigLoaderImport != null) {
		layoutConfigLoaderImport.load();
		Hashtable<String, LayoutConfig> layouts = layoutConfigLoaderImport.getLayouts();
		if (layouts != null) {
		    for (LayoutConfig layout : layouts.values()) {
			debug(layout);
		    }
		    layoutConfigLoader.setLayouts(layouts);
		    layoutConfigLoader.save();
		}
	    }

	} catch (LayoutLoaderException e) {
	    error(e.getMessage(), e);
	    return false;
	}

	return true;
    }

}
