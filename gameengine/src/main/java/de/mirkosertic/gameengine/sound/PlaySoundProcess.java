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
package de.mirkosertic.gameengine.sound;

import de.mirkosertic.gameengine.core.Promise;
import de.mirkosertic.gameengine.process.AbstractGameProcess;
import de.mirkosertic.gameengine.type.ResourceName;

public class PlaySoundProcess extends AbstractGameProcess {

    private final GameSoundSystem gameSoundSystem;
    private final ResourceName resourceName;
    private Object soundObject;

    PlaySoundProcess(GameSoundSystem aSoundSystem, ResourceName aResourceName) {
        gameSoundSystem = aSoundSystem;
        resourceName = aResourceName;
    }

    @Override
    public void started() {
        super.started();
        playSound();
    }

    private void playSound() {
        gameSoundSystem.play(resourceName).thenContinue(new Promise.NoReturnHandler() {
            @Override
            public void process(Object aResult) {
                soundObject = aResult;
            }
        });
    }

    @Override
    public void killed() {
        if (soundObject != null) {
            gameSoundSystem.stop(soundObject);
        }
    }
}