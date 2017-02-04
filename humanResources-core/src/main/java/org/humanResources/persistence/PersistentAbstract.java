package org.humanResources.persistence;

import javax.persistence.*;
import java.io.Serializable;


@MappedSuperclass
public abstract class PersistentAbstract implements Persistent,Serializable
{
	@Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
	@Column(name = "id", updatable = false, nullable = false)
	protected Long id = null;//new Long(UNSAVED_ID);

	public Long getId() {
		return this.id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
}
