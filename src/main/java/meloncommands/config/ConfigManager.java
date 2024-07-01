package meloncommands.config;


import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import meloncommands.MelonCommands;
import turniplabs.halplibe.helper.RecipeBuilder;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigManager {

	private static final HashMap<String, File> kitFileHashMap = new HashMap<>();
	private static final HashMap<String, File> roleFileHashMap = new HashMap<>();
	private static final HashMap<String, File> configFileHashMap = new HashMap<>();

	public static final HashMap<String, KitData> kitHashMap = new HashMap<>();
	public static final HashMap<String, RoleData> roleHashMap = new HashMap<>();
	public static final HashMap<String, ConfigData> configHashMap = new HashMap<>();

	private static final Path roleFilePath = Paths.get(FabricLoader.getInstance().getConfigDir() + "/" + MelonCommands.MOD_ID + "/roles");
	private static final Path kitFilePath = Paths.get(FabricLoader.getInstance().getConfigDir() + "/" + MelonCommands.MOD_ID + "/kits");
	private static final Path configFilePath = Paths.get(FabricLoader.getInstance().getConfigDir() + "/" + MelonCommands.MOD_ID);

	static{new File("./config/meloncommands").mkdirs();}
	static{new File("./config/meloncommands/kits").mkdirs();}
	static{new File("./config/meloncommands/roles").mkdirs();}

	/**Prepares the config file for either saving or loading
	 * @param id Config Config entry identifier
	 */

	private static void prepareKitFile(String id) {
		if (kitFileHashMap.get(id) != null) {
			return;
		}
		kitFileHashMap.put(id, new File(kitFilePath.toFile(), id + ".json"));
	}

	private static void prepareRoleFile(String id) {
		if (roleFileHashMap.get(id) != null) {
			return;
		}
		roleFileHashMap.put(id, new File(roleFilePath.toFile(), id + ".json"));
	}

	private static void prepareConfigFile() {
		if (configFileHashMap.get("config") != null) {
			return;
		}
		configFileHashMap.put("config", new File(configFilePath.toFile(), "config" + ".json"));
	}

	private static void loadKit(String id) {
		prepareKitFile(id);

		try {
			if (!kitFileHashMap.get(id).exists()) {
				saveKit(id);
			}
			if (kitFileHashMap.get(id).exists()) {
				BufferedReader br = new BufferedReader(new FileReader(kitFileHashMap.get(id)));
				kitHashMap.put(id, MelonCommands.GSON.fromJson(br, KitData.class));
				saveKit(id);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load Kit: [" + id + "]'s configuration file; reverting to defaults");
			e.printStackTrace();
		}
	}

	private static void loadRole(String id) {
		prepareRoleFile(id);

		try {
			if (!roleFileHashMap.get(id).exists()) {
				saveRole(id);
			}
			if (roleFileHashMap.get(id).exists()) {
				BufferedReader br = new BufferedReader(new FileReader(roleFileHashMap.get(id)));
				roleHashMap.put(id, MelonCommands.GSON.fromJson(br, RoleData.class));
				saveRole(id);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load Role: [" + id + "]'s configuration file; reverting to defaults");
			e.printStackTrace();
		}
	}

	public static void loadConfig() {
		prepareConfigFile();

		try {
			if (!configFileHashMap.get("config").exists()) {
				saveConfig();
			}
			if (configFileHashMap.get("config").exists()) {
				BufferedReader br = new BufferedReader(new FileReader(configFileHashMap.get("config")));
				configHashMap.put("config", MelonCommands.GSON.fromJson(br, ConfigData.class));
				saveConfig();
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load Config configuration file; reverting to defaults");
			e.printStackTrace();
		}
	}

	public static void loadAllKits(){
		try {
			Set<String> files = listFilesUsingFilesList(kitFilePath.toString());
			kitHashMap.clear();
			for (String file : files){
				loadKit(file.replace(".json", ""));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void loadAllRoles(){
		try {
			Set<String> files = listFilesUsingFilesList(roleFilePath.toString());
			roleHashMap.clear();
			for (String file : files){
				loadRole(file.replace(".json", ""));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**Saves the specified config entry
	 * @param id Config entry identifier
	 */
	private static void saveKit(String id) {
		RecipeBuilder.isExporting = true;
		prepareKitFile(id);

		String jsonString = MelonCommands.GSON.toJson(kitHashMap.get(id));

		try (FileWriter fileWriter = new FileWriter(kitFileHashMap.get(id))) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			System.err.println("Couldn't save Kit: [" + id + "]'s configuration file");
			e.printStackTrace();
		}
		RecipeBuilder.isExporting = false;
	}

	private static void saveRole(String id) {
		RecipeBuilder.isExporting = true;
		prepareRoleFile(id);

		String jsonString = MelonCommands.GSON.toJson(roleHashMap.get(id));

		try (FileWriter fileWriter = new FileWriter(roleFileHashMap.get(id))) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			System.err.println("Couldn't save Role: [" + id + "]'s configuration file");
			e.printStackTrace();
		}
		RecipeBuilder.isExporting = false;
	}

	public static void saveConfig() {
		RecipeBuilder.isExporting = true;
		prepareConfigFile();

		String jsonString = MelonCommands.GSON.toJson(configHashMap.get("config"));

		try (FileWriter fileWriter = new FileWriter(configFileHashMap.get("config"))) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			System.err.println("Couldn't save Config configuration file");
			e.printStackTrace();
		}
		RecipeBuilder.isExporting = false;
	}

	/**
	 * Saves every config entry
	 */
	@ApiStatus.Internal
	public static void saveAllKits(){
		for (String id: kitHashMap.keySet()) {
			saveKit(id);
		}
	}

	public static void saveAllRoles(){
		for (String id: roleHashMap.keySet()) {
			saveRole(id);
		}
	}

	private static Set<String> listFilesUsingFilesList(String dir) throws IOException {
		try (Stream<Path> stream = Files.list(Paths.get(dir))) {
			return stream
				.filter(file -> !Files.isDirectory(file))
				.map(Path::getFileName)
				.map(Path::toString)
				.collect(Collectors.toSet());
		}
	}


	/**
	 * @param id Config entry identifier
	 */
	public static KitData getKitConfig(String id) {
		if (kitHashMap.get(id) == null){
			{
				kitHashMap.put(id, new KitData());
				loadKit(id);

				return kitHashMap.get(id);
			}
		}
		return kitHashMap.get(id);
	}

	public static RoleData getRoleConfig(String id) {
		if (roleHashMap.get(id) == null){
			{
				roleHashMap.put(id, new RoleData());
				loadRole(id);

				return roleHashMap.get(id);
			}
		}
		return roleHashMap.get(id);
	}

	public static ConfigData getConfig() {
		if (configHashMap.get("config") == null){
			{
				configHashMap.put("config", new ConfigData());
				loadConfig();

				return configHashMap.get("config");
			}
		}
		return configHashMap.get("config");
	}

	public static int removeKitConfig(String id){
		int error = 2;
		if (!kitFileHashMap.containsKey(id)) {
			error = 1;
			return error;
		}
		if(kitFileHashMap.get(id).delete()){
			error = 0;
		}
		kitFileHashMap.remove(id);
		kitHashMap.remove(id);
		return error;
	}

	public static int removeRoleConfig(String id){
		int error = 2;
		if (!roleFileHashMap.containsKey(id)) {
			error = 1;
			return error;
		}
		if(roleFileHashMap.get(id).delete()){
			error = 0;
		}
		roleFileHashMap.remove(id);
		roleHashMap.remove(id);
		return error;
	}




	static{
		loadAllKits();
		loadAllRoles();
		loadConfig();
	}
}
