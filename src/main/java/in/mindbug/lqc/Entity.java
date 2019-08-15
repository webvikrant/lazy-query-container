package in.mindbug.lqc;

import java.util.HashMap;

public class Entity {
	private final HashMap<String, Attribute> map;

	public Entity() {
		super();
		this.map = new HashMap<>();
	}

	HashMap<String, Attribute> getMap() {
		return map;
	}

}
