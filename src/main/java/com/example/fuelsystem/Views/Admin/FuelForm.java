package com.example.fuelsystem.Views.Admin;

import com.example.fuelsystem.Models.Employee;
import com.example.fuelsystem.Models.Fuel;
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
public class FuelForm extends FormLayout {
    private TextField fuelQuantity = new TextField("First Name");
    private TextField fuelDistributed = new TextField("Last Name");

    private Binder<Fuel> binder = new Binder<>(Fuel.class);



    Button save=new Button("Save");

    Button delete=new Button("Delete");
    Button cancel=new Button("Cancel");


    public void setFuel(Fuel fuel){
        binder.setBean(fuel);
    }
    private com.vaadin.flow.component.Component createButtonsLayout() {

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickListener(clickEvent -> validateAndSave());
        delete.addClickListener(clickEvent -> fireEvent(new FuelForm.DeleteEvent(this,binder.getBean())));
        cancel.addClickListener(clickEvent -> fireEvent(new FuelForm.CloseEvent(this)));

        binder.addStatusChangeListener(statusChangeEvent -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save,delete,cancel);
    }

    private void validateAndSave() {
        if (binder.isValid()){
            fireEvent(new SaveEvent((this),binder.getBean()));
        }
    }
    public static abstract class FuelFormEvent extends ComponentEvent<FuelForm> {
        private Employee employee;
        protected FuelFormEvent(FuelForm source, Fuel fuel) {
            super(source, false);
            this.employee = employee;
        }
        public Employee getEmployee() {
            return employee;
        }
    }
    public static class SaveEvent extends FuelForm.FuelFormEvent {
        SaveEvent(FuelForm source, Fuel fuel) {
            super(source, fuel);
        }
    }
    public static class DeleteEvent extends FuelForm.FuelFormEvent {
        DeleteEvent(FuelForm source, Fuel fuel) {
            super(source, fuel);
        }
    }
    public static class CloseEvent extends FuelForm.FuelFormEvent {
        CloseEvent(FuelForm source) {
            super(source, null);
        }
    }
    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
