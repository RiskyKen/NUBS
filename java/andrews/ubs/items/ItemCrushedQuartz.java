package andrews.ubs.items;

import andrews.ubs.Reference;
import andrews.ubs.UltimateBlockStormMod;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class ItemCrushedQuartz extends Item {
	
	public ItemCrushedQuartz(String name) {
		super();
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setCreativeTab(UltimateBlockStormMod.instance.itemtab);
	}

}