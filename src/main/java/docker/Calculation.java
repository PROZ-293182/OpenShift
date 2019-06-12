package docker;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.google.gson.Gson;

import bsh.Interpreter;

class Expr {
	private String expr;

	public String getExpression() {
		return expr;
	}
}

class Result {
	private String result;

	public void setResult(String r) {
		result = r;
	}
}

//musi byc domyslny, publiczny konstruktor
@Path("/calculate")
public class Calculation {

	Interpreter interpreter = new Interpreter();
	Gson gson = new Gson();
	Expr expression = new Expr();
	Result result = new Result();

	@GET
	@Produces(javax.ws.rs.core.MediaType.TEXT_PLAIN)
	public String returnText() {
		return "You can calculate an expression when you use POST method.";
	}

	@POST
	@Consumes(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	@Produces(javax.ws.rs.core.MediaType.APPLICATION_JSON)
	public String calculate(String input) {
		expression = gson.fromJson(input, Expr.class);

		try {
			interpreter.eval("double result = " + expression.getExpression());
			result.setResult(((Double) interpreter.get("result")).toString());
		} catch (Exception e) {
			result.setResult("ERROR");
		}

		return gson.toJson(result);
	}
}