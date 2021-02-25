package com.binas.tachographcms.views.generatecode;

import com.binas.tachographcms.model.entity.User;
import com.binas.tachographcms.service.impl.UserServiceImpl;
import com.binas.tachographcms.views.main.MainView;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;

@Route(value = "tacho/generateCode", layout = MainView.class)
@PageTitle("Generuj kody")
@JsModule("./src/views/generatecode/generatecode-view.js")
@CssImport("./styles/views/generatecode/generatecode-view.css")
@Tag("generatecode-view")
public class GenerateCodeView extends PolymerTemplate<TemplateModel> {

    @Id
    private Grid<User> grid;

    @Id
    private Button generate;

    @Id
    private Button remove;

    private BeanValidationBinder<User> binder;

    private User user;

    public GenerateCodeView(@Autowired UserServiceImpl userService) {
        grid.addColumn(User::getName).setHeader("Imię").setAutoWidth(true);
        grid.addColumn(User::getSurname).setHeader("Nazwisko").setAutoWidth(true);
        grid.addColumn(User::getPhoneNumber).setHeader("Numer telefonu").setAutoWidth(true);
        grid.addColumn(User::getCompanyName).setHeader("Firma").setAutoWidth(true);
        grid.addColumn(User::getDaysReminder).setHeader("Co ile dni wysyłać przypomnienia?").setAutoWidth(true);
        grid.addColumn(User::getCode).setHeader("Kod").setAutoWidth(true);
        grid.setDataProvider(new CrudServiceDataProvider<>(userService));
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.setHeightFull();

        // when a row is selected or deselected, populate form
        grid.asSingleSelect().addValueChangeListener(event -> {
            if (event.getValue() != null) {
                Optional<User> userFromBackend = userService.get(event.getValue().getId());
                // when a row is selected but the data is no longer available, refresh grid
                if(userFromBackend.isPresent()) {
                    user = userFromBackend.get();
                }
            }
        });

        generate.addClickListener(e -> {
            try {
                userService.generateNewCode(this.user.getId());
                refreshGrid();
                Notification.show("Nowy kod został zapisany.");
            } catch (Exception validationException) {
                Notification.show("Wystąpił błąd podczas generowania nowego kodu.");
            }
        });

        remove.addClickListener(e -> {
            try {
                userService.removeUserCode(this.user.getId());
                refreshGrid();
                Notification.show("Kod został usunięty.");
            } catch (Exception validationException) {
                Notification.show("Wystąpił błąd podczas usuwania kodu.");
            }
        });
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }
}
