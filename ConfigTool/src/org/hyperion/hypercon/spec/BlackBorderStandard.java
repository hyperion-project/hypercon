package org.hyperion.hypercon.spec;

public enum BlackBorderStandard {
	defaultt("default"),
	classic("classic"),
    osd("osd");

    private final String text;

    private BlackBorderStandard(final String text){
        this.text = text;
    }
    @Override
    public String toString() {
        return text;
    }
}