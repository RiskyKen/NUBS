package andrews.ubs.items;

import andrews.ubs.Reference;
import andrews.ubs.UltimateBlockStormMod;
import net.minecraft.item.ItemFood;
import net.minecraft.util.ResourceLocation;

public class ItemChakraBerries extends ItemFood 
{
	public ItemChakraBerries(String name) 
	{
		super(1, 2, false);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setCreativeTab(UltimateBlockStormMod.instance.foodtab);
		}
	
}
