package org.hyperion.hypercon;

public class JavaVersion
		{
		    //specifies minimum major version. Examples: 5 (JRE 5), 6 (JRE 6), 7 (JRE 7) etc.
		    private static final int MAJOR_VERSION = 7;

		    //specifies minimum minor version. Examples: 12 (JRE 6u12), 23 (JRE 6u23), 2 (JRE 7u2) etc.
		    private static final int MINOR_VERSION = 1;

		    //checks if the version of the currently running JVM is bigger than
		    //the minimum version required to run this program.
		    //returns true if it's ok, false otherwise
		    public static boolean isOKJVMVersion()
		    {
		        //get the JVM version
		        String version = System.getProperty("java.version");

		        //extract the major version from it
		        int sys_major_version;
		        try
		        {
		        	sys_major_version = Integer.parseInt(String.valueOf(version.charAt (2)));
		        }
		        catch(Exception e) {
		        	sys_major_version = 9;
		        }

		        //if the major version is too low (unlikely !!), it's not good
		        if (sys_major_version < MAJOR_VERSION) {
		            return false;
		        } else if (sys_major_version > MAJOR_VERSION) {
		            return true;
		        } else {
		            //find the underline ( "_" ) in the version string
		            int underlinepos = version.lastIndexOf("_");

		            try {
		                //everything after the underline is the minor version.
		                //extract that
		                int mv = Integer.parseInt(version.substring(underlinepos + 1));

		                //if the minor version passes, wonderful
		                return (mv >= MINOR_VERSION);

		            } catch (NumberFormatException e) {
		                //if it's not ok, then the version is probably not good
		                return false;
		            }
		        }
		    }
}