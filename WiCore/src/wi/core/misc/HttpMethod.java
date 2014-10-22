/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wi.core.misc;

import wi.core.pattern.IStringValuable;

/**
 *
 * @author hermeschang
 */
public enum HttpMethod implements IStringValuable {

    Get("GET"), Post("POST"), Put("PUT"), Delete("DELETE");

    private String mValue;

    private HttpMethod(String value) {
        this.mValue = value;
    }

    @Override
    public String getValue() {
        return this.mValue;
    }

}
