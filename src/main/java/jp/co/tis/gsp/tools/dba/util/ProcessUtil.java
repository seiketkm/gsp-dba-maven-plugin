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

package jp.co.tis.gsp.tools.dba.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.seasar.framework.util.FileInputStreamUtil;

public class ProcessUtil {
	private static final Log log = new SystemStreamLog();

	public static void execWithInput(File dumpFile, String... args) throws IOException, InterruptedException {
        execWithInput(dumpFile, null, args);
	}

    public static void execWithInput(File dumpFile, Map<String, String> environment, String... args) throws IOException, InterruptedException {
        FileInputStream in=null;
        
        Process process = null;
        OutputStream stdin = null;
        InputStream stdout = null;
        BufferedReader br = null;
        
        try {
            in = FileInputStreamUtil.create(dumpFile);
            ProcessBuilder pb = new ProcessBuilder(args);
            pb.redirectErrorStream(true);
            if (environment != null) {
                pb.environment().putAll(environment);
            }
            log.info(StringUtils.join(args, ' ')+"を実行します");
            process = pb.start();
            stdin = process.getOutputStream();
            stdout = process.getInputStream();
            
            byte[] buf = new byte[8192];
            while(true) {
                int res = in.read(buf);
                if (res <= 0) break;
                stdin.write(buf, 0, res);
            }
            stdin.flush();
            
        	stdin.close();
        	process.waitFor();
        	
            br = new BufferedReader(new InputStreamReader(stdout));
			String line;
			while ((line = br.readLine()) != null) {
				System.out.println(line);
		    }
            
        } catch(IOException e) {
            throw e;
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(br);
            
            if(process != null){
    			stdout.close();
            	process.destroy();
            }
        }

    }


}
