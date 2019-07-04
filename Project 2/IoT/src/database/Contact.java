package database;

/**
 * Contacto telefonico
 * @author fc47806, fc47878, fc47888
 *
 */
public class Contact {
	
	// Atributos
	private String phoneNum;
	
	/**
	 * Constructor
	 */
	public Contact(String pN){
		phoneNum = pN;
	}
	
	public String getPhoneNum() {
		
		return this.phoneNum;
	}
	
	@Override
	public String toString() {
		
		return "phoneNum: " + phoneNum;
	}
}
