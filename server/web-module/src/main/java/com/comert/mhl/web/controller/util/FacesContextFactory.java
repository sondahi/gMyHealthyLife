package com.comert.mhl.web.controller.util;

import jakarta.enterprise.inject.Produces;
import jakarta.faces.context.FacesContext;

class FacesContextFactory {

    @Produces
    public FacesContext getFacesContext(){
        return FacesContext.getCurrentInstance();
    }

}
