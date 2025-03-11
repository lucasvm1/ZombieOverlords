package com.lucasvm.zombieoverlords.events;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.SpawnerBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import com.lucasvm.zombieoverlords.ZombieOverlordsMod;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = "zombieoverlords")
public class SpawnerOverrideEvents {

    private static final Logger LOGGER = LogUtils.getLogger();

    // This map helps track which chunks we've already processed for each player
    // No need to keep modifying the same spawners repeatedly
    private static final Map<UUID, ChunkPos> lastChunkMap = new HashMap<>();

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // Only process on server-side and at the end of the tick
        if (event.phase != TickEvent.Phase.END) return;
        if (!(event.player instanceof ServerPlayer serverPlayer)) return;

        ServerLevel serverLevel = serverPlayer.serverLevel();

        // Figure out which chunk the player is in
        int chunkX = Mth.floor(serverPlayer.getX()) >> 4;
        int chunkZ = Mth.floor(serverPlayer.getZ()) >> 4;
        ChunkPos currentPos = new ChunkPos(chunkX, chunkZ);

        UUID playerId = serverPlayer.getUUID();
        ChunkPos lastPos = lastChunkMap.get(playerId);

        // If the player moved to a new chunk, we need to process it
        if (!currentPos.equals(lastPos)) {
            lastChunkMap.put(playerId, currentPos);

            // Get the chunk (assuming it's loaded)
            LevelChunk chunk = serverLevel.getChunk(currentPos.x, currentPos.z);

            // Check each block entity in the chunk
            chunk.getBlockEntities().forEach((blockPos, blockEntity) -> {
                if (serverLevel.getBlockState(blockPos).getBlock() instanceof SpawnerBlock) {
                    if (blockEntity instanceof SpawnerBlockEntity spawner) {
                        // Here's the magic - convert every spawner to zombie spawners!
                        spawner.setEntityId(EntityType.ZOMBIE, serverLevel.random);

                        // Let the player know if debug messages are enabled
                        if (serverLevel.getGameRules().getBoolean(ZombieOverlordsMod.DEBUG_SPAWNER)) {
                            serverPlayer.sendSystemMessage(
                                    Component.literal("Spawner at " + blockPos + " has been converted to ZOMBIE!")
                            );
                        }

                        LOGGER.info("Converted spawner to ZOMBIE at {} (Chunk: {}) in dimension {} by player {}",
                                blockPos, currentPos, serverLevel.dimension().location(), serverPlayer.getName().getString());
                    }
                }
            });
        }
    }
}