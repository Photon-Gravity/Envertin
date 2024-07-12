package envertin.type;

import arc.Core;
import arc.graphics.g2d.Draw;
import arc.graphics.g2d.TextureRegion;
import arc.math.Mathf;
import arc.math.geom.Geometry;
import arc.struct.Seq;
import mindustry.Vars;
import mindustry.core.Renderer;
import mindustry.gen.Building;
import mindustry.graphics.Drawf;
import mindustry.graphics.Layer;
import mindustry.graphics.Pal;
import mindustry.world.blocks.power.Battery;
import mindustry.world.blocks.power.BeamNode;
import mindustry.world.blocks.power.PowerGraph;
import mindustry.world.blocks.power.PowerNode;

import java.util.Arrays;

import static mindustry.Vars.tilesize;
import static mindustry.Vars.world;

public class RaycastPylon extends BeamNode {
	TextureRegion[] directionRegions;

	@Override
	public void load() {
		super.load();
		directionRegions = new TextureRegion[2];
		directionRegions[0] = Core.atlas.find(name + "-direction1");
		directionRegions[1] = Core.atlas.find(name + "-direction2");
	}

	public RaycastPylon(String name) {
		super(name);
		rotate = true;
	}

	@Override
	protected TextureRegion[] icons() {
		TextureRegion[] icons = super.icons();
		TextureRegion[] out = new TextureRegion[icons.length+1];
		System.arraycopy(icons, 0, out, 0, icons.length);
		out[out.length-1] = directionRegions[0];

		return out;
	}

	@Override
	public void drawPlace(int x, int y, int rotation, boolean valid){
			int maxLen = range + size/2;
			Building dest = null;
			Seq<Building> pierced = new Seq<>();
			var dir = Geometry.d4[rotation];
			int dx = dir.x, dy = dir.y;
			int offset = size/2;
			for(int j = 1 + offset; j <= range + offset; j++){
				var other = world.build(x + j * dir.x, y + j * dir.y);

				//hit insulated wall
				if(other != null && other.isInsulated()){
					break;
				}

				if(other != null && other.block.hasPower && other.team == Vars.player.team() && !(other.block instanceof PowerNode)){
					pierced.add(other);
					if(other instanceof RaycastPylonBuild || other instanceof Battery.BatteryBuild){
						maxLen = j;
						dest = other;
						break;
					}
				}
			}

			Drawf.dashLine(Pal.placing,
					x * tilesize + dx * (tilesize * size / 2f + 2),
					y * tilesize + dy * (tilesize * size / 2f + 2),
					x * tilesize + dx * (maxLen) * tilesize,
					y * tilesize + dy * (maxLen) * tilesize
			);

			if(dest != null){
				for(int i = 0; i < pierced.size; i++){
					Drawf.square(pierced.get(i).x, pierced.get(i).y, pierced.get(i).block.size * tilesize/2f + 2.5f, 0f);
				}
				Drawf.square(dest.x, dest.y, dest.block.size * tilesize/2f + 2.5f, 0f);
			}
	}
	public class RaycastPylonBuild extends BeamNodeBuild{
		Seq<Building> pierced = new Seq<>();
		int lastRotation = -1;
		public Building getLink(){
			return this.links[rotation];
		}

		@Override
		public void draw() {

			Draw.rect(block.region, x, y);

			Draw.rect(rotation < 2 ? ((RaycastPylon)block).directionRegions[0] : ((RaycastPylon)block).directionRegions[1], x, y, rotation * 90);

			drawTeamTop();

			if(Mathf.zero(Renderer.laserOpacity)) return;

			Draw.z(Layer.power);
			Draw.color(laserColor1, laserColor2, (1f - power.graph.getSatisfaction()) * 0.86f + Mathf.absin(3f, 0.1f));
			Draw.alpha(Renderer.laserOpacity);
			float w = laserWidth + Mathf.absin(pulseScl, pulseMag);
			if(dests[rotation] != null && links[rotation].wasVisible && (!(links[rotation] instanceof RaycastPylonBuild && ((RaycastPylonBuild)links[rotation]).getLink() == this) || links[rotation].id < id)){
				int dst = Math.max(Math.abs(dests[rotation].x - tile.x),  Math.abs(dests[rotation].y - tile.y));

				//don't draw lasers for adjacent blocks
				if(dst > 1 + size/2){
					var point = Geometry.d4[rotation];
					float poff = tilesize/2f;
					Drawf.laser(laser, laserEnd, x + poff*size*point.x, y + poff*size*point.y, dests[rotation].worldx() - poff*point.x, dests[rotation].worldy() - poff*point.y, w);
				}
			}

			Draw.reset();
		}

