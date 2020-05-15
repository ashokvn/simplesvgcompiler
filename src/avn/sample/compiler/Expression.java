package avn.sample.compiler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Expression {

	private String type;
	private String name;
	
	private Map<String, ArrayList<String>> argumentMap = new HashMap<String, ArrayList<String>>();

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ArrayList<String>> getArgumentMap() {
		return argumentMap;
	}

	@Override
	public String toString() {
		return "Expression [type :" + type + ", name : " + name + ", argumentMap : " + argumentMap + "]";
	}

	
	
}
