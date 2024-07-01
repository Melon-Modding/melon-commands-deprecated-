package meloncommands;

import net.minecraft.core.net.command.CommandSender;

import java.util.*;

public class CmdSyntaxBuilder {

	ArrayList<CmdSyntaxLine> syntaxLines = new ArrayList<>();


	public void append(CmdSyntaxLine syntaxLine){
		if(syntaxLine.owner.equals("none")){
			syntaxLines.add(syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
					} else {
						for (int j = i + 1; j < syntaxLines.size(); j++) {
							if (syntaxLines.get(j).name.equals(syntaxLines.get(i).owner)) {
								if (j == syntaxLines.size() - 1) {
									syntaxLines.add(syntaxLine);
									break;
								}
								continue;
							}
							syntaxLines.add(syntaxLine);
							break;
						}
					}
					break;
				}
			}
		}
	}

	public void append(String name, String message){
		CmdSyntaxLine syntaxLine = new CmdSyntaxLine(name, message);
		if(syntaxLine.owner.equals("none")){
			syntaxLines.add(syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
					} else {
						for (int j = i + 1; j < syntaxLines.size(); j++) {
							if (syntaxLines.get(j).name.equals(syntaxLines.get(i).owner)) {
								if (j == syntaxLines.size() - 1) {
									syntaxLines.add(syntaxLine);
									break;
								}
								continue;
							}
							syntaxLines.add(syntaxLine);
							break;
						}
					}
					break;
				}
			}
		}
	}

	public void append(String name, String owner, String message){
		CmdSyntaxLine syntaxLine = new CmdSyntaxLine(name, owner, message);
		if(syntaxLine.owner.equals("none")){
			syntaxLines.add(syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
					} else {
						for (int j = i + 1; j < syntaxLines.size(); j++) {
							if (syntaxLines.get(j).name.equals(syntaxLines.get(i).owner)) {
								if (j == syntaxLines.size() - 1) {
									syntaxLines.add(syntaxLine);
									break;
								}
								continue;
							}
							syntaxLines.add(syntaxLine);
							break;
						}
					}
					break;
				}
			}
		}
	}

	public void prepend(CmdSyntaxLine syntaxLine){
		if(syntaxLines.isEmpty()){
			syntaxLines.add(syntaxLine);
		} else if (syntaxLine.owner.equals("none")){
			syntaxLines.add(0, syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
                    } else {
						syntaxLines.add(i + 1, syntaxLine);
                    }
                    break;
                }
			}
		}
	}

	public void prepend(String name, String message){
		CmdSyntaxLine syntaxLine = new CmdSyntaxLine(name, message);
		if(syntaxLines.isEmpty()){
			syntaxLines.add(syntaxLine);
		} else if (syntaxLine.owner.equals("none")){
			syntaxLines.add(0, syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
                    } else {
						syntaxLines.add(i + 1, syntaxLine);
                    }
                    break;
                }
			}
		}
	}

	public void prepend(String name, String owner, String message){
		CmdSyntaxLine syntaxLine = new CmdSyntaxLine(name, owner, message);
		if(syntaxLines.isEmpty()){
			syntaxLines.add(syntaxLine);
		} else if (syntaxLine.owner.equals("none")){
			syntaxLines.add(0, syntaxLine);
		} else {
			for (int i = 0; i < syntaxLines.size(); i++) {
				if (syntaxLines.get(i).name.equals(syntaxLine.owner)) {
					if (syntaxLines.size() - 1 == i) {
						syntaxLines.add(syntaxLine);
                    } else {
						syntaxLines.add(i + 1, syntaxLine);
                    }
                    break;
                }
			}
		}
	}

	public void printAll(CommandSender sender){
		for(CmdSyntaxLine syntaxLine : syntaxLines){
			sender.sendMessage(syntaxLine.message);
		}
	}

	ArrayList<String> layerOwnerMessages = new ArrayList<>();

	String insideLayersOwner;
	CmdSyntaxLine insideLayer;
	private void printLayerOwners(CmdSyntaxLine syntaxLine, CommandSender sender){
		insideLayer = syntaxLine;
		for(int i = syntaxLines.size() - 1; i >= 0; i--){
			if(insideLayer.owner.equals("none")) {
				break;
			}
			if(syntaxLines.get(i).name.equals(syntaxLine.name)){
				insideLayer = syntaxLine;
				for(int j = i-1; j >= 0; j--){
					assert insideLayer != null;
					if(syntaxLines.get(j).name.equals(insideLayer.owner)){
						layerOwnerMessages.add(0, syntaxLines.get(j).message);
						insideLayer = syntaxLines.get(j);
						printLayerOwners(insideLayer, sender);
					}
				}
			}
		}
		for(String message : layerOwnerMessages) {
			sender.sendMessage(message);
		}
		layerOwnerMessages.clear();
		insideLayersOwner = null;
	}

	String thisLayerOwner = null;
	public void printLayerUnderOwner(String name, CommandSender sender){
		for(int i = 0; i < syntaxLines.size(); i++){
			if(syntaxLines.get(i).name.equals(name)){
				printLayerOwners(syntaxLines.get(i), sender);
				sender.sendMessage(syntaxLines.get(i).message);
				for(int j = i+1; j < syntaxLines.size(); j++){
					if(syntaxLines.get(j).owner.equals(name)){
						sender.sendMessage(syntaxLines.get(j).message);
						thisLayerOwner = syntaxLines.get(j).name;
					}
				}
			}
		}
		thisLayerOwner = null;
	}

	boolean printedLayerOwners = false;
	public void printAllLayersUnderOwner(String name, CommandSender sender){

		for(int i = 0; i < syntaxLines.size(); i++){
			if(syntaxLines.get(i).name.equals(name)){
				if(!printedLayerOwners) {
					printLayerOwners(syntaxLines.get(i), sender);
					sender.sendMessage(syntaxLines.get(i).message);
					printedLayerOwners = true;
				}
				for(int j = i+1; j < syntaxLines.size(); j++){
					if(syntaxLines.get(j).owner.equals(name)){
						sender.sendMessage(syntaxLines.get(j).message);
						thisLayerOwner = syntaxLines.get(j).name;
					} else if (syntaxLines.get(j).owner.equals(thisLayerOwner)) {
						printedLayerOwners = true;
						printAllLayersUnderOwner(thisLayerOwner, sender);
					}
				}
			}
		}
		thisLayerOwner = null;
		printedLayerOwners = false;
	}

	public void clear(){
		syntaxLines.clear();
	}
}
