package avn.sample.compiler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Lexer {

	private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	private static List<TypeDef> lexer(String code) {
		String[] tokens = code.split(" ");
		List<String> tokenList = Arrays.asList(tokens);

		List<TypeDef> lists = tokenList.stream().filter(c -> checkLength(c)).map(c -> mapr(c))
				.collect(Collectors.toList());

//		System.out.println(lists);

		return lists;
	}

	private static AST parser(List<TypeDef> tokenList) throws Exception {
		if (tokenList == null || tokenList.isEmpty()) {
			return null;
		}
		AST ast = new AST();
		List<Expression> expList = new ArrayList<Expression>();
		int i = 0;
		for (TypeDef typeDef : tokenList) {
			if ("word".equals(typeDef.getType())) {
				switch (typeDef.getValue()) {
				case "Paper":
					Expression exp = new Expression();
					exp.setName(typeDef.getValue());
					exp.setType("CallExpression");
					expList.add(exp);
					++i;
					break;
				case "Pen":
					exp = new Expression();
					exp.setName(typeDef.getValue());
					exp.setType("CallExpression");
					expList.add(exp);
					++i;
					break;
				case "Line":
					exp = new Expression();
					exp.setName(typeDef.getValue());
					exp.setType("CallExpression");
					expList.add(exp);
					++i;
					break;

				default:
					break;
				}

			} else if ("number".equals(typeDef.getType())) {
				Expression exp = expList.get(i - 1);
				ArrayList<String> tmpList = exp.getArgumentMap().get("number");
				if (tmpList == null) {
					tmpList = new ArrayList<String>();
					tmpList.add(typeDef.getValue());
					exp.getArgumentMap().put("number", tmpList);
				} else {
					tmpList.add(typeDef.getValue());
				}

			} else {
				throw new Exception("Paper command must be followed by a number.");
			}

		}
		ast.setExpList(expList);
//		System.out.println("Ast is "+ast);
		return ast;
	}

	private static SvgAst transformer(AST ast) throws Exception {
		SvgAst svgAst = new SvgAst();
		svgAst.setDefualtAttributes();
		int pen_color = 0;
		for (Expression exp : ast.getExpList()) {
			if (exp.getType().equals("CallExpression")) {
				switch (exp.getName()) {
				case "Paper":
					int paperColor = 100 - Integer.parseInt(exp.getArgumentMap().get("number").get(0));
					SvgAst tmpObj = new SvgAst();
					tmpObj.setTag("rect");
					StringBuilder sb = new StringBuilder("x=\"0\" y=\"0\" width=\"100\" height=\"100\"");
					sb.append(" fill=\"rgb(").append(paperColor).append("%,").append(paperColor).append("%,");
					sb.append(paperColor).append("%)\"");
					tmpObj.setAttributes(sb.toString());
					svgAst.getBody().add(tmpObj);
					break;
				case "Pen":
					pen_color = 100 - Integer.parseInt(exp.getArgumentMap().get("number").get(0));
					break;
				case "Line":
					tmpObj = new SvgAst();
					tmpObj.setTag("line");

					List<String> numberList = exp.getArgumentMap().get("number");

					sb = new StringBuilder("x1=\"").append(numberList.get(0)).append("\" y1=\"")
							.append(100 - Integer.parseInt(numberList.get(1)));
					sb.append("\" x2=\"").append(numberList.get(2)).append("\" y2=\"")
							.append(100 - Integer.parseInt(numberList.get(3)));
					sb.append("\" stroke=\"rgb(").append(pen_color).append("%,").append(pen_color).append("%,")
							.append(pen_color).append("%)\"");
					sb.append(" stroke-linecap=\"round\"");
					tmpObj.setAttributes(sb.toString());
					svgAst.getBody().add(tmpObj);
					break;
				default:
					break;
				}
			} else {
				throw new Exception("Invalid type in Expression " + exp.getType());
			}

		}
//		System.out.println(svgAst);
		return svgAst;
	}

	private static String generator(SvgAst rootAst) {

		StringBuilder sb = new StringBuilder("<");
		sb.append(rootAst.getTag()).append(" ").append(rootAst.getAttributes()).append(" >");
		List<SvgAst> children = rootAst.getBody();
		for (SvgAst child : children) {
			sb.append('\n').append('\t').append('<');
			sb.append(child.getTag()).append(" ").append(child.getAttributes()).append(" >");
			sb.append("</").append(child.getTag()).append(">");
		}
		sb.append('\n');
		sb.append("</").append(rootAst.getTag()).append(">");
//		System.out.println(sb.toString());
		return sb.toString();
	}

	private static boolean checkLength(String s) {
		return s.length() > 0;
	}

	private static TypeDef mapr(String s) {
		TypeDef def = new TypeDef();
		if (isNumeric(s)) {
			def.setType("number");
			def.setValue(s);
		} else {
			def.setType("word");
			def.setValue(s);
		}
		return def;
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		return pattern.matcher(strNum).matches();
	}

	public static void main(String... strings) {
		try {
			System.out.println(generator(
					transformer(parser(lexer("Paper 100 Pen 0 Line 50 77 22 27 Line 22 27 78 27 Line 78 27 50 77")))));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	Map<String, String> tokenMap = null;
//	List outputLists = new ArrayList<HashMap<String,String>>();
//	for (String s : tokenList) {
//		tokenMap = new HashMap<String, String>();
//		if(isNumeric(s)) {
//			tokenMap.put("type", "number");
//			tokenMap.put("value", s);
//		} else {
//			tokenMap.put("type", "word");
//			tokenMap.put("value", s);				
//		}
//		outputLists.add(tokenMap);
//	}

}
