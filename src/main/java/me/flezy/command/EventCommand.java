package me.flezy.command;

import me.flezy.Main;
import me.flezy.event.Event;
import me.flezy.event.manager.EventManager;
import me.flezy.event.stage.EventStage;
import me.flezy.event.type.EventType;
import me.flezy.event.type.list.MLG;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand extends Command {

    private final EventManager manager;

    public EventCommand() {
        super("evento");

        this.manager = Main.getEventManager();
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        boolean hasPermission = sender.hasPermission("sumo.evento");

        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente jogadores podem executar este comando!");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            player.sendMessage("§cUso: /" + label + " <" + (hasPermission ? "abrir/fechar" : "entrar/fechar") + ">");
            return false;
        }

        String search = args[0].toLowerCase();

        if (!hasPermission && !(search.equals("entrar") || search.equals("sair"))) {
            player.sendMessage("§cVocê não tem permissão para executar este comando!");
            return false;
        }

        switch (search) {
            case "abrir": {
                if (manager.hasEvent()) {
                    player.sendMessage("§cJá existe um evento aberto!");
                    return false;
                }

                manager.setEvent(new MLG());

                Bukkit.getOnlinePlayers().forEach(online -> online.sendMessage("§cUm evento de sumo foi iniciado! Use §e/evento entrar§c para entrar!"));
                break;
            }

            case "fechar": {
                if (!manager.hasEvent()) {
                    player.sendMessage("§cNão há nenhum evento em aberto no momento.");
                    return false;
                }

                Event event = manager.getEvent();

                event.getPlayers().forEach(manager::leave);

                manager.setEvent(null);
                break;
            }

            case "entrar": {
                if (!manager.hasEvent()) {
                    player.sendMessage("§cNão há nenhum evento em aberto no momento.");
                    return false;
                }

                Event event = manager.getEvent();

                if (!event.isStage(EventStage.WAITING)) {
                    player.sendMessage("§cO evento já iniciou ;(");
                    return false;
                }

                manager.send(player);
                break;
            }

            case "set": {
                Main.getLocationConfig().setLocation(player.getLocation(), "evento");

                player.sendMessage("§eO spawn do evento foi setado com sucesso!");
                break;
            }

            case "sair": {
                if (!manager.hasEvent()) {
                    player.sendMessage("§cNão há nenhum evento em aberto no momento.");
                    return false;
                }

                Event event = manager.getEvent();

                if (!event.getPlayers().contains(player)) {
                    player.sendMessage("§cVocê não está no evento!");
                    return false;
                }

                manager.leave(player);
                break;
            }
        }

        return false;
    }
}