		@Override
		public void pickedUp(){
			super.pickedUp();
			pierced = new Seq<>();
		}

		@Override
		public void updateTile(){
			//TODO this block technically does not need to update every frame, perhaps put it in a special list.
			if(lastChange != world.tileChanges || lastRotation != rotation){
				lastChange = world.tileChanges;
				lastRotation = rotation;
				updateDirections();
			}

		}

		@Override
		public void updateDirections(){

				var prev = links[rotation];
				var prevPierced = pierced;
				var dir = Geometry.d4[rotation];
				Arrays.fill(links, null);
				Arrays.fill(dests, null);
				pierced = new Seq<>();
				links[rotation] = null;
				dests[rotation] = null;
				int offset = size/2;

				//find first block with power in range
				for(int j = 1 + offset; j <= range + offset; j++){
					var other = world.build(tile.x + j * dir.x, tile.y + j * dir.y);

					//hit insulated wall
					if(other != null && other.isInsulated()){
						break;
					}

					//power nodes do NOT play nice with beam nodes, do not touch them as that forcefully modifies their links
					if(other != null && other.block.hasPower && other.block.connectedPower && other.team == team && !(other.block instanceof PowerNode)){
						pierced.add(other);
						if(other instanceof RaycastPylonBuild || other instanceof Battery.BatteryBuild){
							links[rotation] = other;
							dests[rotation] = world.tile(tile.x + j * dir.x, tile.y + j * dir.y);
							pierced.remove(other);
							break;
						}
					}
				}

				var next = links[rotation];

				boolean pierceChange = prevPierced.size != pierced.size;
				if(!pierceChange){
					for(int i = 0; i < pierced.size;i++){
						if(pierced.get(i) == prevPierced.get(i)){
							pierceChange = true;
							break;
						}
					}
				}

				if(next != prev || pierceChange){
					//unlinked, disconnect and reflow
					if(prev != null){
						prev.power.links.removeValue(pos());
						power.links.removeValue(prev.pos());

						PowerGraph newgraph = new PowerGraph();
						//reflow from this point, covering all tiles on this side
						newgraph.reflow(this);

						if(prev.power.graph != newgraph){
							//reflow power for other end
							PowerGraph og = new PowerGraph();
							og.reflow(prev);
						}
					}
					if(prevPierced != null){
						for(int i = 0; i < prevPierced.size; i++){
							if(prevPierced.get(i) != null){
								prevPierced.get(i).power.links.removeValue(pos());
								power.links.removeValue(prevPierced.get(i).pos());

								PowerGraph newgraph = new PowerGraph();
								//reflow from this point, covering all tiles on this side
								newgraph.reflow(this);

								if(prevPierced.get(i).power.graph != newgraph){
									//reflow power for other end
									PowerGraph og = new PowerGraph();
									og.reflow(prevPierced.get(i));
								}
							}
						}
					}


					//linked to a new one, connect graphs
					if(next != null){
						power.links.addUnique(next.pos());
						next.power.links.addUnique(pos());

						power.graph.addGraph(next.power.graph);
						if(pierced != null ) {
							for (int i = 0; i < pierced.size; i++) {
								if(pierced.get(i) != null){
									power.links.addUnique(pierced.get(i).pos());
									pierced.get(i).power.links.addUnique(pos());

									power.graph.addGraph(pierced.get(i).power.graph);
								}
							}
						}
					}
				}
		}
	}
}
