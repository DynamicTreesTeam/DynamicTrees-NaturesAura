package com.harleyoconnor.dynamictreesnaturesaura.blocks;

import com.ferreusveritas.dynamictrees.blocks.BlockDynamicLeaves;
import com.ferreusveritas.dynamictrees.trees.TreeOak;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockDynamicLeavesGolden extends BlockDynamicLeaves {

    public static final int HIGHEST_STAGE = 3;
    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, HIGHEST_STAGE);

    public BlockDynamicLeavesGolden() {
        super();
        setRegistryName(DynamicTreesNaturesAura.MODID, "leaves_golden");
        setUnlocalizedName("leaves_golden");
    }

    @Override
    public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return MapColor.GOLD;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (stateIn.getValue(STAGE) == HIGHEST_STAGE && rand.nextFloat() >= 0.75F)
            NaturesAuraAPI.instance().spawnMagicParticle(
                    pos.getX() + rand.nextFloat(),
                    pos.getY() + rand.nextFloat(),
                    pos.getZ() + rand.nextFloat(),
                    0F, 0F, 0F,
                    0xF2FF00, 0.5F + rand.nextFloat(), 50, 0F, false, true);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HYDRO, TREE, DECAYABLE, STAGE});
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TREE, (meta >> 2) & 3).withProperty(HYDRO, (meta & 3) + 1).withProperty(STAGE, meta & HIGHEST_STAGE);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(HYDRO) - 1) | (state.getValue(TREE) << 2) | state.getValue(STAGE);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(worldIn, pos, state, rand);

        if (worldIn.isRemote) return;

        int stage = state.getValue(STAGE);
        if (stage < HIGHEST_STAGE) {
            worldIn.setBlockState(pos, state.withProperty(STAGE, stage + 1));
        }

        if (stage > 1) {
            BlockPos offset = pos.offset(EnumFacing.random(rand));
            if (worldIn.isBlockLoaded(offset))
                convert(worldIn, offset);
        }
    }

    public static boolean convert(World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() instanceof BlockDynamicLeaves) {
            if (((BlockDynamicLeaves) state.getBlock()).getProperties(state).getTree() instanceof TreeOak && !(state.getBlock() instanceof BlockDynamicLeavesGolden)) {
                if (!world.isRemote) world.setBlockState(pos, ModContent.goldenLeaves.getDefaultState()
                        .withProperty(DECAYABLE, state.getPropertyKeys().contains(DECAYABLE) ? state.getValue(DECAYABLE) : false));
                return true;
            }
        }
        return false;
    }

}