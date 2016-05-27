package org.hyperion.hypercon.spec;

import java.util.Vector;

import org.hyperion.hypercon.language.language;

/**
 * Created by Fabian on 02.04.2015.
 */
public final class HyperionRemoteCalls {

    public enum SystemTypes{
        allsystems(language.getString("ssh.systemlist.allsystemsitem")),
        openelec("OpenELEC / LE");

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
        if(type == SystemTypes.allsystems){
            return "hyperion-remote ";
        }else if(type == SystemTypes.openelec){
            return "/storage/hyperion/bin/hyperion-remote.sh ";
        }
        return "";
    }

    public static String getGrabberv4l2CallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "hyperion-v4l2 ";
        }else if(type == SystemTypes.openelec){
            return "/storage/hyperion/bin/hyperion-v4l2.sh ";
        }
        return "";
    }
    public static String getConfigTargetCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "/etc/hyperion/";
        }else if(type == SystemTypes.openelec){
            return "/storage/.config/";
        }
        return "";
    }
   
    public static String getHyperionInstallCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "cd /tmp && wget -nv -N https://raw.github.com/hyperion-project/hyperion/master/bin/install_hyperion.sh && chmod +x install_hyperion.sh && sudo sh ./install_hyperion.sh HyperConInstall ; rm install_hyperion.sh";
        }else if(type == SystemTypes.openelec){
            return "cd /tmp && curl -# -k -L --output install_hyperion.sh --get https://raw.github.com/hyperion-project/hyperion/master/bin/install_hyperion.sh && sh ./install_hyperion.sh HyperConInstall ; rm install_hyperion.sh";
        }
        return "";
    }
    public static String getHyperionRemoveCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "cd /tmp && wget -nv -N https://raw.github.com/hyperion-project/hyperion/master/bin/remove_hyperion.sh && chmod +x remove_hyperion.sh && sudo sh ./remove_hyperion.sh HyperConRemove ; rm remove_hyperion.sh";
        }else if(type == SystemTypes.openelec){
            return "cd /tmp && curl -# -k -L --output remove_hyperion.sh --get https://raw.github.com/hyperion-project/hyperion/master/bin/remove_hyperion.sh && sh ./remove_hyperion.sh HyperConRemove ; rm remove_hyperion.sh";
        }
        return "";
    }
    public static String getHyperionStartServiceCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "sudo systemctl start hyperion.service 2>/dev/null ; sudo /etc/init.d/hyperion start 2>/dev/null ; sudo /sbin/initctl start hyperion 2>/dev/null";
        }else if(type == SystemTypes.openelec){
            return "/storage/.config/autostart.sh > /dev/null 2>&1 &";
        }
        return "";
    }
    public static String getHyperionStopServiceCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "sudo systemctl stop hyperion.service 2>/dev/null; sudo /etc/init.d/hyperion stop 2>/dev/null ; sudo /sbin/initctl stop hyperion 2>/dev/null";
        }else if(type == SystemTypes.openelec){
            return "killall hyperiond 2>/dev/null";
        }
        return "";
    }
    public static String getHyperionLogCallForSystemType(SystemTypes type){
        if(type == SystemTypes.allsystems){
            return "sudo journalctl -u hyperion.service 2>/dev/null";
        }else if(type == SystemTypes.openelec){
            return "cat /storage/logfiles/hyperion.log";
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
