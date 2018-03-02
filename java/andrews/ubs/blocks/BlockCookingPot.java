package andrews.ubs.blocks;

import java.util.Random;

import andrews.ubs.Reference;
import andrews.ubs.UltimateBlockStormMod;
import andrews.ubs.init.UltimateBlockStormBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockCookingPot extends Block implements ITileEntityProvider
{
	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyBool BURNING = PropertyBool.create("burning");
	private static boolean keepInventory;
	
	public BlockCookingPot(String name)
	{
		super(Material.ROCK);
		this.setUnlocalizedName(name);
		this.setRegistryName(new ResourceLocation(Reference.MODID, name));
		this.setCreativeTab(UltimateBlockStormMod.instance.blocktab);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BURNING, false));
	}
	
//Called when block is added in world
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state)
	{
	    this.setDefaultFacing(worldIn, pos, state);
	}
		
	private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

	
//Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }
    
//Called by ItemBlocks after a block is set in the world, to allow post-place logic
	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
	{
		 worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

	        if (stack.hasDisplayName())
	        {
	            TileEntity tileentity = worldIn.getTileEntity(pos);

	            if (tileentity instanceof TileEntityFurnace)
	            {
	                ((TileEntityFurnace)tileentity).setCustomInventoryName(stack.getDisplayName());
	            }
	        }
	    }

//Convert the given metadata into a BlockState for this Block
	public IBlockState getStateFromMeta(int meta)
    {
       EnumFacing enumfacing = EnumFacing.getFront(meta);

       if (enumfacing.getAxis() == EnumFacing.Axis.Y)
       {
           enumfacing = EnumFacing.NORTH;
       }

       return this.getDefaultState().withProperty(FACING, enumfacing);
    }

//Convert the BlockState into the correct metadata value
   public int getMetaFromState(IBlockState state)
   {
       return ((EnumFacing)state.getValue(FACING)).getIndex();
   }
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot)
	{
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) 
	{
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {BURNING,FACING});
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

//This is used so the Gui opens when the Block is activated
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ)
	{
		if(!worldIn.isRemote)
		{
			playerIn.openGui(UltimateBlockStormMod.instance, Reference.COOKING_POT, worldIn, pos.getX(), pos.getY(), pos.getZ());
		}
		
		return true;
	}
	
//The Item The Block Drops
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		return Item.getItemFromBlock(UltimateBlockStormBlocks.cooking_pot);
	}
	
	@Override
	public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) 
	{
		return new ItemStack(UltimateBlockStormBlocks.cooking_pot);
	}
	
	public static void setState(boolean active, World worldIn, BlockPos pos) 
	{
		IBlockState state = worldIn.getBlockState(pos);
		TileEntity tileentity = worldIn.getTileEntity(pos);
		
		if(active)
		{
			worldIn.setBlockState(pos, UltimateBlockStormBlocks.cooking_pot.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, true), 3);
		}
		else 
		{
			worldIn.setBlockState(pos, UltimateBlockStormBlocks.cooking_pot.getDefaultState().withProperty(FACING, state.getValue(FACING)).withProperty(BURNING, false), 3);
		}
		
		if(tileentity != null) 
		{
			tileentity.validate();
			worldIn.setTileEntity(pos, tileentity);
		}
	}
	
	public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityFurnace();
    }

//Called serverside after this block is replaced with another in Chunk, but before the Tile Entity is updated
   public void breakBlock(World worldIn, BlockPos pos, IBlockState state)
   {
       if (!keepInventory)
       {
           TileEntity tileentity = worldIn.getTileEntity(pos);

           if (tileentity instanceof TileEntityFurnace)
           {
               InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityFurnace)tileentity);
           }
       }

       super.breakBlock(worldIn, pos, state);
   }

//The type of render function called. MODEL for mixed tesr and static model, MODELBLOCK_ANIMATED for TESR-only, LIQUID for vanilla liquids, INVISIBLE to skip all rendering
   public EnumBlockRenderType getRenderType(IBlockState state)
   {
       return EnumBlockRenderType.MODEL;
   }

}
