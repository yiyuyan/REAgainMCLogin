package cn.ksmcbrigade.raml.listeners;

import cn.ksmcbrigade.raml.REAgainMCLogin;
import cn.ksmcbrigade.raml.PlayerLogin;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class OnPlayerConnect {
    public static void listen(ServerPlayerEntity player) {
        PlayerLogin playerLogin = REAgainMCLogin.getPlayer(player);
        playerLogin.setLoggedIn(false);
        player.setInvulnerable(true);
        player.sendMessage(Text.of("§9Welcome to the server, in order to play, you must log in.\n§eLog in using /login and register using /register"), false);
        player.networkHandler.sendPacket(new TitleS2CPacket(Text.of("§aIdentify yourself")));
    }
}
