/*
 * {{{ header & license
 * Copyright (c) 2006 Patrick Wright
 * Copyright (c) 2007 Wisconsin Court System
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; either version 2.1
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.	See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 * }}}
 */
package com.superigno.itext;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextReplacedElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;


/**
 * Factory to create ReplacedElements for SVG embedded in our XML file, using
 * the Salamander library. Salamander in this case will return a Swing JPanel.
 */
public class SVGSalamanderReplacedElementFactory implements ReplacedElementFactory {

	@Override
	public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, UserAgentCallback uac, int cssWidth, int cssHeight) {

		Element element = box.getElement();

		if ((element == null)  || (!this.isSVGEmbedded(element))) {
			//System.out.println("No SVG -> return null");
			return null;
		}

		String filePath = element.getAttribute("data");
		String content = this.getSVGElementContent(element);

		System.out.println("SVGSalamanderReplacedElementFactory - CSS Width:"+cssWidth+" Height:"+cssHeight);
		System.out.println("SVG filePath: "+filePath);
		//System.out.println("Rendering embedded SVG via object tag from: " + path);
		System.out.println("Content is: " + content);
		System.out.println("cssWidth: " + cssWidth);
		System.out.println("cssHeight: " + cssHeight);

		ITextReplacedElement result = null;

		URL fileUrl = null;

		//If filePath is an absolute http(s) URL we need to treat it as such
		if(StringUtils.startsWithIgnoreCase(filePath, "http://") || StringUtils.startsWithIgnoreCase(filePath, "https://")) {
			try {
				fileUrl = new URL(filePath);
			} catch (MalformedURLException e) {
			}
		}

		//Next check if the SVG is a file in a webapp context, see if the file path isn't already correct
		if(fileUrl==null) {
			File f = new File(filePath);
			if(f.canRead()) {
				try {
					fileUrl = f.toURI().toURL();
				} catch (MalformedURLException e) {
					//fileUrl = null; //fileUrl can only be null
				}
			}
		}

		//If all else fails try if the file is a resource in the classpath:
		if(fileUrl==null) {
			fileUrl = SVGSalamanderReplacedElementFactory.class.getResource(filePath);
		}

		if(fileUrl==null) {
			return null;
		}

		System.out.println("SVG fileUrl: "+fileUrl);
		result = new SVGReplacedElement(fileUrl, cssWidth, cssHeight);

		return result;
	}

	@SuppressWarnings("unused")
	private String getSVGElementContent(Element elem) {
		if ( elem.getChildNodes().getLength() > 0 ) {
			return elem.getFirstChild().getNodeValue();
		}
		//else
		return "SVG";
	}

	private boolean isSVGEmbedded(Element elem) {
		//System.out.printf("Node name: %s\tType: %s\n",elem.getNodeName(), elem.getAttribute("type"));
		return elem.getNodeName().equals("object") && elem.getAttribute("type").equals("image/svg+xml");
	}

	@Override
	public void reset() {
		// nothing to do.
	}

	@Override
	public void remove(Element e) {
		// nothing to do.
	}

	@Override
	public void setFormSubmissionListener(FormSubmissionListener listener) {
		// nothing to do.
	}
}
