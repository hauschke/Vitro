/* $This file is distributed under the terms of the license in LICENSE$ */

package edu.cornell.mannlib.vitro.webapp.auth.requestedAction;

import java.util.Arrays;
import java.util.List;

import edu.cornell.mannlib.vitro.webapp.auth.attributes.AccessOperation;
import edu.cornell.mannlib.vitro.webapp.auth.objects.AccessObject;

public class OrAuthorizationRequest extends AuthorizationRequest {

    private final AuthorizationRequest ar1;
    private final AuthorizationRequest ar2;

    OrAuthorizationRequest(AuthorizationRequest ar1, AuthorizationRequest ar2) {
        this.ar1 = ar1;
        this.ar2 = ar2;
    }

    public List<AuthorizationRequest> getItems() {
        return Arrays.asList(ar1, ar2);
    };

    @Override
    public WRAP_TYPE getWrapType() {
        return WRAP_TYPE.OR;
    }

    @Override
    public String toString() {
        return "(" + ar1 + " || " + ar2 + ")";
    }

    @Override
    public AccessObject getAccessObject() {
        return null;
    }

    @Override
    public AccessOperation getAccessOperation() {
        return null;
    }

}
