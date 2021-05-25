package main.entity;

import java.io.Serializable;

/**
 * Root of all entities which have identifier field
 *
 * @author Dmitry Kuzavkov
 */

public abstract class Entity implements Serializable{

    private long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
