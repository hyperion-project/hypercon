package org.hyperion.hypercon.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import org.hyperion.hypercon.spec.LedModel;
import org.mufassa.model.ModelList;

public class LedTvComponent extends JComponent {

	private final BufferedImage mDisplayedImage = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
	private final Graphics2D mImageG2d = mDisplayedImage.createGraphics();
	
	private final int mBorderWidth = 12;
	
	private final ModelList<LedModel> mLeds;

	private LedModel mSelectedLed;
	
	public LedTvComponent(final ModelList<LedModel> pLeds) {
		mLeds = pLeds;
		
		addMouseMotionListener(mMouseMotionListener);
	}
	
	public void setImage(Image pImage) {
		mImageG2d.clearRect(0, 0, mDisplayedImage.getWidth(), mDisplayedImage.getHeight());
		mImageG2d.drawImage(pImage, 0,0, mDisplayedImage.getWidth(), mDisplayedImage.getHeight(), null);
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setColor(Color.DARK_GRAY.darker());
		g2d.fillRect(0,0, getWidth()-1, getHeight()-1);
		
		g2d.drawImage(mDisplayedImage, mBorderWidth, mBorderWidth, getWidth()-2*mBorderWidth, getHeight()-2*mBorderWidth, null);
		if (mLeds == null) {
			return;
		}
		
		g2d.setColor(Color.GRAY);
		for (LedModel led : mLeds) {
			Rectangle rect = led2tv(led.imageRectangle.getValue());
			
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
			
			int seqNr = led.sequenceNr.getValue();
			
			switch (led.side.getValue()) {
			case top_left:
				g2d.drawString(""+seqNr, 0, 11);
				break;
			case top:
				g2d.drawString(""+seqNr, (int)rect.getCenterX(), 11);
				break;
			case top_right:
				g2d.drawString(""+seqNr, (int)getWidth()-11, (int)11);
				break;
			case right:
				g2d.drawString(""+seqNr, (int)getWidth()-11, (int)rect.getCenterY());
				break;
			case bottom_right:
				g2d.drawString(""+seqNr, (int)getWidth()-11, (int)getHeight()-1);
				break;
			case bottom:
				g2d.drawString(""+seqNr, (int)rect.getCenterX(), (int)getHeight()-1);
				break;
			case bottom_left:
				g2d.drawString(""+seqNr, (int)0, (int)getHeight()-1);
				break;
			case left:
				g2d.drawString(""+seqNr, 0, (int)rect.getCenterY());
				break;
			}
		}
		if (mSelectedLed != null) {
			Rectangle rect = led2tv(mSelectedLed.imageRectangle.getValue());

			g2d.setStroke(new BasicStroke(3.0f));
			g2d.setColor(Color.WHITE);
			g2d.drawRect(rect.x, rect.y, rect.width, rect.height);
		}
	}
	
	public Rectangle led2tv(Rectangle2D pLedRect) {
		int tvWidth  = getWidth()-2*mBorderWidth;
		int tvHeight = getHeight()-2*mBorderWidth;
		
		int x = (int) Math.round(mBorderWidth + tvWidth*pLedRect.getX());
		int y = (int) Math.round(mBorderWidth + tvHeight*pLedRect.getY());
		int width = (int) Math.round(tvWidth * pLedRect.getWidth());
		int height = (int) Math.round(tvHeight * pLedRect.getHeight());
		
		return new Rectangle(x,y, width, height);
	}
	
	private final MouseMotionListener mMouseMotionListener = new MouseMotionListener() {
		@Override
		public void mouseMoved(MouseEvent e) {
			mSelectedLed = null;

			double x = (double)(e.getX() - mBorderWidth) / (getWidth() - mBorderWidth*2);
			double y = (double)(e.getY() - mBorderWidth) / (getHeight() - mBorderWidth*2);
			
			for (LedModel led : mLeds) {
				if (led.imageRectangle.getValue().contains(x, y) || (Math.abs(led.location.getValue().getX() - x) < 0.01 && Math.abs(led.location.getValue().getY() - y) < 0.01)) {
					mSelectedLed = led;
					break;
				}
			}
			
			repaint();
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			
		}
	};

}
