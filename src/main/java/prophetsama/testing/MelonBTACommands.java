package prophetsama.testing;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.data.gamerule.GameRuleBoolean;
import net.minecraft.core.data.gamerule.GameRules;
import net.minecraft.core.data.registry.recipe.adapter.ItemStackJsonAdapter;
import net.minecraft.core.item.Item;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.net.command.Command;
import net.minecraft.core.net.command.Commands;
import net.minecraft.core.net.command.commands.SpawnCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import prophetsama.testing.commands.Kitten;
import prophetsama.testing.commands.Spawn;
import prophetsama.testing.commands.Kit;
import prophetsama.testing.commands.WhereAmI;
import turniplabs.halplibe.helper.CommandHelper;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;


public class MelonBTACommands implements ModInitializer, GameStartEntrypoint{
    public static final String MOD_ID = "melonbtacommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static final Gson GSON = (new GsonBuilder()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().registerTypeAdapter(ItemStack.class, new ItemStackJsonAdapter()).create();

	//

	public static GameRuleBoolean FIRE_TICKS = GameRules.register(new GameRuleBoolean("doFireTick", true));
	public static GameRuleBoolean DAYLIGHT_CYCLE = GameRules.register(new GameRuleBoolean("doDaylightCycle", true));

	//
	public static TomlConfigHandler config;

	public void updateConfig() {

	}

	@Override
	public void onInitialize() {
		updateConfig();
		LOGGER.info("Testing Grounds initialized.");
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
		CommandHelper.createCommand(new Spawn());
		CommandHelper.createCommand(new Kit());
		CommandHelper.createCommand(new WhereAmI());
		CommandHelper.createCommand(new Kitten());
	}
}
