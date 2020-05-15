package avn.sample.compiler;
import java.util.ArrayList;
import java.util.List;

public class SvgAst {
	
	private String tag = "svg";
	
	private String attributes;
	
	private List<SvgAst> body = new ArrayList<SvgAst>();
	
	public void setDefualtAttributes() {
		StringBuilder sb = new StringBuilder();
		sb.append("width=\"100\" height=\"100\" viewBox=\"0 0 100 100\" ");
		sb.append("xmlns=\"http://www.w3.org/2000/svg\" version: \"1.1\"");
		attributes = sb.toString();
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAttributes() {
		return attributes;
	}

	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}

	public List<SvgAst> getBody() {
		return body;
	}

	@Override
	public String toString() {
		return "SvgAst [tag=" + tag + ", attributes=" + attributes + ", body=" + body + "]";
	}

	
	
}
