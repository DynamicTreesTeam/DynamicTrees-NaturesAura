package com.harleyoconnor.dynamictreesnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import de.ellpeck.naturesaura.NaturesAura;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.blocks.tiles.TileEntityAncientLeaves;
import de.ellpeck.naturesaura.reg.IModItem;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockDynamicLeavesAncient extends BlockDynamicLeaves implements IModItem, ITileEntityProvider {

    public BlockDynamicLeavesAncient() {
        setRegistryName(DynamicTreesNaturesAura.MODID, "leaves_ancient");
        setUnlocalizedName("leaves_ancient");
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.PINK;
    }

    @Override
    public String getBaseName() {
        return "leaves_ancient";
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent fmlPreInitializationEvent) {

    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        GameRegistry.registerTileEntity(TileEntityAncientLeaves.class, new ResourceLocation(NaturesAura.MOD_ID, "ancient_leaves"));
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent fmlPostInitializationEvent) {

    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);
        worldIn.removeTileEntity(pos);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileEntityAncientLeaves) {
                if (((TileEntityAncientLeaves) tile).getAuraContainer(null).getStoredAura() <= 0) {
                    worldIn.setBlockState(pos, ModBlocks.DECAYED_LEAVES.getDefaultState());
                }
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, worldIn, pos, rand);
        if (rand.nextFloat() >= 0.95F && !worldIn.getBlockState(pos.down()).isFullBlock()) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile instanceof TileEntityAncientLeaves) {
                if (((TileEntityAncientLeaves) tile).getAuraContainer(null).getStoredAura() > 0) {
                    NaturesAuraAPI.instance().spawnMagicParticle(
                            pos.getX() + rand.nextDouble(), pos.getY(), pos.getZ() + rand.nextDouble(),
                            0F, 0F, 0F, 0xCC4780,
                            rand.nextFloat() * 2F + 0.5F,
                            rand.nextInt(50) + 75,
                            rand.nextFloat() * 0.02F + 0.002F, true, true);

                }
            }
        }
    }

    @Override
    public BlockPlanks.EnumType getWoodType(int meta) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityAncientLeaves();
    }

}
