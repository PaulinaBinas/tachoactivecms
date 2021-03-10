package com.binas.tachographcms.views.editdata;

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
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.artur.helpers.CrudServiceDataProvider;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Route(value = "tacho/editUsers", layout = MainView.class)
@PageTitle("Edytuj dane")
@JsModule("./src/views/editdata/editdata-view.js")
@CssImport("./styles/views/editdata/editdata-view.css")
@Tag("editdata-view")
public class EditDataView extends PolymerTemplate<TemplateModel> {

    @Id
    private Grid<User> grid;

    @Id
    private TextField name;
    @Id
    private TextField surname;
    @Id
    private TextField phoneNumber;
    @Id
    private TextField companyName;
    @Id
    private TextField daysReminder;
    @Id
    private TextField code;

    @Id
    private Button cancel;
    @Id
    private Button save;

    private BeanValidationBinder<User> binder;

    private User user;

    public EditDataView(@Autowired UserServiceImpl userService) {
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
                if (userFromBackend.isPresent()) {
                    populateForm(userFromBackend.get());
                } else {
                    refreshGrid();
                }
            } else {
                clearForm();
            }
        });

        // Configure Form
        binder = new BeanValidationBinder<>(User.class);

        // Bind fields. This where you'd define e.g. validation rules
        binder.forField(daysReminder).withConverter(new StringToIntegerConverter("Dozwolone są tylko liczby"))
                .bind("daysReminder");

        binder.bindInstanceFields(this);

        cancel.addClickListener(e -> {
            clearForm();
            refreshGrid();
        });

        save.addClickListener(e -> {
            try {
                if (this.user == null) {
                    this.user = new User();
                }
                AtomicBoolean uniquePhone = new AtomicBoolean(true);
                userService.getAllUsers().stream().forEach(u -> {
                    if(u.getPhoneNumber().equals(this.phoneNumber.getValue())) {
                        uniquePhone.set(false);
                        Notification.show("Nie zapisano danych, ponieważ podany numer telefonu występuje już w bazie. Zmień go na unikalny.");
                    }
                });
                if(uniquePhone.get()) {
                    if (userService.getUserByCode(this.code.getValue()) == null || this.user.getCode() == this.code.getValue() || this.code.getValue().isEmpty()) {
                        binder.writeBean(this.user);

                        userService.update(this.user);
                        clearForm();
                        refreshGrid();
                        Notification.show("Zapisano dane użytkownika.");
                    } else {
                        Notification.show("Nie zapisano danych, ponieważ podany kod występuje już w bazie. Zmień go na unikalny.");
                    }
                }
            } catch (ValidationException validationException) {
                Notification.show("Wystąpił błąd podczas zapisywania danych użytkownika.");
            }
        });
    }

    private void refreshGrid() {
        grid.select(null);
        grid.getDataProvider().refreshAll();
    }

    private void clearForm() {
        populateForm(null);
    }

    private void populateForm(User value) {
        this.user = value;
        binder.readBean(this.user);

    }


}
