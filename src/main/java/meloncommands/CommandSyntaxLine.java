package meloncommands;

public class CommandSyntaxLine {

	public String name;
	public String owner;
	public String message;

	public CommandSyntaxLine(String name, String owner, String message){
		this.name = name;
		this.owner = owner;
		this.message = message;
	}
	public CommandSyntaxLine(String name, String message){
		this.name = name;
		this.owner = "none";
		this.message = message;
	}

}
