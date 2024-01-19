package prophetsama.testing.config;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import net.minecraft.core.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class KitData {
	@SerializedName(value = "Kit Cooldown") @Expose
	public long kitCooldown = 0;
	@SerializedName(value = "Kit Items (Item Stack List)") @Expose
	public List<ItemStack> kitItems = new ArrayList<>();

}
