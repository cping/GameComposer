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
package de.mirkosertic.gameengine.web.github;

import de.mirkosertic.gameengine.core.Promise;
import de.mirkosertic.gameengine.web.EditorProject;
import de.mirkosertic.gameengine.web.ResourceAccessor;
import de.mirkosertic.gameengine.web.html5.Blob;
import de.mirkosertic.gameengine.web.indexeddb.IndexedDBFilesystem;
import org.teavm.jso.core.JSString;
import org.teavm.jso.json.JSON;

public class GithubEditorProject implements EditorProject {

    // http://mdswanson.com/blog/2011/07/23/digging-around-the-github-api-take-2.html
    // https://gist.github.com/robnyman/1894032
    // https://api.github.com/repos/mirkosertic/GameComposer/git/refs/heads/master

    private final GithubProjectDefinition projectDefinition;

    public GithubEditorProject(GithubProjectDefinition aProjectDefinition) {
        projectDefinition = aProjectDefinition;
    }

    @Override
    public Promise<ResourceAccessor, String> initializeLoader() {
        return IndexedDBFilesystem.open("github_" + projectDefinition.getUser() + "_" + projectDefinition.getRelativePath()+ "_" + projectDefinition.getRelativePath().replace("/","_")).thenContinue((Promise.Handler<IndexedDBFilesystem, ResourceAccessor>) aResult -> {
            String theBaseURL = "https://raw.githubusercontent.com/" + projectDefinition.getUser() + "/" + projectDefinition.getRepository() + "/master" + projectDefinition.getRelativePath();
            GithubResourceAccessor theAccessor = new GithubResourceAccessor(theBaseURL, aResult);
            //TODO: Wait for completion here
            theAccessor.persistFile(DEFINITION_FILENAME, Blob.createJSONBlob(JSString.valueOf(JSON.stringify(projectDefinition))));
            return theAccessor;
        });
    }
}