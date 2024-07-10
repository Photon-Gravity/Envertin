package envertin.type;

import arc.graphics.Color;
import arc.graphics.g2d.Draw;
import arc.math.Mathf;
import arc.math.geom.Point2;
import arc.util.Time;
import mindustry.Vars;
import mindustry.content.Blocks;
import mindustry.content.Fx;
import mindustry.graphics.Pal;
import mindustry.world.Tile;
import mindustry.world.blocks.environment.SteamVent;

import static mindustry.Vars.tilesize;

public class RuinedBuilding extends SteamVent {
	public static Point2[] offsetArray = {
			new Point2(0, 0),
			new Point2(1, 0),
			new Point2(1, 1),
			new Point2(0, 1),
			new Point2(-1, 1),
			new Point2(-1, 0),
			new Point2(-1, -1),
			new Point2(0, -1),
			new Point2(1, -1),
			new Point2(2, -1),
			new Point2(2, 0),
			new Point2(2, 1),
			new Point2(2, 2),
			new Point2(1, 2),
			new Point2(0, 2),
			new Point2(-1, 2),
	};


	public Color effectColor = Pal.vent;
	public float effectSpacing = 15f;

	static{
		for(var p : offsetArray){
			p.sub(1, 1);
		}
	}

	public RuinedBuilding(String name){
		super(name);
		variants = 0;
		effect = Fx.none;
		parent = Blocks.air;
	}

	@Override
	public void drawBase(Tile tile){
		parent.drawBase(tile);

		if(checkAdjacent(tile)){
			Mathf.rand.setSeed(tile.pos());
			Draw.rect(variantRegions[Mathf.randomSeed(tile.pos(), 0, Math.max(0, variantRegions.length - 1))], tile.worldx() - tilesize, tile.worldy() - tilesize);
		}
	}

	@Override
	public boolean updateRender(Tile tile){
		return checkAdjacent(tile);
	}

	@Override
	public void renderUpdate(UpdateRenderState state){
		if(state.tile.block() == Blocks.air && (state.data += Time.delta) >= effectSpacing){
			effect.at(state.tile.x * tilesize - tilesize, state.tile.y * tilesize - tilesize, effectColor);
			state.data = 0f;
		}
	}

	@Override
	public boolean checkAdjacent(Tile tile){
		for(var point : offsetArray){
			Tile other = Vars.world.tile(tile.x + point.x, tile.y + point.y);
			if(other == null || other.floor() != this){
				return false;
			}
		}
		return true;
	}
}
