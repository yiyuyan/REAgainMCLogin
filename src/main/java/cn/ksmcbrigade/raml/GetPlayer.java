package cn.ksmcbrigade.raml;

import net.minecraft.server.network.ServerPlayerEntity;
import cn.ksmcbrigade.raml.PlayerLogin;
import java.util.HashMap;
import java.util.UUID;

public class GetPlayer extends HashMap<UUID, PlayerLogin> {
    public PlayerLogin get(ServerPlayerEntity player) {
        UUID uuid = player.getUuid();
        if (containsKey(uuid)) {
            return super.get(uuid);
        }
        PlayerLogin newPlayer = new PlayerLogin(player);
        put(uuid, newPlayer);
        return newPlayer;
    }
}
