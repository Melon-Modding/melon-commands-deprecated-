package meloncommands;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import meloncommands.commands.kit.KitCommand;
import meloncommands.commands.kit.KittenCommand;
import meloncommands.commands.misc.WhereAmICommand;
import meloncommands.commands.role.RoleCommand;
import meloncommands.commands.tpa.TPACommand;
import meloncommands.commands.tpa.TPADenyCommand;
import meloncommands.commands.tpa.TPAcceptCommand;
import meloncommands.commands.utility.MelonCommandsCommand;
import meloncommands.config.ConfigData;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.SpawnCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;

import java.util.HashMap;


public class MelonCommands implements ModInitializer, GameStartEntrypoint{

	public static final String MOD_ID = "meloncommands";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final Gson GSON = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter()).create();

	public static final HashMap<String, String> colorMap = new HashMap<>();
	static{
		colorMap.put("purple", "§a");
		colorMap.put("blue", "§b");
		colorMap.put("brown", "§c");
		colorMap.put("green", "§d");
		colorMap.put("red", "§e");
		colorMap.put("black", "§f");
		colorMap.put("orange", "§1");
		colorMap.put("magenta", "§2");
		colorMap.put("light_blue", "§3");
		colorMap.put("yellow", "§4");
		colorMap.put("lime", "§5");
		colorMap.put("pink", "§6");
		colorMap.put("grey", "§7");
		colorMap.put("gray", "§7");
		colorMap.put("light_grey", "§8");
		colorMap.put("light_gray", "§8");
		colorMap.put("cyan", "§9");
		colorMap.put("white", "§0");
	}

	//
	public static GameRuleBoolean FIRE_TICKS = GameRules.register(new GameRuleBoolean("doFireTick", true));

	//
	public static TomlConfigHandler config;

	public void updateConfig() {

	}

	@Override
	public void onInitialize() {
		updateConfig();
		LOGGER.info("MelonCommands initialized");
		RoleCommand.buildRoleSyntax();
		LOGGER.info("MelonCommands Role Syntax Built");
	}

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {
		for(Command command : Commands.commands){
			if(command instanceof SpawnCommand){
				Commands.commands.remove(command);
				break;
			}
		}
		CommandHelper.createCommand(new KitCommand());
		CommandHelper.createCommand(new RoleCommand());
		CommandHelper.createCommand(new WhereAmICommand());
		CommandHelper.createCommand(new KittenCommand());
		// For summoning dummy lightningBolt
		// CommandHelper.createCommand(new Smite());
		CommandHelper.createCommand(new TPACommand());
		CommandHelper.createCommand(new TPAcceptCommand());
		CommandHelper.createCommand(new TPADenyCommand());
		CommandHelper.createCommand(new MelonCommandsCommand());


	}
}
