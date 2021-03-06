/**
 *                    BioJava development code
 *
 * This code may be freely distributed and modified under the
 * terms of the GNU Lesser General Public Licence.  This should
 * be distributed with the code.  If you do not have a copy,
 * see:
 *
 *      http://www.gnu.org/copyleft/lesser.html
 *
 * Copyright for this code is held jointly by the individual
 * authors.  These should be listed in @author doc comments.
 *
 * For more information on the BioJava project and its aims,
 * or to join the biojava-l mailing list, visit the home page
 * at:
 *
 *      http://www.biojava.org/
 *
 * Created on Aug 30, 2011
 * Created by Andreas Prlic
 *
 * @since 3.0.2
 */
package org.biojava.nbio.structure.scop.server;

import org.biojava.nbio.structure.domain.pdp.Domain;
import org.biojava.nbio.structure.scop.ScopDescription;
import org.biojava.nbio.structure.scop.ScopDomain;
import org.biojava.nbio.structure.scop.ScopNode;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;


/** Utility classes for the XML serialization and de-serialization of SCOP.
 *
 * @author Andreas Prlic
 * @since 3.0.2
 *
 */
public class XMLUtil {

	static JAXBContext jaxbContextScopDescription;
	static {
		try {
			jaxbContextScopDescription= JAXBContext.newInstance(ScopDescription.class);
		} catch (JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}

	static JAXBContext jaxbContextScopDomain;
	static {
		try {
			jaxbContextScopDomain= JAXBContext.newInstance(ScopDomain.class);
		} catch (JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}

	static JAXBContext jaxbContextScopNode;
	static {
		try {
			jaxbContextScopNode= JAXBContext.newInstance(ScopNode.class);
		} catch (JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}

	static JAXBContext jaxbContextDomains;
	static {
		try {
			jaxbContextDomains= JAXBContext.newInstance(TreeSet.class);
		} catch (JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}

	static JAXBContext jaxbContextStringSortedSet;
	static {
		try {
			jaxbContextStringSortedSet= JAXBContext.newInstance(TreeSetStringWrapper.class);
		} catch (JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}

	static JAXBContext jaxbContextComments;
	static {
		try {
			jaxbContextComments = JAXBContext.newInstance(ListStringWrapper.class);
		} catch( JAXBException e){
			throw new RuntimeException("Could not initialize JAXB context", e);
		}
	}


	public static String getScopDescriptionXML(ScopDescription desc){

		return converScopDescription(desc);

	}

	public static ScopDescription getScopDescriptionFromXML(String xml){

		ScopDescription job = null;

		try {

			Unmarshaller un = jaxbContextScopDescription.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopDescription) un.unmarshal(bais);

		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return job;
	}

	private static String converScopDescription(ScopDescription desc) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopDescription.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( desc, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return baos.toString();
	}

	public static String getScopDescriptionsXML(List<ScopDescription> descriptions){

		ScopDescriptions container = new ScopDescriptions();
		container.setScopDescription(descriptions);

		return container.toXML();

	}



	public static String getCommentsXML(List<String> comments ){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextComments.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			ListStringWrapper wrapper = new ListStringWrapper();
			wrapper.setData(comments);

			m.marshal( wrapper, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return baos.toString();
	}
	public static List<String> getCommentsFromXML(String xml){

		List<String> comments = null;

		try {

			Unmarshaller un = jaxbContextComments.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			ListStringWrapper wrapper = (ListStringWrapper) un.unmarshal(bais);
			comments = wrapper.getData();

		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return comments;
	}


	public static String getScopNodeXML(ScopNode scopNode){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopNode.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( scopNode, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return baos.toString();
	}

	public static ScopNode getScopNodeFromXML(String xml){
		ScopNode job = null;

		try {

			Unmarshaller un = jaxbContextScopNode.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopNode) un.unmarshal(bais);

		} catch (JAXBException e){
			throw new RuntimeException("Could not parse from XML", e);
		}

		return job;
	}

	public static String getScopNodesXML(List<ScopNode> nodes) {
		ScopNodes container = new ScopNodes();
		container.setScopNode(nodes);

		return container.toXML();
	}

	public static String getScopDomainXML(ScopDomain domain){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextScopDomain.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( domain, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return baos.toString();
	}

	public static ScopDomain getScopDomainFromXML(String xml){
		ScopDomain job = null;

		try {

			Unmarshaller un = jaxbContextScopDomain.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			job = (ScopDomain) un.unmarshal(bais);

		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return job;
	}

	public static String getScopDomainsXML(List<ScopDomain> domains) {
		ScopDomains container = new ScopDomains();
		container.setScopDomain(domains);

		return container.toXML();
	}


	public static String getDomainsXML(SortedSet<Domain> domains){
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextDomains.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			m.marshal( domains, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return baos.toString();
	}
	@SuppressWarnings("unchecked")
	public static SortedSet<Domain> getDomainsFromXML(String xml) {

		SortedSet<Domain> domains = null;
		try {

			Unmarshaller un = jaxbContextDomains.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			domains = (SortedSet<Domain>) un.unmarshal(bais);

		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return domains;
	}

	public static String getDomainRangesXML(SortedSet<String> domainRanges){
		if ( ! (domainRanges instanceof TreeSet)) {
			throw new IllegalArgumentException("SortedSet needs to be a TreeSet!");
		}
		TreeSet<String> data = (TreeSet<String>)domainRanges;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		PrintStream ps = new PrintStream(baos);

		try {

			Marshaller m = jaxbContextStringSortedSet.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			TreeSetStringWrapper wrapper = new TreeSetStringWrapper();
			wrapper.setData(data);
			m.marshal( wrapper, ps);


		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return baos.toString();
	}

	public static SortedSet<String> getDomainRangesFromXML(String xml){
		SortedSet<String> domains = null;
		try {

			Unmarshaller un = jaxbContextStringSortedSet.createUnmarshaller();

			ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());

			TreeSetStringWrapper wrapper = (TreeSetStringWrapper) un.unmarshal(bais);
			domains = wrapper.getData();

		} catch (JAXBException e){
			throw new RuntimeException("Could not serialize to XML", e);
		}

		return domains;
	}
}
