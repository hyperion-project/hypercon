package org.hyperion.hypercon.spec;

public enum EffectStandard {
	none("None"),
	knightrider("Knight rider"),
	snake("Snake"),
	runningdots("Running dots"),
	xmas("X-Mas"),
	random("Random"),
	systemshutdown("System Shutdown"),
	traces("Color traces"),
	cinemabright("Cinema brighten lights"),
	cinemadim("Cinema dim lights"),
	sparks("Sparks"),
	sparkscolor("Sparks Color"),
	policelightssingle("Police Lights Single"),
	policelightssolid("Police Lights Solid"),
	rainbowswirlfast("Rainbow swirl fast"),
	rainbowswirl("Rainbow swirl"),
	rainbowmood("Rainbow mood"),
	moodblobsblue("Blue mood blobs"),
	moodblobscold("Cold mood blobs"),
	moodblobsfull("Full color mood blobs"),
	moodblobsgreen("Green mood blobs"),
	moodblobsred("Red mood blobs"),
	moodblobswarm("Warm mood blobs"),
	strobeblue("Strobe blue"),
	stroberaspbmc("Strobe Raspbmc"),
	strobewhite("Strobe white"),
	udplistener("UDP listener");

	
	private final String text;

    private EffectStandard(final String text){
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}