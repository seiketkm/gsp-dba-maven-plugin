/*
 * Copyright (C) 2015 coastland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.co.tis.gsp.tools.dba.mojo;

import java.util.Map;
import jp.co.tis.gsp.tools.dba.dialect.DialectFactory;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;

public abstract class AbstractDbaMojo extends AbstractMojo {
    /**
     * Database driver class.
     */
    @Parameter(required = true)
    protected String driver;

    /**
     * The url for connection.
     */
    @Parameter(required = true)
    protected String url;

    /**
     * The name of admin user.
     */
    @Parameter(required = true)
    protected String adminUser;
    
    /**
     * The password of admin user.
     */
    @Parameter
    protected String adminPassword;
    
	/**
     * The name of database user.
	 */
    @Parameter(required = true)
	protected String user;

    /**
     * The password of database user;
     */
    @Parameter
    protected String password;

	/**
	 * The name of schema.
	 */
    @Parameter
	protected String schema;


	/**
	 * dumpFile
	 */
    @Parameter
	protected String dmpFile;

    /**
     * additional Dialect names.<br/>
     * key : &lt; JDBC URL prefix &gt;. <br/>
     * value : &lt; dialect FQDN &gt;. <br/>
     *
     * eg. if you want to register xxx.yyy.ZZDialect for jdbc URL &quot;jdbc:foo:.....&quot;<br/>
     * write as follows.
     * <pre>
     *
     * &lt;configuration&gt;
     *   &lt;optionalDialects&gt;
     *     &lt;foo&gt;xxx.yyy.ZZDialect&lt;/foo&gt;
     *   &lt;/optionalDialects&gt;
     * &lt;/configuration&gt;
     *
     * </pre>.
     */
    @Parameter
    protected Map<String, String> optionalDialects;

    /**
     * setup execution environments.
     * override if needed.
     */
    protected void setupEnvironments() {
    }

    /**
     * register optional dialect to {@link DialectFactory}.
     * this must be done before {@link #executeMojoSpec}.
     */
    private void registerOptionalDialect() {
        DialectFactory.registerOptionalDialects(optionalDialects);
    }

    @Override
    public final void execute() throws MojoExecutionException, MojoFailureException {
        registerOptionalDialect();
        setupEnvironments();
        executeMojoSpec();
    }

    /**
     * execute mojo specific processing.
     * 
     * @throws MojoExecutionException @{@link org.apache.maven.plugin.Mojo#execute()}
     * @throws MojoFailureException @{@link org.apache.maven.plugin.Mojo#execute()}
     */
    protected abstract void executeMojoSpec() throws MojoExecutionException, MojoFailureException;
}
