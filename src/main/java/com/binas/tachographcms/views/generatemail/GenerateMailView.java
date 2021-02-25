package com.binas.tachographcms.views.generatemail;

import com.binas.tachographcms.model.entity.Email;
import com.binas.tachographcms.service.EmailService;
import com.binas.tachographcms.views.main.MainView;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.validator.EmailValidator;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "tacho/generateMail", layout = MainView.class)
@PageTitle("Edytuj email")
@JsModule("./src/views/generatemail/generatemail-view.js")
@CssImport("./styles/views/generatemail/generatemail-view.css")
@Tag("generatemail-view")
public class GenerateMailView extends PolymerTemplate<TemplateModel> {


    @Id
    private Label currentmail;
    @Id
    private EmailField emailfield;
    @Id
    private Button savebtn;

    private Email email;

    public GenerateMailView(@Autowired EmailService emailService) {
        refreshText(emailService);

        savebtn.addClickListener(e -> {
            try {
                emailService.setEmail(emailfield.getValue());
                refreshText(emailService);
                Notification.show("Nowy mail został zapisany.");
            } catch (Exception validationException) {
                Notification.show("Wystąpił błąd podczas zapisywania maila.");
            }
        });
    }

    private void refreshText(EmailService emailService) {
        currentmail.setText("Obecny mail: " + emailService.getEmail());
    }
}
