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
package de.mirkosertic.gameengine.teavm;

import de.mirkosertic.gameengine.core.Game;
import org.teavm.jso.ajax.XMLHttpRequest;
import org.teavm.jso.json.JSON;

import java.util.Map;

public class TeaVMGameLoader {

    public interface GameLoadedListener {
        void onGameLoaded(Game aGame);
        void onGameLoadedError(Throwable aThrowable);
    }

    protected final GameLoadedListener listener;

    public TeaVMGameLoader(GameLoadedListener aListener) {
        listener = aListener;
    }

    public void loadFromServer() {
        final XMLHttpRequest theRequest = XMLHttpRequest.create();
        theRequest.overrideMimeType("text/plain");
        theRequest.open("GET", "game.json");
        theRequest.onComplete(() -> listener.onGameLoaded(parse(theRequest.getResponseText())));
        theRequest.send();
    }

    protected Game parse(String aResponse) {
        Map<String, Object> theResult = new TeaVMMap(JSON.parse(aResponse).cast());
        return Game.deserialize(theResult);
    }
}