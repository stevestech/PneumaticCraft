package pneumaticCraft.common.event;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.ChunkPosition;
import pneumaticCraft.api.drone.SpecialVariableRetrievalEvent;
import pneumaticCraft.common.entity.living.EntityDrone;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class DroneSpecialVariableHandler{

    @SubscribeEvent
    public void onSpecialVariableRetrieving(SpecialVariableRetrievalEvent event){
        if(event.specialVarName.equalsIgnoreCase("owner")) {
            EntityDrone drone = (EntityDrone)event.drone;
            EntityPlayer player = drone.getOwner();
            if(player != null) event.coordinate = getPosForEntity(player);
        } else if(event.specialVarName.equalsIgnoreCase("drone")) {
            event.coordinate = getPosForEntity(event.drone);
        } else if(event.specialVarName.toLowerCase().startsWith("player=")) {
            EntityPlayer player = MinecraftServer.getServer().getConfigurationManager().func_152612_a(event.specialVarName.substring("player=".length()));
            if(player != null) event.coordinate = getPosForEntity(player);
        }
    }

    private ChunkPosition getPosForEntity(Entity entity){
        int x = (int)Math.floor(entity.posX);
        int y = (int)Math.floor(entity.posY) + 1;
        int z = (int)Math.floor(entity.posZ);
        return new ChunkPosition(x, y, z);
    }

}
