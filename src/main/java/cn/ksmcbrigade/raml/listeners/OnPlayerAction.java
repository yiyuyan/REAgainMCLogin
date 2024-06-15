package cn.ksmcbrigade.raml.listeners;

import cn.ksmcbrigade.raml.PlayerLogin;
import cn.ksmcbrigade.raml.REAgainMCLogin;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public class OnPlayerAction {
    public static boolean canInteract(ServerPlayNetworkHandler networkHandler) {
        ServerPlayerEntity player = networkHandler.player;
        PlayerLogin playerLogin = REAgainMCLogin.getPlayer(player);
        return playerLogin.isLoggedIn();
    }
}
