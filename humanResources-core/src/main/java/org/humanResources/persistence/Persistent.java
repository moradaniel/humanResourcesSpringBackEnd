package org.humanResources.persistence;

/**
 ***************************************************************************************************
 * Defines an integer identifier that can be used to denote persistent classes
 * 	
 ***************************************************************************************************
 */
public interface Persistent
{
	public static final int UNSAVED_ID = -1;
	Long getId();
	void setId(Long id);
}
