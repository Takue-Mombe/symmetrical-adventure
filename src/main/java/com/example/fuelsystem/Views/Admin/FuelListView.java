package com.example.fuelsystem.Views.Admin;

import com.example.fuelsystem.Models.Employee;
import com.example.fuelsystem.Service.EmployeeService;
import com.example.fuelsystem.Service.FuelService;
import com.example.fuelsystem.Views.MainLayoutAdmin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "fuels",layout = MainLayoutAdmin.class)
public class FuelListView extends VerticalLayout {
    private final FuelService fuelService;
    private FuelForm fuelForm;
    Grid<Employee> grid=new Grid<>(Employee.class);
    TextField filterText=new TextField();
    public FuelListView(FuelService fuelService) {
        this.fuelService = fuelService;


        setClassName("fuelService");
        setSizeFull();


        configureGrid();
        getToolBar();



        fuelForm =  new FuelForm();
        fuelForm.addListener(FuelForm.SaveEvent.class,this::saveContact);
        fuelForm.addListener(FuelForm.DeleteEvent.class,this::deleteContact);
        fuelForm.addListener(FuelForm.CloseEvent.class, e->closedEditor());
        Div content=new Div(fuelForm,grid);

        content.addClassName("content");
        content.setSizeFull();
        add(getToolBar(),content);
        updateGrid();
        closedEditor();
    }



    private void deleteFuel(FuelForm.DeleteEvent deleteEvent) {
        fuelService.delete();
        updateList();
        closedEditor();
    }

    private void saveContact(FuelForm.SaveEvent saveEvent) {
        fuelService.add(saveEvent.getFuel());
        updateGrid();
        closedEditor();
    }


    private HorizontalLayout getToolBar() {

        Button addPatientsBtn=new Button("Add New Fuel", clickEvent -> addEmployee());
        HorizontalLayout toolbar=new HorizontalLayout(filterText,addPatientsBtn);
        toolbar.addClassName("Toolbar");
        return toolbar;
    }

    private void addEmployee() {
        grid.asSingleSelect().clear();
        editEmployee(new Employee());
    }


    private void updateGrid() {
        grid.setItems(employeeService.findAll());

    }

    private void configureGrid() {
        grid.addClassName("EmployeeGrid");
        grid.setSizeFull();

        // Set the width of the columns
        grid.getColumns().forEach(column -> column.setAutoWidth(true));

        // Add value change listener for selecting patients
        grid.asSingleSelect().addValueChangeListener(event -> editEmployee(event.getValue()));
    }



    private void editEmployee(Employee employee) {
        if (employee==null){
            closedEditor();
        }else {
            fuelForm.setEmployee(employee);
            fuelForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closedEditor() {
        fuelForm.setEmployee(null);
        fuelForm.setVisible(false);
        removeClassName("editing");
    }
}
