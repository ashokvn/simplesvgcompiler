package avn.sample.compiler;
import java.util.ArrayList;
import java.util.List;

public class AST {
	private String type = "Drawing";
	
	private List<Expression> expList = new ArrayList<Expression>();

	public List<Expression> getExpList() {
		return expList;
	}
	

	public void setExpList(List<Expression> expList) {
		this.expList = expList;
	}



	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return "AST [type : " + type + ", expList : " + expList + "]";
	}
	
	
}
