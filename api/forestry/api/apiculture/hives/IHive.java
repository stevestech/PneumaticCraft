/*******************************************************************************
 * Copyright 2011-2014 SirSengir
 *
 * This work (the API) is licensed under the "MIT" License, see LICENSE.txt for details.
 ******************************************************************************/
package forestry.api.apiculture.hives;

import forestry.api.apiculture.IHiveDrop;
import forestry.api.core.EnumHumidity;
import forestry.api.core.EnumTemperature;
import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.ArrayList;

public interface IHive {
	/**
	 * The hive block to be placed in the world.
	 */
	Block getHiveBlock();
	int getHiveMeta();

	/**
	 * Hive drops
	 */
	ArrayList<IHiveDrop> getDrops();
	void addDrop(IHiveDrop drop);

	/**
	 * returns true if the hive can be generated in these conditions.
	 * Used as a fast early-elimination check for hives that have no hope of spawning in the area.
	 */
	boolean isGoodBiome(BiomeGenBase biome);
	boolean isGoodHumidity(EnumHumidity humidity);
	boolean isGoodTemperature(EnumTemperature temperature);

	/**
	 * returns true if the hive can be generated at this location.
	 * Used for advanced conditions, like checking that the ground below the hive is a certain type.
	 */
	boolean isGoodLocation(World world, int x, int y, int z);

	/**
	 * returns true if the hive can safely replace the block at this location.
	 */
	boolean canReplace(World world, int x, int y, int z);

	/**
	 * float representing the relative chance a hive will generate in a chunk.
	 * Default is 1.0, higher numbers result in more hives, smaller will result in fewer.
	 * Tree hives may want around 3.0 to 4.0 since there are less locations to generate on.
 	 */
	float genChance();

	/**
	 * return a Y value that the hive should try to generate at.
	 * returns negative if the hive can't be placed anywhere.
	 */
	int getYForHive(World world, int x, int z);

	/**
	 * Called after successful hive generation.
	 * world, x, y, z give the location of the new hive.
	 **/
	void postGen(World world, int x, int y, int z);
}
