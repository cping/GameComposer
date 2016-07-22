package de.mirkosertic.gameengine.dragome;

import org.w3c.dom.html.CanvasRenderingContext2D;

import de.mirkosertic.gameengine.generic.CSSUtils;
import de.mirkosertic.gameengine.type.Color;
import de.mirkosertic.gameengine.type.EffectCanvas;
import de.mirkosertic.gameengine.type.Position;

public class DragomeEffectCanvas implements EffectCanvas {

    private final CanvasRenderingContext2D renderingContext;

    public DragomeEffectCanvas(CanvasRenderingContext2D renderingContext) {
        this.renderingContext = renderingContext;
    }

    @Override
    public void setPaint(Color aColor) {
        String theColor = CSSUtils.toColor(aColor);
        renderingContext.setStrokeStyle(theColor);
        renderingContext.setFillStyle(theColor);
    }

    @Override
    public void drawSingleDot(Position aPosition) {
        renderingContext.strokeRect(aPosition.x, aPosition.y, 1, 1);
    }

    @Override
    public void fillRect(int aX, int aY, int aWidth, int aHeight) {
        renderingContext.fillRect(aX, aY, aWidth, aHeight);
    }

    @Override
    public void fillPolygon(int[] aXPositions, int[] aYPositions, int aNumberOfPositions) {
        renderingContext.beginPath();
        renderingContext.moveTo(aXPositions[0], aYPositions[0]);
        for (int i=1;i<aNumberOfPositions;i++) {
            renderingContext.lineTo(aXPositions[i], aYPositions[i]);
        }
        renderingContext.closePath();
        renderingContext.fill();
    }
}