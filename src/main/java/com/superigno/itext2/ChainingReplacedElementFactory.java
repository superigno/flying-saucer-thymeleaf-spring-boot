/**
 * 
 */
package com.superigno.itext2;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.xhtmlrenderer.extend.ReplacedElement;
import org.xhtmlrenderer.extend.ReplacedElementFactory;
import org.xhtmlrenderer.extend.UserAgentCallback;
import org.xhtmlrenderer.layout.LayoutContext;
import org.xhtmlrenderer.render.BlockBox;
import org.xhtmlrenderer.simple.extend.FormSubmissionListener;

public class ChainingReplacedElementFactory implements ReplacedElementFactory {
    private List<ReplacedElementFactory> replacedElementFactories 
            = new ArrayList<ReplacedElementFactory>();

    public void addReplacedElementFactory(ReplacedElementFactory replacedElementFactory) {
        this.replacedElementFactories.add(0, replacedElementFactory);
    }

    @Override
    public ReplacedElement createReplacedElement(LayoutContext c, BlockBox box, 
            UserAgentCallback uac, int cssWidth, int cssHeight) {
        for(ReplacedElementFactory replacedElementFactory : this.replacedElementFactories) {
            ReplacedElement element = replacedElementFactory
                    .createReplacedElement(c, box, uac, cssWidth, cssHeight);
            if(element != null) {
                return element;
            }
        }
        return null;
    }

    @Override
    public void reset() {
        for(ReplacedElementFactory replacedElementFactory : this.replacedElementFactories) {
            replacedElementFactory.reset();
        }
    }

    @Override
    public void remove(Element e) {
        for(ReplacedElementFactory replacedElementFactory : this.replacedElementFactories) {
            replacedElementFactory.remove(e);
        }
    }

    @Override
    public void setFormSubmissionListener(FormSubmissionListener listener) {
        for(ReplacedElementFactory replacedElementFactory : this.replacedElementFactories) {
            replacedElementFactory.setFormSubmissionListener(listener);
        }
    }
}