package com.gildedgames.aether.common.blocks.dungeon;

import com.gildedgames.aether.api.loot.LootGenerator;
import com.gildedgames.aether.common.blocks.util.variants.IBlockVariants;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.BlockVariant;
import com.gildedgames.aether.common.blocks.util.variants.blockstates.PropertyVariant;
import com.gildedgames.aether.common.registry.LootDefinitions;
import com.gildedgames.aether.common.registry.minecraft.SoundsAether;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class BlockLabyrinthContainer extends Block implements IBlockVariants {
	private static final AxisAlignedBB CONTAINER_SMALL_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.6D, 0.8D),
			CONTAINER_LARGE_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.7D, 0.9D);

	public static final BlockVariant
			VARIANT_SMALL = new BlockVariant(0, "small"),
			VARIANT_LARGE = new BlockVariant(1, "large");

	public static final PropertyVariant PROPERTY_VARIANT = PropertyVariant.create("variant", VARIANT_SMALL, VARIANT_LARGE);

	public BlockLabyrinthContainer()
	{
		super(Material.ROCK, MapColor.STONE);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs tab, List<ItemStack> list)
	{
		for (BlockVariant variant : PROPERTY_VARIANT.getAllowedValues())
		{
			list.add(new ItemStack(item, 1, variant.getMeta()));
		}
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	@Override
	public boolean isFullCube(IBlockState state)
	{
		return false;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player)
	{
		world.playSound(player, pos, SoundsAether.break_labyrinth_container, SoundCategory.NEUTRAL, 0.4f, 0.8f + (world.rand.nextFloat() * 0.4f));

		for (int i = 0; i < 12; i++) {
			double x = pos.getX() + world.rand.nextDouble();
			double y = pos.getY() + world.rand.nextDouble();
			double z = pos.getZ() + world.rand.nextDouble();

			world.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, TileEntity te, ItemStack stack)
	{
		if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops"))
		{
			if (world.rand.nextInt(3) == 0)
			{
				int amount = world.rand.nextInt(8) + 3;

				while (amount > 0)
				{
					int split = EntityXPOrb.getXPSplit(amount);

					amount -= split;

					world.spawnEntityInWorld(new EntityXPOrb(world, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, split));
				}
			}
			else
			{
				int count = state.getValue(PROPERTY_VARIANT) == VARIANT_LARGE ? 2 : 1;

				for (int i = 0; i < count; i++)
				{
					ItemStack loot = LootGenerator.generate(LootDefinitions.LABYRINTH_TRASH, world.rand);

					Block.spawnAsEntity(world, pos, loot);
				}
			}
		}
	}

	@Override
	public boolean canHarvestBlock(IBlockAccess world, BlockPos pos, EntityPlayer player)
	{
		return true;
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		BlockVariant variant = state.getValue(PROPERTY_VARIANT);

		return variant == VARIANT_LARGE ? CONTAINER_LARGE_AABB : CONTAINER_SMALL_AABB;
	}

	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		BlockVariant variant = PROPERTY_VARIANT.fromMeta(meta & 7);

		return this.getDefaultState().withProperty(PROPERTY_VARIANT, variant);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return state.getValue(PROPERTY_VARIANT).getMeta();
	}

	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, PROPERTY_VARIANT);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return PROPERTY_VARIANT.fromMeta(stack.getMetadata()).getName();
	}
}
