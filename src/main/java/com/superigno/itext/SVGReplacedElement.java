package com.superigno.itext;

import java.awt.Graphics2D;
import java.awt.Point;
import java.net.URI;
import java.net.URL;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.xhtmlrenderer.css.style.CalculatedStyle;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.pdf.ITextOutputDevice;
import org.xhtmlrenderer.pdf.ITextReplacedElement;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.render.PageBox;
import org.xhtmlrenderer.render.RenderingContext;

import com.kitfox.svg.SVGDiagram;
import com.kitfox.svg.SVGException;
import com.kitfox.svg.SVGUniverse;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;

public class SVGReplacedElement implements ITextReplacedElement {

	private Point location = new Point(0, 0);
	private SVGDiagram diagram;
	private int cssWidth;
	private int cssHeight;

	private static final Logger LOG = Logger.getLogger(SVGReplacedElement.class);

	public SVGReplacedElement(URL svgFileUrl, int cssWidth, int cssHeight) {
		this.cssWidth = cssWidth;
		this.cssHeight = cssHeight;
		this.diagram = this.loadDiagram(svgFileUrl);
	}

	private SVGDiagram loadDiagram(URL svgFileUrl) {
		SVGUniverse svgUniverse = new SVGUniverse();
		URI svgFileUri = svgUniverse.loadSVG(svgFileUrl);
		SVGDiagram diagram = svgUniverse.getDiagram(svgFileUri);
		diagram.setIgnoringClipHeuristic(true);
		return diagram;
	}

	@Override public void detach(LayoutContext c) { }

	@Override public int getBaseline() {
		return 0;
	}

	@Override public int getIntrinsicWidth() {
		return (int)this.diagram.getWidth();
	}

	@Override public int getIntrinsicHeight() {
		return (int)this.diagram.getHeight();
	}

	@Override public boolean hasBaseline() {
		return false;
	}

	@Override public boolean isRequiresInteractivePaint() {
		return false;
	}

	@Override public Point getLocation() {
		return this.location;
	}

	@Override public void setLocation(int x, int y) {
		this.location.x = x;
		this.location.y = y;
	}

	@Override public void paint(RenderingContext renderingContext, ITextOutputDevice outputDevice, BlockBox blockBox) {
		PdfContentByte pdfContentByte = outputDevice.getWriter().getDirectContent();
		float widthInPoints = this.cssWidth / outputDevice.getDotsPerPoint();
		float heightInPoints = this.cssHeight / outputDevice.getDotsPerPoint();

		PdfTemplate pdfTemplate = pdfContentByte.createTemplate(widthInPoints, heightInPoints);
		Graphics2D graphics2d = pdfTemplate.createGraphics(pdfTemplate.getWidth(), pdfTemplate.getHeight());
		try {
			graphics2d.scale(widthInPoints / this.diagram.getWidth(), heightInPoints / this.diagram.getHeight());
			this.diagram.render(graphics2d);
		}
		catch(SVGException e) {
			if (SVGReplacedElement.LOG.isEnabledFor(Level.ERROR)) {
				SVGReplacedElement.LOG.log(Level.ERROR, "Cannot create SVG element for PDF.", e);
			}
			return;
		}
		finally {
			graphics2d.dispose();
		}
		PageBox page = renderingContext.getPage();
		float x = blockBox.getAbsX() + page.getMarginBorderPadding(renderingContext, CalculatedStyle.LEFT);
		float y = page.getBottom() - (blockBox.getAbsY() + this.cssHeight) + page.getMarginBorderPadding(renderingContext, CalculatedStyle.BOTTOM);
		x /= outputDevice.getDotsPerPoint();
		y /= outputDevice.getDotsPerPoint();
		pdfContentByte.addTemplate(pdfTemplate, x, y);
	}

}
