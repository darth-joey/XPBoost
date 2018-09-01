package cz.dubcat.xpboost.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import cz.dubcat.xpboost.Main;
import cz.dubcat.xpboost.api.MainAPI;

public class Signs implements Listener {

	@EventHandler
	public void onSignCreate(SignChangeEvent e) {
		Block block = e.getBlock();
		if (block.getState() instanceof Sign) {

			if (!e.getLine(0).isEmpty()
					&& e.getLine(0).equalsIgnoreCase(Main.getPlugin().getConfig().getString("settings.sign.line1"))) {
				Player player = e.getPlayer();
				if (player.hasPermission("xpboost.admin")) {
					e.setLine(0, MainAPI.colorizeText(Main.getPlugin().getConfig().getString("settings.sign.line1")));
					int i;
					for (i = 2; i < 5; i++) {
						if (!Main.getPlugin().getConfig().getString("settings.sign.line" + i).equals("")) {
							int lel = i - 1;
							e.setLine(lel, MainAPI
									.colorizeText(Main.getPlugin().getConfig().getString("settings.sign.line" + i)));
						}
					}

				} else {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Block block = event.getClickedBlock();
		Player player = event.getPlayer();
		Action action = event.getAction();
		if (action == Action.RIGHT_CLICK_BLOCK && (block.getState().getType() == Material.SIGN_POST
				|| block.getState().getType() == Material.WALL_SIGN)) {
			Sign sign = (Sign) block.getState();
			if (sign.getLine(0).equalsIgnoreCase(
					MainAPI.colorizeText(Main.getPlugin().getConfig().getString("settings.sign.line1")))) {
				if (player.hasPermission("xpboost.use") || player.hasPermission("xpboost.gui")) {
					MainAPI.openXpBoostShop(player);
				}
			}
		}

	}

}
