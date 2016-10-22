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
package de.mirkosertic.gameengine.web;

import de.mirkosertic.gameengine.event.Property;
import org.teavm.jso.JSBody;
import org.teavm.jso.browser.Window;
import org.teavm.jso.dom.html.HTMLElement;
import org.teavm.jso.dom.html.HTMLOptionElement;
import org.teavm.jso.dom.html.HTMLOptionsCollection;
import org.teavm.jso.dom.html.HTMLSelectElement;
import org.teavm.jso.dom.xml.Document;

public abstract class GenericRuleEditorElement implements HTMLElement {

    private static final Document DOCUMENT = Window.current().getDocument();

    @JSBody(params = {}, script = "return document.createElement('p');")
    public static native GenericRuleEditorElement create();

    public void addText(String aText) {
        appendChild(DOCUMENT.createTextNode(aText));
    }

    public <T> void addSelection(Property<T> aProperty, T[] aAllowedValues, HTMLInputBinder.Converter<T, String> aConverter) {
        HTMLSelectElement theSelectElement = (HTMLSelectElement) DOCUMENT.createElement("select");
        HTMLOptionsCollection theOptions = theSelectElement.getOptions();
        T theCurrentValue = aProperty.get();
        for (T theValue : aAllowedValues) {
            HTMLOptionElement theOption = (HTMLOptionElement)DOCUMENT.createElement("option");
            theOption.setText(aConverter.convertFrom(theValue));
            if (theCurrentValue == theValue) {
                theOption.setSelected(true);
            }
            theOptions.add(theOption);
        }
        theSelectElement.addEventListener("change", aEvent -> {
            if (theSelectElement.getSelectedIndex() >= 0) {
                aProperty.set(aAllowedValues[theSelectElement.getSelectedIndex()]);
            }
        });
        appendChild(theSelectElement);
    }
}