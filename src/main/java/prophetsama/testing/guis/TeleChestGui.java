package prophetsama.testing.guis;

import net.minecraft.core.InventoryAction;
import net.minecraft.core.entity.player.EntityPlayer;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.IInventory;
import net.minecraft.core.player.inventory.slot.Slot;

import java.util.List;

public class TeleChestGui extends Container {

	public TeleChestGui(IInventory inv, int numberOfRows) {
		for (int j = 0; j < numberOfRows; ++j) {
			for (int i1 = 0; i1 < 9; ++i1) {
				this.addSlot(new Slot(inv, i1 + j * 9, 8 + i1 * 18, 18 + j * 18));
			}
		}
	}

	@Override
	public List<Integer> getMoveSlots(InventoryAction action, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public List<Integer> getTargetSlots(InventoryAction inventoryAction, Slot slot, int i, EntityPlayer entityPlayer) {
		return null;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
		return false;
	}
}
