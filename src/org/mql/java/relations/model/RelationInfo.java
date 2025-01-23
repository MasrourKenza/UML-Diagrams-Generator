package org.mql.java.relations.model;

import org.mql.java.relations.ennumerations.RelationType;

public class RelationInfo {
	private String sourceClss;
	private String targetClss;
	private RelationType type;
	private String name;
	private boolean isCollection;
	
	
	
	public RelationInfo() {
		
	}

	public RelationInfo(String sourceClss, String targetClss, RelationType type) {
		super();
		this.sourceClss = sourceClss;
		this.targetClss = targetClss;
		this.type = type;
	}

	public String getSourceClss() {
		return sourceClss;
	}

	public void setSourceClss(String sourceClss) {
		this.sourceClss = sourceClss;
	}

	public String getTargetClss() {
		return targetClss;
	}

	public void setTargetClss(String targetClss) {
		this.targetClss = targetClss;
	}

	public RelationType getType() {
		return type;
	}

	public void setType(RelationType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCollection() {
		return isCollection;
	}

	public void setCollection(boolean isCollection) {
		this.isCollection = isCollection;
	}
	
	@Override
	public String toString() {
		String result = sourceClss + " --[" + type + "]";
		  if (name != null) {
	            result = result + " (via " + name + ")";
	        }
	        if (isCollection) {
	            result = result + " [Collection]";
	        }
	        return result;
	  
	}
	
}
