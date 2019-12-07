/**
 * Copyright 2011 Alexandre Dutra
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package fr.dutra.tools.maven.deptree.extras;

import java.io.*;
import java.util.Collections;

import org.apache.commons.io.FileUtils;

import fr.dutra.tools.maven.deptree.core.Node;
import fr.dutra.tools.maven.deptree.core.InputType;
import fr.dutra.tools.maven.deptree.core.ParseException;
import fr.dutra.tools.maven.deptree.core.Parser;
import fr.dutra.tools.maven.deptree.core.VisitException;
import org.apache.maven.shared.invoker.*;


public class VelocityRendererMain {

    /**
     * Arguments list:
     * <ol>
     * <li>Full path of dependency tree file to parse;</li>
     * <li>Dependency tree file format; must be the (case-insensitive) name of a <code>{@link InputType}</code> enum;</li>
     * <li>Full path to the output directory where HTML files and static resources will be generated (<em>this directory will be erased</em>);</li>
     * <li>Renderer format; must be the (case-insensitive) name of a <code>{@link VelocityRenderType}</code> enum;</li>
     * </ol>
     * @param args
     * @throws ParseException
     * @throws IOException
     * @throws VisitException
     */
    public static void main(String[] args) throws IOException, ParseException, VisitException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("/Users/chengzhiya/owlaser-paclist/paclist/pom.xml"));
        //request.setGoals(Collections.singletonList("dependency:tree -DoutputFile=/Users/chengzhiya/Desktop/tree -DoutputType=text"));
        request.setGoals(Collections.singletonList("dependency:tree -D outputFile=/Users/chengzhiya/Desktop/dependency_tree.graphml -D outputType=graphml"));
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv("MAVEN_HOME")));
        try {
            invoker.execute(request);
        } catch (MavenInvocationException e) {
            e.printStackTrace();
        }
        InputType format = InputType.GRAPHML;
        Reader r = new BufferedReader(new InputStreamReader(new FileInputStream("/Users/chengzhiya/Desktop/dependency_tree.graphml"),"UTF-8"));
        Parser parser = format.newParser();
        Node tree = parser.parse(r);
        File outputDir = new File("/Users/chengzhiya/Desktop/result2");
        FileUtils.deleteDirectory(outputDir);
        outputDir.mkdirs();
        VelocityRenderer renderer =
            VelocityRenderType.JQUERY_TREE_TABLE.newRenderer();
        renderer.setFileName("index.html");
        renderer.setOutputDir(outputDir);
        renderer.visit(tree);


    }

}
