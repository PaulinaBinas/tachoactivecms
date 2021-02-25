package com.binas.tachographcms.views.changepassword;

import com.binas.tachographcms.model.entity.Admin;
import com.binas.tachographcms.service.AdminService;
import com.binas.tachographcms.views.main.MainView;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "tacho/changePassword", layout = MainView.class)
@PageTitle("Zmień hasło")
@JsModule("./src/views/changepassword/changepassword-view.js")
@CssImport("./styles/views/changepassword/changepassword-view.css")
@Tag("changepassword-view")
public class ChangePasswordView extends PolymerTemplate<TemplateModel> {

    @Id
    private PasswordField oldpassword;
    @Id
    private PasswordField newpassword;
    @Id
    private PasswordField repeatpassword;
    @Id
    private Button savebtn;
    private Admin admin;

    public ChangePasswordView(@Autowired AdminService adminService) {
        refreshAdmin(adminService);
        savebtn.addClickListener(e -> {
            if (oldpassword.getValue().equals(admin.getPassword())) {
                if (newpassword.getValue().equals(repeatpassword.getValue())) {
                    try {
                        adminService.changePassword(repeatpassword.getValue());
                        Notification.show("Nowe hasło zostało zapisane.");
                    } catch (Exception error) {
                        Notification.show("Wystąpił błąd podczas zapisywania nowego hasła.");
                    }
                } else {
                    Notification.show("Nowe hasła nie są takie same.");
                }
            } else {
                Notification.show("Stare hasło nie jest poprawne.");
            }
            refreshAdmin(adminService);
        });
    }

    private void refreshAdmin(AdminService adminService) {
        this.admin = adminService.getAdmin().get();
    }
}
