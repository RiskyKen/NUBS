package andrews.ubs.world.gen;

import java.util.ArrayList;
import java.util.Random;

import andrews.ubs.init.UltimateBlockStormBlocks;
import andrews.ubs.world.biome.BiomeChakraInfused;
import andrews.ubs.world.gen.generators.WorldGenChakraInfusedMushroom;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomePlains;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ChakraInfusedMushroomGen implements IWorldGenerator
{
	public static final WorldGenChakraInfusedMushroom CHAKRAMUSHROOM = new WorldGenChakraInfusedMushroom("chakra_infused_mushroom");
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider)
	{
		switch(world.provider.getDimension())
		{
		case 1:
			
			break;
			
		case 0:
			
			generateChakraInfusedMushroom(CHAKRAMUSHROOM, world, random, chunkX, chunkZ, 1, UltimateBlockStormBlocks.chakra_infused_grass, BiomeChakraInfused.class);
			
			break;
			
		case -1:
		
		}
	}
	
	private void generateChakraInfusedMushroom(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, int chance, Block topBlock, Class<?>... classes)
	{
		
		ArrayList<Class<?>> classesList = new ArrayList<Class<?>>(java.util.Arrays.asList(classes));
		
		int x = (chunkX * 16 + random.nextInt(9) + random.nextInt(15));
		int z = (chunkZ * 16 + random.nextInt(9) + random.nextInt(15));
		int y = calculateGenerationHeight(world, x, z, topBlock) + 1;
		BlockPos pos = new BlockPos(x,y,z);
		
		Class<?> biome = world.provider.getBiomeForCoords(pos).getClass();
		
		if(world.getWorldType() != WorldType.FLAT)
		{
			if(classesList.contains(biome))
			{
				if(random.nextInt(chance) == 0)
				{
					generator.generate(world, random, pos);
				}
			}
		}
	}
	
	private static int calculateGenerationHeight(World world, int x, int z, Block topBlock)
	{
		int y = world.getHeight();
		boolean foundGround = false;
		
		while(!foundGround && y-- >= 0)
		{
			Block block = world.getBlockState(new BlockPos(x,y,z)).getBlock();
			foundGround = block == topBlock;
		}
		
		return y;
	}
}
