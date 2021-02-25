package com.binas.tachographcms.views.base;

import com.binas.tachographcms.views.main.MainView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route(value = "")
@PageTitle("TachoActive")
@CssImport("./styles/views/base/base-view.css")
@Theme(value = Lumo.class, variant = Lumo.DARK)
public class BaseView extends VerticalLayout {

    private Image logo;
    private Anchor login;

    public BaseView() {
        login = new Anchor("tacho/editUsers", "Zaloguj się");
        login.setId("loginBtn");
        logo = new Image("images/logo.png", "TachoActive logo");
        add(logo);
        add(login);
    }
}
