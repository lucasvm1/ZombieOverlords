package com.lucasvm.zombieoverlords;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

@Mod("zombieoverlords")
public class ZombieOverlordsMod {
    private static final Logger LOGGER = LogUtils.getLogger();

    // My custom gamerule to toggle spawner debug messages - defaults to false
    public static final GameRules.Key<GameRules.BooleanValue> DEBUG_SPAWNER =
            GameRules.register("debugSpawner", GameRules.Category.MISC, GameRules.BooleanValue.create(false));

    public ZombieOverlordsMod() {
        LOGGER.info("Initializing Zombie Overlords Mod - Prepare for the zombie apocalypse!");
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("Setting up Zombie Overlords: Using default spawn rules since I'm controlling spawning with another mod");
        event.enqueueWork(() -> {
            // Registering zombie spawn placement using vanilla rules
            // I could modify these but I'm letting another mod handle that part
            SpawnPlacements.register(
                    EntityType.ZOMBIE,
                    SpawnPlacements.Type.ON_GROUND,
                    Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                    ZombieOverlordsMod::customZombieSpawnRule
            );
        });
    }

    public static boolean customZombieSpawnRule(
            EntityType<? extends Monster> type,
            ServerLevelAccessor world,
            MobSpawnType reason,
            BlockPos pos,
            RandomSource random
    ) {
        // Using vanilla spawn rules - I could customize this later if needed
        return Monster.checkMonsterSpawnRules(type, world, reason, pos, random);
    }
}