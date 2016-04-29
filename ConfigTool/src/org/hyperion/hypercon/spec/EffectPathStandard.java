package org.hyperion.hypercon.spec;

import org.hyperion.hypercon.language.language;

public enum EffectPathStandard {
	allsystems(language.getString("external.effect.effectpathlist.allsystems")), 
	libreelec(language.getString("external.effect.effectpathlist.libreelec")), 
	openelec(language.getString("external.effect.effectpathlist.openelec"));
	
	private final String mtext;

    private EffectPathStandard(final String name){
        mtext = name;
    }
	/**
	 * Returns the type identifier as used by hyperion effects
	 * @return
	 */
	public String getTypeId() {
		if (this == allsystems) {return "/opt/hyperion/effects";} 
		else if (this == libreelec) {return "/storage/.kodi/addons/service.hyperion/effects";} 
		else if (this == openelec) {return "/storage/hyperion/effects";} 

		return super.name();
	}
    @Override
    public String toString() {
        return mtext;
    }
}
