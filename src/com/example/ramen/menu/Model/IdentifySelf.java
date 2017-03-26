package com.example.ramen.menu.Model;

import java.io.Serializable;

public class IdentifySelf implements Serializable  {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
