package prophetsama.testing;

import net.fabricmc.api.ModInitializer;
import net.minecraft.core.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import turniplabs.halplibe.util.GameStartEntrypoint;
import turniplabs.halplibe.util.RecipeEntrypoint;
import turniplabs.halplibe.util.TomlConfigHandler;
import turniplabs.halplibe.util.toml.Toml;


public class ExampleMod implements ModInitializer, GameStartEntrypoint, RecipeEntrypoint {
    public static final String MOD_ID = "melonbtacommands";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

//
//	Example using halplibe config
//	public void updateConfig() {
//		Toml properties = new Toml("MelonBTA Commands Configuration");
//
//		//properties.addEntry("starterKitItems", "List of items to be added to the Starter Kit.", );
//
//		properties.addEntry("entry", "comment", 0);
//
//		TomlConfigHandler config = new TomlConfigHandler(null, MOD_ID, properties);
//
//		entry = config.getInt("spawnX");
//	}


    @Override
    public void onInitialize() {
        //updateConfig();
		LOGGER.info("Testing Grounds initialized.");
    }

	@Override
	public void beforeGameStart() {

	}

	@Override
	public void afterGameStart() {

	}

	@Override
	public void onRecipesReady() {

	}
}
