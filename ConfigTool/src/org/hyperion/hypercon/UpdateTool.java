package org.hyperion.hypercon;

import java.awt.Desktop;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.hyperion.hypercon.language.language;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class UpdateTool {

	private static final String UPDATE_URL = "https://raw.githubusercontent.com/hyperion-project/hypercon/master/update.xml";
	private static final String ACTUAL_VERSION = Main.versionStr;

	public static void main(String[] args) {
		checkForUpdate(UPDATE_URL);
	}

	/**
	 * This function checks for the update, and calls
	 * {@link #getElementFromURL(String)} to catch an {@link Element} from the
	 * given UPDATE_URL String. After that it will check if an update is
	 * available for the current version with {@link #isVersionActual(String)}.
	 * 
	 * If this is the case, then {@link #getElementFromURL(String)} will be
	 * called to open the system web browser that is linked to the http url.
	 * 
	 * If not so, then only a message is actually shown that there is no update
	 * available.
	 * 
	 * @param updateUrl the URL with the well formed xml that holds in the update information for hyperion
	 * 
	 */
	public static void checkForUpdate(String updateUrl) {
		try {

			Element rootElement = getElementFromURL(updateUrl);

			String actualVersion = rootElement.getChildText("actualVersion");
			String versionInfo = rootElement.getChildText("versionInfo");
			String updateURL = rootElement.getChildText("updateURL");

			if (isVersionActual(actualVersion)) {
        	    String message=language.getString("general.versioncheck.newversionmessage") + "\n\n" + language.getString("general.versioncheck.currentversiontitle") +" "+ Main.versionStr + "\n" + language.getString("general.versioncheck.newversiontitle") +" "+ actualVersion + "\n" +  language.getString("general.versioncheck.changelogtitle") +" "+ versionInfo + "\n\n" + language.getString("general.versioncheck.askfordownload");
		        int reply = JOptionPane.showConfirmDialog(new JFrame(), message, language.getString("general.HyperConInformationDialogTitle"),
		        		    JOptionPane.YES_NO_OPTION);
		        	 if (reply == JOptionPane.YES_OPTION) {
		        		 try {
		        			 openWebpage(new URL(updateURL));
		        		 	 System.exit(0); return;
		        		 }finally {
		        			 } 
		        	 } 
			} else {
				System.out.println("UpdateCheck: There is a no new version available");
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e){
			e.printStackTrace(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This function catches the given URL and returns a {@link Element}
	 * 
	 * @param updateUrl The url to get the XML file from web.
	 * 
	 * @return a {@link Element}
	 * 
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private static Element getElementFromURL(String updateUrl)
			throws ParserConfigurationException, SAXException, IOException, MalformedURLException {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(new URL(updateUrl).openStream());
		DOMBuilder builder = new DOMBuilder();
		org.jdom.Document rootDocument = builder.build(doc);
		Element rootElement = rootDocument.getRootElement();
		return rootElement;
	}

	/**
	 * This function compares the parameter updateVersion with the static String
	 * ACTUAL_VERSION. If updateVersion is greater than the ACTUAL_VERSION, true
	 * will be returned, otherwise, the return value is false.
	 * 
	 * @param updateVersion
	 *            the parsed version Info as {@link String}
	 * @return a {@link Boolean}
	 */
	private static boolean isVersionActual(String updateVersion) {
		updateVersion = updateVersion.replace("V", "").replace(".", "");
		Integer updateVersionAsInt = Integer.parseInt(updateVersion);
		String actualVersion = ACTUAL_VERSION.replace("V", "").replace(".", "");
		Integer actualVersionAsInt = Integer.parseInt(actualVersion);
		return updateVersionAsInt > actualVersionAsInt;
	}

	/**
	 * This function opens a given {@link URI} with the System Desktop
	 * functions.
	 * 
	 * @param uri a {@link URI}
	 */
	private static void openWebpage(URI uri) {
		Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
		if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			try {
				desktop.browse(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * This function converts an {@link URL} to an {@link URI} and calls
	 * {@link #openWebpage(URI)}.
	 * 
	 * @param url a {@link URL}
	 */
	private static void openWebpage(URL url) {
		try {
			openWebpage(url.toURI());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
