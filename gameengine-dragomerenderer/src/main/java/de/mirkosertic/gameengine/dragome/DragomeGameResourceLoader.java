/*
 * Copyright 2016 Mirko Sertic
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.mirkosertic.gameengine.dragome;

import java.io.IOException;

import com.dragome.services.WebServiceLocator;
import de.mirkosertic.gameengine.core.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.dragome.web.html.dom.DomHandler;

import de.mirkosertic.gameengine.type.ResourceName;

public class DragomeGameResourceLoader implements GameResourceLoader {

    private final String sceneId;
    private final Element cacheElement;
    private final Document document;

    public DragomeGameResourceLoader(String aSceneId) {
        sceneId = aSceneId;

        DomHandler theHandler = WebServiceLocator.getInstance().getDomHandler();

        cacheElement = theHandler.getElementBySelector("#resourcecache");
        document = theHandler.getDocument();
    }

    @Override
    public GameResource load(ResourceName aResourceName) throws IOException {
        String theResourceName = sceneId + aResourceName.name;
        if (aResourceName.name.endsWith(".wav")) {
            return new DragomeGameResource(theResourceName, GameResourceType.SOUND, null);
        }

        Element theImage = document.createElement("img");
        theImage.setAttribute("src", theResourceName);
        cacheElement.appendChild(theImage);

        return new DragomeGameResource(theResourceName, GameResourceType.BITMAP, theImage);
    }

    @Override
    public void flush() {
        NodeList theChilds = cacheElement.getChildNodes();
        for (int i=0;i<theChilds.getLength();i++) {
            Node theChild = theChilds.item(i);
            theChild.getParentNode().removeChild(theChild);
        }
    }

    @Override
    public LoadedSpriteSheet loadSpriteSheet(ResourceName aResourceName, SuccessCallback aSuccessCallback) {
        return LoadedSpriteSheet.EMPTY;
    }
}