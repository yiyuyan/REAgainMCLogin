package cn.ksmcbrigade.raml.commands;

import cn.ksmcbrigade.raml.PlayerLogin;
import cn.ksmcbrigade.raml.REAgainMCLogin;
import cn.ksmcbrigade.raml.RegisteredPlayersJson;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;

import java.util.function.Supplier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class LoginCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(literal("login")
                .then(argument("password", StringArgumentType.word())
                    .executes(ctx -> {
                        String password = StringArgumentType.getString(ctx, "password");
                        String username = ctx.getSource().getPlayer().getEntityName();
                        ServerPlayerEntity player = ctx.getSource().getPlayer();

                        if (!RegisteredPlayersJson.isPlayerRegistered(username)) {
                            ctx.getSource().sendFeedback(() -> Text.of("§cYou're not registered! Use /register instead."), false);
                        } else if (RegisteredPlayersJson.isCorrectPassword(username, password)) {
                            PlayerLogin playerLogin = REAgainMCLogin.getPlayer(ctx.getSource().getPlayer());
                            playerLogin.setLoggedIn(true);
                            ctx.getSource().sendFeedback(() -> Text.of("§aLogged in."), false);
                            if (!player.isCreative()) {
                                player.setInvulnerable(false);
                            }
                            player.networkHandler.sendPacket(new PlaySoundS2CPacket(new RegistryEntry.Direct<>(SoundEvents.BLOCK_NOTE_BLOCK_PLING.value()), SoundCategory.MASTER, player.getPos().x,player.getPos().y,player.getPos().z, 100f, 0f,0));
                        } else {
                            player.networkHandler.sendPacket(new PlaySoundS2CPacket(new RegistryEntry.Direct<>(SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR), SoundCategory.MASTER, player.getPos().x,player.getPos().y,player.getPos().z, 100f, 0.5f,0));
                            ctx.getSource().sendFeedback(() -> Text.of("§cIncorrect password!"), false);
                        }
                        return 1;
        })));
    }
}
