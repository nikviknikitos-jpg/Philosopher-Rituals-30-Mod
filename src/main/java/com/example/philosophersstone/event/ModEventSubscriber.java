package com.example.philosophersstone.event;

import com.example.philosophersstone.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

/**
 * Логика трансмутации: ПКМ философским камнем по блоку — превращает один материал в другой.
 */
@Mod.EventBusSubscriber
public class ModEventSubscriber {

    private static final Map<Block, Block> TRANSMUTATION_MAP = new HashMap<>();

    static {
        // Камень -> золото (алхимия!)
        TRANSMUTATION_MAP.put(Blocks.STONE, Blocks.GOLD_ORE);
        TRANSMUTATION_MAP.put(Blocks.COBBLESTONE, Blocks.RAW_GOLD_BLOCK);
        TRANSMUTATION_MAP.put(Blocks.DEEPSLATE, Blocks.DEEPSLATE_GOLD_ORE);
        TRANSMUTATION_MAP.put(Blocks.NETHERRACK, Blocks.GLOWSTONE);
        TRANSMUTATION_MAP.put(Blocks.DIRT, Blocks.CLAY);
        TRANSMUTATION_MAP.put(Blocks.SAND, Blocks.GLASS);
        // Железо -> золото
        TRANSMUTATION_MAP.put(Blocks.IRON_ORE, Blocks.GOLD_ORE);
        TRANSMUTATION_MAP.put(Blocks.DIORITE, Blocks.QUARTZ_BLOCK);
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack heldItem = event.getItemStack();

        if (!heldItem.is(ModItems.PHILOSOPHERS_STONE.get())) {
            return;
        }

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block result = TRANSMUTATION_MAP.get(state.getBlock());

        if (result == null) {
            return; // этот блок трансмутировать нельзя
        }

        event.setCanceled(true);
        if (level.isClientSide) {
            return;
        }

        if (!player.getAbilities().instabuild) {
            int damage = heldItem.getDamageValue() + 1;
            if (damage >= 30) {
                heldItem.shrink(1);
            } else {
                heldItem.setDamageValue(damage);
            }
        }

        level.setBlockAndUpdate(pos, result.defaultBlockState());
        level.playSound(null, pos, SoundEvents.ANVIL_USE, SoundSource.BLOCKS, 1.0F, 1.8F);

        // Вспышка искр (только на клиенте)
        if (level instanceof net.minecraft.server.level.ServerLevel serverLevel) {
            for (int i = 0; i < 5; i++) {
                serverLevel.sendParticles(net.minecraft.core.particles.ParticleTypes.ELECTRIC_SPARK,
                        pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        4, 0.4, 0.4, 0.4, 0.0);
            }
        }
    }

    /**
     * ПКМ по воздуху: 4 эссенции трансмутации -> 1 золотой самородок.
     */
    @SubscribeEvent
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        Level level = event.getLevel();
        Player player = event.getEntity();
        ItemStack heldItem = event.getItemStack();

        if (heldItem.is(ModItems.TRANSMUTATION_ESSENCE.get()) && heldItem.getCount() >= 4) {
            if (!level.isClientSide) {
                heldItem.shrink(4);
                player.addItem(new ItemStack(Items.GOLD_NUGGET, 1));
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 1.0F, 1.2F);
            }
            event.setCanceled(true);
        }
    }
}
