package envertin.graphics;

import arc.graphics.g2d.Draw;
import arc.graphics.g2d.Fill;
import arc.math.geom.Vec2;
import mindustry.entities.Effect;

import static arc.graphics.g2d.Draw.alpha;
import static arc.graphics.g2d.Draw.color;
import static envertin.util.EnvConstant.s;
import static mindustry.content.Fx.rand;

public class EnvFx {
	public static Vec2 v = new Vec2();
	public static Effect sulfurSmoke = new Effect(36*s, e -> {
		color(EnvPal.sulfuricSmoke);

		float z = Draw.z();
		Draw.z(111);

		rand.setSeed(e.id);
		float len = rand.random(4f) + 44f;

		e.scaled(e.lifetime * rand.random(0.3f, 1f), b -> {
			alpha(b.fout() * 0.8f);
			v.trns(45, len * b.fin());
			Fill.circle(e.x + v.x, e.y + v.y, 3f * b.fin() + 0.5f + Math.min(b.fin() * 8, 1));
		});
		Draw.z(z);
	});
}
