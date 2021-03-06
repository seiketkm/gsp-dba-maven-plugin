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

package jp.co.tis.gsp.tools.db;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.Locale;

import jp.co.tis.gsp.tools.db.beans.Erd;

import org.apache.commons.lang.StringUtils;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class AbstractDbObjectParser {

	protected File outputDirectory;
	protected String schema;
	protected String url;
	private final Configuration fmConfig = new Configuration();
	protected final LinkedList<ClassTemplateLoader> templateLoaderList = new LinkedList<>();
	protected TypeMapper typeMapper;

	public AbstractDbObjectParser() {
		super();
		fmConfig.setEncoding(Locale.JAPANESE, "UTF-8");
		fmConfig.setNumberFormat(System.getProperty("gsp.freemarker.number_format", "#"));
	}

	protected void setupTemplateLoader() {
		templateLoaderList.add(new ClassTemplateLoader(AbstractDbObjectParser.class, "/jp/co/tis/gsp/tools/db/template/"));
		if (url != null) {
			String[] urlTokens = StringUtils.split(url, ':');
			if(urlTokens.length < 3) {
				throw new IllegalArgumentException("url isn't jdbc url format.");
			}
			templateLoaderList.addFirst(
					new ClassTemplateLoader(Erd.class, "/jp/co/tis/gsp/tools/db/template/"
							+urlTokens[1]+"/")
					);
		}

	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setOutputDirectory(File outputDirectory) {
		this.outputDirectory = outputDirectory;
	}

	protected Writer getWriter(String name) throws IOException {
		if (outputDirectory == null) {
			return new OutputStreamWriter(System.out);
		}

		File outputFile = new File(outputDirectory, name + ".sql");
		return new FileWriter(outputFile);
	}

	protected Template getTemplate(String templateName) throws IOException {
		Template template = null;

		for(ClassTemplateLoader templateLoader :templateLoaderList) {
			fmConfig.setTemplateLoader(templateLoader);
			try {
				template = fmConfig.getTemplate(templateName);
			} catch(IOException ignore) {
			}
			if(template != null) return template;
		}
		throw new IOException("テンプレート(" + templateName + ")が見つかりません");
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setTypeMapper(TypeMapper typeMapper) {
		this.typeMapper = typeMapper;
	}

	public File getOutputDirectory() {
		return outputDirectory;
	}

}