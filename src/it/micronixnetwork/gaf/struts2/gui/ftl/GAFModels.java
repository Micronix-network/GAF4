package it.micronixnetwork.gaf.struts2.gui.ftl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork2.util.ValueStack;

public class GAFModels {
    
    protected ValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;
     
    protected DivModel div;
    protected SetModel set;
    protected GetCardParamModel getCardParam;
    protected PropertyDialogModel  propDialog;
    protected ImportCardPropertiesGuiModel importGui;
    protected GeneralPropertiesTabModel generalPropertiesTab;
    protected SelectModel select;
    protected InputParamModel inputParam;
    protected PlaceCardsModel placeCards;
    protected MainMenuModel mainMenu;
    
    
    public GAFModels(ValueStack stack, HttpServletRequest req, HttpServletResponse res){
        this.stack = stack;
        this.req = req;
        this.res = res;
    }
     
    public DivModel getDiv() {
        if (div == null) {
            div = new DivModel(stack, req, res);
        }
        return div;
    }
    
    public SetModel getSet() {
        if (set == null) {
            set = new SetModel(stack, req, res);
        }
        return set;
    }
    
    public InputParamModel getInputParam() {
        if (inputParam == null) {
            inputParam = new InputParamModel(stack, req, res);
        }
        return inputParam;
    }
    
    public SelectModel getSelect() {
        if (select == null) {
            select = new SelectModel(stack, req, res);
        }
        return select;
    }
    
    public GetCardParamModel getGetCardParam() {
	if (getCardParam == null) {
	    getCardParam = new GetCardParamModel(stack, req, res);
        }
        return getCardParam;
    }
    
    
    public GeneralPropertiesTabModel getGeneralPropertiesTab(){
	if (generalPropertiesTab == null) {
	    generalPropertiesTab = new GeneralPropertiesTabModel(stack, req, res);
        }
        return generalPropertiesTab;
    }
    
    public ImportCardPropertiesGuiModel getImportGui(){
	if (importGui == null) {
	    importGui = new ImportCardPropertiesGuiModel(stack, req, res);
        }
        return importGui;
    }
    
    public PropertyDialogModel getPropDialog(){
	if (propDialog == null) {
	    propDialog = new PropertyDialogModel(stack, req, res);
        }
        return propDialog;
    }
    
    public PlaceCardsModel getPlaceCards(){
	if (placeCards == null) {
	    placeCards = new PlaceCardsModel(stack, req, res);
        }
        return placeCards;
    }
    
    public MainMenuModel getMainMenu() {
	if (mainMenu == null) {
	    mainMenu = new MainMenuModel(stack, req, res);
        }
        return mainMenu;
    }
    
}
