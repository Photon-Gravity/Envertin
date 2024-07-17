package envertin.content;

import arc.audio.Sound;
import mindustry.Vars;

public class EnvertinSounds {
	public static Sound dissolveLoop;
	public static void load(){
		dissolveLoop = Vars.tree.loadSound("dissolveLoop");
	}
}
