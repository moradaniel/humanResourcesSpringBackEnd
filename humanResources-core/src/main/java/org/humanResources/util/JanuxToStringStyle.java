package org.humanResources.util;

import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.builder.StandardToStringStyle;


/**
 ***************************************************************************************************
 * Convenience class that provides alternatives to the apache lang StandardToStringStyle, and
 * exposes them as static variables
 * 
 ***************************************************************************************************
 */
public class JanuxToStringStyle extends StandardToStringStyle
{
	/** instantiates a plain vanilla JanuxToStringStyle class, which uses a COMPACT format by default */
	public static final ToStringStyle COMPACT = new JanuxToStringStyle();
	
	/** 
	 * Instantiates a plain vanilla StandardToStringStyle, but sets:
	 * <ul>
	 * 	<li>useFieldNames(true)</li>
	 * 	<li>useShortClassName(true)</li>
	 * 	<li>useUseIdentityHashCode(false)</li>
	 * </ul>
	 */
	public JanuxToStringStyle() 
	{
		super();
		this.setUseFieldNames(true);
		this.setUseShortClassName(true);
		this.setUseIdentityHashCode(false);
	}

} // end class


