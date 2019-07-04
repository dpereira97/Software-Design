package dynamicAlerts;


/**
 * Creates a Pair of values
 * @author fc47806, fc47878, fc47888
 */
public class Pair<K, V> {

	// Atributes
	private K key;
	private V value;
	
	public Pair(K key, V value) {
		
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		
		return this.key;
	}

	public V getValue() {
		
		return this.value;
	}
}
