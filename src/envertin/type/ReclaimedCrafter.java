package envertin.type;

import mindustry.game.Team;
import mindustry.world.Tile;
import mindustry.world.blocks.production.AttributeCrafter;

public class ReclaimedCrafter extends AttributeCrafter {
	public ReclaimedCrafter(String name) {
		super(name);
	}

	@Override
	public boolean canPlaceOn(Tile tile, Team team, int rotation) {
		return super.canPlaceOn(tile, team, rotation);
	}
}
