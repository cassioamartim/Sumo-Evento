package me.flezy.executor;

import me.flezy.Main;
import me.flezy.api.EventManager;
import me.flezy.api.PlayerManager;
import me.flezy.setter.Progress;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Event implements CommandExecutor {


    boolean hasOpen = EventManager.getEvent(Progress.OPEN);
    boolean hasStarted = EventManager.getEvent(Progress.STARTED);
    boolean hasClosed = EventManager.getEvent(Progress.CLOSED);

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        Player player = (((Player) commandSender).getPlayer());
        if (strings.length == 0) {
            if (player.hasPermission("sumo.evento")) {
                player.sendMessage("§aUso correto /evento abrir/fechar");
                return false;
            } else {
                if (hasClosed) {
                    player.sendMessage("§aEvento ja foi iniciado" + EventManager.getEvent(Progress.OPEN));
                } else {
                    player.sendMessage("§cNehum evento estar ativo" + EventManager.getEvent(Progress.OPEN));
                    return false;
                }
            }
            return false;
        }
        switch (strings[0]) {
            case "abrir":

                    //TODO - Ageitar isso, para quando alguem testar abrir mesmo q ja foi iniciado ele vai cancelar
                    // E mandar msg que evento ja estar aberto
                if (!hasOpen) {
                    EventManager.setEvent(Progress.OPEN, true);
                    player.sendMessage("§3§lEVENTO: §aVoce inicio o evento mlg sumo ");
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        player1.sendMessage("§b§lEVENTO§f: §aEvento mlg sumo foi iniciado, /evento entrar");
                    });
                    return false;
                } else {
                    player.sendMessage("§cEvento já estar inciado, digite /evento fechar");
                }
                break;
            case "fechar":
                //TODO - Mesma coisa
                if (!hasClosed) {
                    EventManager.setEvent(Progress.CLOSED, true);
                    player.sendMessage("§aVoce fechou o evento mlg sumo");
                } else {
                    player.sendMessage("§cNao tem evento " + EventManager.getEventActivity());
                }
                break;
            case "set":
                Main.config.setLocation(player.getLocation(), "evento");
                player.sendMessage("§aSpawn do evento setado com sucesso.");
                break;
            case "sair":
                if (PlayerManager.inEvent(player)) {
                    EventManager.leftEvent(player);
                } else {
                    player.sendMessage("§cVoce nao estar nenhum evento." + PlayerManager.getMap().size());
                }
                break;
            case "entrar":
                if (hasOpen || !hasClosed || !hasStarted) {
                    EventManager.joinEvent(player);
                    return false;
                } else if (hasStarted) {
                    player.sendMessage("§3Evento ja foi  iniciado");
                    return false;
                } else {
                    player.sendMessage("§cEvento nao estar aberto");
                    return false;
                }
        }

        return false;
    }
}
