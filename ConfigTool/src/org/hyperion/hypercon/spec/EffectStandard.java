package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.language.language;

public enum EffectStandard {
	none(language.getString("external.effect.booteffectlist.None")), //$NON-NLS-1$
	knightrider(language.getString("external.effect.booteffectlist.Knight_Rider")), //$NON-NLS-1$
	snake(language.getString("external.effect.booteffectlist.Snake")), //$NON-NLS-1$
	runningdots(language.getString("external.effect.booteffectlist.Running_dots")), //$NON-NLS-1$
	xmas(language.getString("external.effect.booteffectlist.X_mas")), //$NON-NLS-1$
	random(language.getString("external.effect.booteffectlist.Random")), //$NON-NLS-1$
	systemshutdown(language.getString("external.effect.booteffectlist.System_Shutdown")), //$NON-NLS-1$
	traces(language.getString("external.effect.booteffectlist.Color_traces")), //$NON-NLS-1$
	cinemabright(language.getString("external.effect.booteffectlist.Cinema_brighten_lights")), //$NON-NLS-1$
	cinemadim(language.getString("external.effect.booteffectlist.Cinema_dim_lights")), //$NON-NLS-1$
	sparks(language.getString("external.effect.booteffectlist.Sparks")), //$NON-NLS-1$
	sparkscolor(language.getString("external.effect.booteffectlist.Sparks_Colors")), //$NON-NLS-1$
	policelightssingle(language.getString("external.effect.booteffectlist.Police_lights_single")), //$NON-NLS-1$
	policelightssolid(language.getString("external.effect.booteffectlist.Police_lights_solid")), //$NON-NLS-1$
	rainbowswirlfast(language.getString("external.effect.booteffectlist.Rainbow_swirl_fast")), //$NON-NLS-1$
	rainbowswirl(language.getString("external.effect.booteffectlist.Rainbow_swirl")), //$NON-NLS-1$
	rainbowmood(language.getString("external.effect.booteffectlist.Rainbow_mood")), //$NON-NLS-1$
	moodblobsblue(language.getString("external.effect.booteffectlist.Blue_mood_blobs")), //$NON-NLS-1$
	moodblobscold(language.getString("external.effect.booteffectlist.Cold_mood_blobs")), //$NON-NLS-1$
	moodblobsfull(language.getString("external.effect.booteffectlist.Full_color_mood_blobs")), //$NON-NLS-1$
	moodblobsgreen(language.getString("external.effect.booteffectlist.Green_mood_blobs")), //$NON-NLS-1$
	moodblobsred(language.getString("external.effect.booteffectlist.Red_mood_blobs")), //$NON-NLS-1$
	moodblobswarm(language.getString("external.effect.booteffectlist.Warm_mood_blobs")), //$NON-NLS-1$
	strobeblue(language.getString("external.effect.booteffectlist.Strobe_blue")), //$NON-NLS-1$
	stroberaspbmc(language.getString("external.effect.booteffectlist.Strobe_raspbmc")), //$NON-NLS-1$
	strobewhite(language.getString("external.effect.booteffectlist.Strobe_white")); //$NON-NLS-1$
	
	private final String mtext;

    private EffectStandard(final String name){
        mtext = name;
    }
	/**
	 * Returns the type identifier as used by hyperion effects
	 * @return
	 */
	public String getTypeId() {
		if (this == none) {return "";} //$NON-NLS-1$
		else if (this == knightrider) {return "Knight rider";} //$NON-NLS-1$
		else if (this == snake) {return "Snake";} //$NON-NLS-1$
		else if (this == runningdots) {return "Running dots";} //$NON-NLS-1$
		else if (this == xmas) {return "X-Mas";} //$NON-NLS-1$
		else if (this == random) {return "Random";} //$NON-NLS-1$
		else if (this == systemshutdown) {return "System Shutdown";} //$NON-NLS-1$
		else if (this == traces) {return "Color traces";} //$NON-NLS-1$
		else if (this == cinemabright) {return "Cinema brighten lights";} //$NON-NLS-1$
		else if (this == cinemadim) {return "Cinema dim lights";} //$NON-NLS-1$
		else if (this == sparks) {return "Sparks";} //$NON-NLS-1$
		else if (this == sparkscolor) {return "Sparks Color";} //$NON-NLS-1$
		else if (this == policelightssingle) {return "Police Lights Single";} //$NON-NLS-1$
		else if (this == policelightssolid) {return "Police Lights Solid";} //$NON-NLS-1$
		else if (this == rainbowswirlfast) {return "Rainbow swirl fast";} //$NON-NLS-1$
		else if (this == rainbowswirl) {return "Rainbow swirl";} //$NON-NLS-1$
		else if (this == rainbowmood) {return "Rainbow mood";} //$NON-NLS-1$
		else if (this == moodblobsblue) {return "Blue mood blobs";} //$NON-NLS-1$
		else if (this == moodblobscold) {return "Cold mood blobs";} //$NON-NLS-1$
		else if (this == moodblobsfull) {return "Full color mood blobs";} //$NON-NLS-1$
		else if (this == moodblobsgreen) {return "Green mood blobs";} //$NON-NLS-1$
		else if (this == moodblobsred) {return "Red mood blobs";} //$NON-NLS-1$
		else if (this == moodblobswarm) {return "Warm mood blobs";} //$NON-NLS-1$
		else if (this == strobeblue) {return "Strobe blue";} //$NON-NLS-1$
		else if (this == stroberaspbmc) {return "Strobe Raspbmc";} //$NON-NLS-1$
		else if (this == strobewhite) {return "Strobe white";} //$NON-NLS-1$
		return super.name();
	}
    @Override
    public String toString() {
        return mtext;
    }
}