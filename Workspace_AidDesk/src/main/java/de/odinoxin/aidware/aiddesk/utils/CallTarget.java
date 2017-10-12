package de.odinoxin.aidware.aiddesk.utils;

import javax.ws.rs.client.WebTarget;

public interface CallTarget<X> {
    X execute(WebTarget webTarget);
}