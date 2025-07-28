package org.agile.qa.utils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;

import org.agile.qa.setup.ConfigFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathConstants;

import org.xml.sax.SAXException;


public class XmlUtils {
	ConfigFileReader filereader = new ConfigFileReader();
	private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);
	private Document doc;
	private XPath xPath = XPathFactory.newInstance().newXPath();

	public XmlUtils() {
		this.readXml();
	}
	
	private void readXml() {
		
		try {
			File xmlFile = new File(filereader.getValidLoginXMLPath());
			logger.info("File read from " + filereader.getValidLoginXMLPath());
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(xmlFile);
			doc.getDocumentElement().normalize();
			logger.info("XML Document Normalized.");
		} catch (ParserConfigurationException e) {
			logger.error(e.getMessage());
			return;
		} catch (SAXException e) {
			logger.error(e.getMessage());
			return;
		} catch (IOException e) {
			logger.error(e.getMessage());
			return;
		}
	}
	
	public String getAdminUsername() {
		try {
			String username = xPath.evaluate("/LoginDetails/login[@admin='true']/username", doc);
			return username;
		} catch (XPathExpressionException e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public String getAdminPassword() {
		try {
			String username = xPath.evaluate("/LoginDetails/login[@admin='true']/password", doc);
			return username;
		} catch (XPathExpressionException e) {
			logger.error(e.getMessage());
		}
		return "";
	}
	
	public List<String> getAllUsernames() {
		try {
			NodeList nodes = (NodeList) xPath.evaluate(
					"/LoginDetails/login/username",
					doc,
					XPathConstants.NODESET);

	        List<String> usernames = new ArrayList<>();
	        for (int i = 0; i < nodes.getLength(); i++) {
	            usernames.add(nodes.item(i).getTextContent().trim());
	        }
			
			return usernames;
	    } catch (XPathExpressionException e) {
	        logger.error(e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
	public List<String> getAllPasswords() {
		try {
			NodeList nodes = (NodeList) xPath.evaluate(
					"/LoginDetails/login/password",
					doc,
					XPathConstants.NODESET);

	        List<String> passwords = new ArrayList<>();
	        for (int i = 0; i < nodes.getLength(); i++) {
	            passwords.add(nodes.item(i).getTextContent().trim());
	        }
			
			return passwords;
	    } catch (XPathExpressionException e) {
	        logger.error(e.getMessage());
	        return Collections.emptyList();
	    }
	}
	
}