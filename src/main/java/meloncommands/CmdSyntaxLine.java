package meloncommands;

public class CmdSyntaxLine {

	public String name;
	public String owner;
	public String message;

	public CmdSyntaxLine(String name, String owner, String message){
		this.name = name;
		this.owner = owner;
		this.message = message;
	}
	public CmdSyntaxLine(String name, String message){
		this.name = name;
		this.owner = "none";
		this.message = message;
	}

}
