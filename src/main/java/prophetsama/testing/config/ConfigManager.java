package prophetsama.testing.config;


import net.fabricmc.loader.api.FabricLoader;
import org.jetbrains.annotations.ApiStatus;
import prophetsama.testing.MelonBTACommands;
import turniplabs.halplibe.HalpLibe;
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
	private static final HashMap<String, File> fileHashMap = new HashMap<>();
	public static final HashMap<String, KitData> configHashMap = new HashMap<>();
	private static final Path filePath = Paths.get(FabricLoader.getInstance().getConfigDirectory() + "/" + MelonBTACommands.MOD_ID);

	/**Prepares the config file for either saving or loading
	 * @param id Config Config entry identifier
	 */
	private static void prepareKitFile(String id) {
		if (fileHashMap.get(id) != null) {
			return;
		}

		try {
			Files.createDirectories(filePath);
		} catch (IOException e) {
			System.err.println("Failed to create directory!" + e.getMessage());
		}
		fileHashMap.put(id, new File(filePath.toFile(), id + ".json"));
	}
	private static void load(String id) {
		prepareKitFile(id);

		try {
			if (!fileHashMap.get(id).exists()) {
				save(id);
			}
			if (fileHashMap.get(id).exists()) {
				BufferedReader br = new BufferedReader(new FileReader(fileHashMap.get(id)));
				configHashMap.put(id, MelonBTACommands.GSON.fromJson(br, KitData.class));
				save(id);
			}
		} catch (FileNotFoundException e) {
			System.err.println("Couldn't load MelonBTACommands configuration file; reverting to defaults");
			e.printStackTrace();
		}
	}
	public static void loadAll(){
		try {
			Set<String> files = listFilesUsingFilesList(filePath.toString());
			configHashMap.clear();
			for (String file : files){
				load(file.replace(".json", ""));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**Saves the specified config entry
	 * @param id Config entry identifier
	 */
	private static void save(String id) {
		RecipeBuilder.isExporting = true;
		prepareKitFile(id);

		String jsonString = MelonBTACommands.GSON.toJson(configHashMap.get(id));

		try (FileWriter fileWriter = new FileWriter(fileHashMap.get(id))) {
			fileWriter.write(jsonString);
		} catch (IOException e) {
			System.err.println("Couldn't save MelonBTACommands configuration file");
			e.printStackTrace();
		}
		RecipeBuilder.isExporting = false;
	}

	/**
	 * Saves every config entry
	 */
	@ApiStatus.Internal
	public static void saveAll(){
		for (String id: configHashMap.keySet()) {
			save(id);
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
	public static KitData getConfig(String id) {
		if (configHashMap.get(id) == null){
			{
				configHashMap.put(id, new KitData());
				load(id);

				return configHashMap.get(id);
			}
		}
		return configHashMap.get(id);
	}

	public static int removeKit(String id){
		int error = 2;
		if (!fileHashMap.containsKey(id)) {
			error = 1;
			return error;
		}
		if(fileHashMap.get(id).delete()){
			error = 0;
		}
		fileHashMap.remove(id);
		configHashMap.remove(id);
		return error;
	}


	static{loadAll();}

}
