package envertin.graphics;

import arc.graphics.Color;
import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static envertin.util.EnvConstant.*;
import static mindustry.content.Fx.rand;

public class EnvFx {
	public static Vec2 v = new Vec2();
	public static Effect sulfurSmoke = new Effect(12*s, e -> {
		color(Color.black);

		rand.setSeed(e.id);
		for(int i = 0; i < 5; i++){
			float len = rand.random(16f) + 8f;

			e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
				alpha(b.fout());
				v.trns(45, len * b.fin());
				Fill.circle(e.x + v.x, e.y + v.y, 3.6f * b.fin() + 0.6f);
			});
		}
	});
}
