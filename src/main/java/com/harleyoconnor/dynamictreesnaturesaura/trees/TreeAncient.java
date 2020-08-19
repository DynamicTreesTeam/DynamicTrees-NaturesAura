package com.harleyoconnor.dynamictreesnaturesaura.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockSurfaceRoot;
import com.ferreusveritas.dynamictrees.systems.featuregen.FeatureGenRoots;
import com.ferreusveritas.dynamictrees.trees.Species;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import com.harleyoconnor.dynamictreesnaturesaura.DynamicTreesNaturesAura;
import com.harleyoconnor.dynamictreesnaturesaura.ModContent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

import java.util.List;
import java.util.function.BiFunction;

public class TreeAncient extends TreeFamily {

    public static Block leavesBlock = Block.getBlockFromName("naturesaura:ancient_leaves");
    public static Block logBlock = Block.getBlockFromName("naturesaura:ancient_log");
    public static Block saplingBlock = Block.getBlockFromName("naturesaura:ancient_sapling");

    public class SpeciesAncient extends Species {

        private final int minTrunkRadiusForRoots = 6;

        public SpeciesAncient(TreeFamily treeFamily) {
            super(treeFamily.getName(), treeFamily, ModContent.ancientLeavesProperties);

            setBasicGrowingParameters(0.6f, 24.0f, 3, 4, 0.8f);

            setupStandardSeedDropping();
            generateSeed();
            addGenFeature(new FeatureGenRoots(this.minTrunkRadiusForRoots).setScaler(getRootScaler()));
        }

        protected BiFunction<Integer, Integer, Integer> getRootScaler() {
            return (inRadius, trunkRadius) -> {
                float scale = MathHelper.clamp(trunkRadius >= this.minTrunkRadiusForRoots ? (trunkRadius / 10f) : 0, 0, 1);
                return (int) (inRadius * scale);
            };
        }

        @Override
        public boolean isThick() {
            return true;
        }

        @Override
        public int maxBranchRadius() {
            return 16;
        }
    }

    public TreeAncient() {
        super(new ResourceLocation(DynamicTreesNaturesAura.MODID, "ancient"));

        setPrimitiveLog(logBlock.getDefaultState(), new ItemStack(logBlock, 1, 0));
        ModContent.ancientLeavesProperties.setTree(this);

        ModContent.ancientRoot = new BlockSurfaceRoot(Material.WOOD, getName() + "root");

        addConnectableVanillaLeaves((state) -> state.getBlock() == leavesBlock);
    }

    @Override
    public void createSpecies() {
        setCommonSpecies(new SpeciesAncient(this));
    }

    @Override
    public boolean isThick() {
        return true;
    }

    @Override
    public boolean autoCreateBranch() {
        return true;
    }

    @Override
    public BlockSurfaceRoot getSurfaceRoots() {
        return ModContent.ancientRoot;
    }

    @Override
    public List<Block> getRegisterableBlocks(List<Block> blockList) {
        blockList.add(ModContent.ancientRoot);
        return super.getRegisterableBlocks(blockList);
    }

}
