package org.hyperion.hypercon;


import javax.swing.*;
import java.awt.*;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Created by Fabian on 15.02.2015.
 */
public class ErrorHandling {


    public static JFrame mainframe;


    public static void ShowException(Exception e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        String exceptionAsString = sw.toString();

        JOptionPane.showMessageDialog(mainframe, e.getMessage() + "\n\n" + exceptionAsString, "Error", JOptionPane.OK_OPTION); //$NON-NLS-1$ //$NON-NLS-2$

    }

    public static void ShowMessage(String Message){

        JOptionPane.showMessageDialog(mainframe,Message, "Info", JOptionPane.OK_OPTION); //$NON-NLS-1$

    }

    public static void ShowMessage(String Message, String title){

        JOptionPane.showMessageDialog(mainframe,Message, title, JOptionPane.OK_OPTION);

    }



}
