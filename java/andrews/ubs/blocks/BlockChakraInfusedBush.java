package andrews.ubs.blocks;

import andrews.ubs.Reference;
import andrews.ubs.UltimateBlockStormMod;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockChakraInfusedBush extends BlockBush implements IPlantable 
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625F, 0F, 0.0625F, 0.0625F * 15F, 0.0625F * 15F, 0.0625F * 15F);
	
	public BlockChakraInfusedBush(String name)
	{
		super(Material.PLANTS);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setSoundType(SoundType.PLANT);
		this.setCreativeTab(UltimateBlockStormMod.instance.blocktab);
		this.useNeighborBrightness = true;
	}
	
//this is used so the block will not render the pixels without a texture black
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
//this is used so we can see the blocks around this one, without rendering problems
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
		
//this is used to get rid of the shadows
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}
	
//this is used to call the bounding box
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}
	
}
