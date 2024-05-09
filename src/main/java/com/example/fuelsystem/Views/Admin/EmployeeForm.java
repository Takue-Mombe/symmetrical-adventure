package com.example.fuelsystem.Views.Admin;


import com.example.fuelsystem.Models.Employee;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.shared.Registration;
import org.springframework.stereotype.Component;

@Component
public class EmployeeForm extends FormLayout {


    private TextField firstName = new TextField("First Name");
    private TextField lastName = new TextField("Last Name");
    private TextField email = new TextField("Email");
    private TextField employeeNumber = new TextField("Employee Number");
    private TextField phoneNumber = new TextField("Phone Number");
    private TextField department = new TextField("Department");


    private Binder<Employee> binder = new Binder<>(Employee.class);



    Button save=new Button("Save");

    Button delete=new Button("Delete");
    Button cancel=new Button("Cancel");

    public void setEmployee(Employee employee){
        binder.setBean(employee);
    }
    private com.vaadin.flow.component.Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(clickEvent -> validateAndSave());
        delete.addClickListener(clickEvent -> fireEvent(new DeleteEvent(this,binder.getBean())));
        cancel.addClickListener(clickEvent -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(statusChangeEvent -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,delete,cancel);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent((this),binder.getBean()));
        }
    }


    // Events
    public static abstract class EmployeeFormEvent extends ComponentEvent<EmployeeForm> {
        private Employee employee;
        protected EmployeeFormEvent(EmployeeForm source, Employee employee) {
            super(source, false);
            this.employee = employee;
        }
        public Employee getEmployee() {
            return employee;
        }
    }
    public static class SaveEvent extends EmployeeFormEvent {
        SaveEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }
    }
    public static class DeleteEvent extends EmployeeFormEvent {
        DeleteEvent(EmployeeForm source, Employee employee) {
            super(source, employee);
        }
    }
    public static class CloseEvent extends EmployeeFormEvent {
        CloseEvent(EmployeeForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}

