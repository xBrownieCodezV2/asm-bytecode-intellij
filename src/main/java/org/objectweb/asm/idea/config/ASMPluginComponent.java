/*
 *
 *  Copyright 2011 Cédric Champeau
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * /
 */

package org.objectweb.asm.idea.config;
/**
 * Created by IntelliJ IDEA.
 * User: cedric
 * Date: 18/01/11
 * Time: 19:51
 */

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.components.StoragePathMacros;
import com.intellij.openapi.project.Project;
import org.jdom.Element;

/**
 * A component created just to be able to configure the plugin.
 *
 * @author Cédric Champeau
 * @author Thiakil (December 2017)
 */
@State(
	name = ASMPluginConfiguration.COMPONENT_NAME,
	storages = {
		@Storage(StoragePathMacros.WORKSPACE_FILE)
	}
)
public class ASMPluginComponent implements PersistentStateComponent<Element>{
	
	private boolean         skipFrames;
	private boolean         skipDebug;
	private boolean         skipCode;
	private boolean         expandFrames;
	private GroovyCodeStyle codeStyle = GroovyCodeStyle.LEGACY;
	
	public ASMPluginComponent(final Project project){
	}
	
	public boolean isSkipCode(){
		return skipCode;
	}
	
	public void setSkipCode(final boolean skipCode){
		this.skipCode = skipCode;
	}
	
	public boolean isSkipDebug(){
		return skipDebug;
	}
	
	public void setSkipDebug(final boolean skipDebug){
		this.skipDebug = skipDebug;
	}
	
	public boolean isSkipFrames(){
		return skipFrames;
	}
	
	public void setSkipFrames(final boolean skipFrames){
		this.skipFrames = skipFrames;
	}
	
	public GroovyCodeStyle getCodeStyle(){
		return codeStyle;
	}
	
	public void setCodeStyle(final GroovyCodeStyle codeStyle){
		this.codeStyle = codeStyle;
	}
	
	public boolean isExpandFrames(){
		return expandFrames;
	}
	
	public void setExpandFrames(final boolean expandFrames){
		this.expandFrames = expandFrames;
	}
	
	// -------------------- state persistence
	
	@Override
	public Element getState(){
		Element root    = new Element("state");
		Element asmNode = new Element("asm");
		asmNode.setAttribute("skipDebug", String.valueOf(skipDebug));
		asmNode.setAttribute("skipFrames", String.valueOf(skipFrames));
		asmNode.setAttribute("skipCode", String.valueOf(skipCode));
		asmNode.setAttribute("expandFrames", String.valueOf(expandFrames));
		root.addContent(asmNode);
		Element groovyNode = new Element("groovy");
		groovyNode.setAttribute("codeStyle", codeStyle.toString());
		root.addContent(groovyNode);
		return root;
	}
	
	@Override
	public void loadState(final Element state){
		Element asmNode = state.getChild("asm");
		if(asmNode != null){
			final String skipDebugStr = asmNode.getAttributeValue("skipDebug");
			if(skipDebugStr != null) skipDebug = Boolean.parseBoolean(skipDebugStr);
			final String skipFramesStr = asmNode.getAttributeValue("skipFrames");
			if(skipFramesStr != null) skipFrames = Boolean.parseBoolean(skipFramesStr);
			final String skipCodeStr = asmNode.getAttributeValue("skipCode");
			if(skipCodeStr != null) skipCode = Boolean.parseBoolean(skipCodeStr);
			final String expandFramesStr = asmNode.getAttributeValue("expandFrames");
			if(expandFramesStr != null) expandFrames = Boolean.parseBoolean(expandFramesStr);
		}
		Element groovyNode = state.getChild("groovy");
		if(groovyNode != null){
			String codeStyleStr = groovyNode.getAttributeValue("codeStyle");
			if(codeStyleStr != null) codeStyle = GroovyCodeStyle.valueOf(codeStyleStr);
		}
	}
	
	@Override
	public String toString(){
		return "ASMPluginComponent{" +
		       "skipFrames=" + skipFrames +
		       ", skipDebug=" + skipDebug +
		       ", skipCode=" + skipCode +
		       ", expandFrames=" + expandFrames +
		       ", codeStyle=" + codeStyle +
		       '}';
	}
}


