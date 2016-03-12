package org.hyperion.hypercon.spec;

import java.util.Vector;

/**
 * Created by Fabian on 02.04.2015.
 */
public final class HyperionRemoteCalls {

    public enum SystemTypes{
        raspbian("Raspbian"),
        openelec("OpenElec"),
        kodi("Kodi");

        private final String text;

        private SystemTypes(final String text){
            this.text = text;
        }
        @Override
        public String toString() {
            return text;
        }
    }

    public static String getHyperionRemoteCallForSystemType(SystemTypes type){
        if(type == SystemTypes.raspbian){
            return "hyperion-remote ";
        }else if(type == SystemTypes.openelec || type == SystemTypes.kodi){
            return "/storage/hyperion/bin/hyperion-remote.sh ";
        }
        return "";
    }

    public static String getGrabberv4l2CallForSystemType(SystemTypes type){
        if(type == SystemTypes.raspbian){
            return "hyperion-v4l2 ";
        }else if(type == SystemTypes.openelec || type == SystemTypes.kodi){
            return "hyperion-v4l2 "; //right now there is no grabberv4l2 standard support on openelec, so here is the same call specified, even as it may not work
        }
        return "";
    }
   
    public static String getHyperionInstallCallForSystemType(SystemTypes type){
        if(type == SystemTypes.raspbian){
            return "cd /tmp && wget -nv -N https://raw.github.com/tvdzwan/hyperion/master/bin/install_hyperion.sh && chmod +x install_hyperion.sh && ./install_hyperion.sh HyperConInstall && rm install_hyperion.sh";
        }else if(type == SystemTypes.openelec || type == SystemTypes.kodi){
            return "cd /tmp && curl -# -k -L --output install_hyperion.sh --get https://raw.github.com/tvdzwan/hyperion/master/bin/install_hyperion.sh && sh ./install_hyperion.sh HyperConInstall && rm install_hyperion.sh";
        }
        return "";
    }
    public static String getHyperionRemoveCallForSystemType(SystemTypes type){
        if(type == SystemTypes.raspbian){
            return "cd /tmp && wget -nv -N https://raw.github.com/tvdzwan/hyperion/master/bin/remove_hyperion.sh && chmod +x remove_hyperion.sh && ./remove_hyperion.sh HyperConRemove && rm remove_hyperion.sh";
        }else if(type == SystemTypes.openelec || type == SystemTypes.kodi){
            return "cd /tmp && curl -# -k -L --output remove_hyperion.sh --get https://raw.github.com/tvdzwan/hyperion/master/bin/remove_hyperion.sh && sh ./remove_hyperion.sh HyperConRemove && rm remove_hyperion.sh";
        }
        return "";
    }
    public static Vector<String> getSystemTypesAsVecor(){
        Vector<String> result = new Vector();

        for(SystemTypes type : SystemTypes.values()){
            result.add(type.toString());
        }

        return result;

    }

    public static SystemTypes fromString(String string) throws Exception {

        SystemTypes result = null;

        for(SystemTypes type : SystemTypes.values()) {
            if (type.toString().equals(string)) {
                result = type;
            }
        }
        if(result == null){
            throw new Exception("There is no Systemtype specified with the Name \"" + string + "\"");
        }

        return result;
    }

}
