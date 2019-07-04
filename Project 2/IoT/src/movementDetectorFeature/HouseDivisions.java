package movementDetectorFeature;

import i18n.I18N;
import i18n.Messages;

/**
 * House division. 
 * @author fc47806, fc47878, fc47888
 */
public enum HouseDivisions {
	
	Cozinha(I18N.getString(Messages.KITCHEN)), 
	Sala(I18N.getString(Messages.LIVING_ROOM)), 
	Quarto_Mestre(I18N.getString(Messages.MASTER_ROOM)), 
	Quarto(I18N.getString(Messages.BEDROOM)), 
	WC(I18N.getString(Messages.TOILET)), 
	Dispensa(I18N.getString(Messages.PANTRY)), 
	Sotao(I18N.getString(Messages.ATTIC));
	
	// Attributes
	private String nome;
	
	/**
	 * Constructor
	 * @param nome nome da divisao
	 */
	HouseDivisions(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
