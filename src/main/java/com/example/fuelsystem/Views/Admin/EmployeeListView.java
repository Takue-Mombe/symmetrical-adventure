package com.example.fuelsystem.Views.Admin;


import com.example.fuelsystem.Models.Employee;
import com.example.fuelsystem.Models.User;
import com.example.fuelsystem.Service.EmployeeService;
import com.example.fuelsystem.Views.MainLayoutAdmin;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route(value = "employees",layout = MainLayoutAdmin.class)
@CssImport(value = "style.css")
public class EmployeeListView extends VerticalLayout {

    private final EmployeeService employeeService;
    private EmployeeForm employeeForm;
    Grid<Employee> grid=new Grid<>(Employee.class);
    TextField filterText=new TextField();
    public EmployeeListView(EmployeeService employeeService) {
        this.employeeService = employeeService;


        setClassName("EmployeeListView");
        setSizeFull();


        configureGrid();
        getToolBar();



        employeeForm =  new EmployeeForm();
        employeeForm.addListener(EmployeeForm.SaveEvent.class,this::saveContact);
        employeeForm.addListener(EmployeeForm.DeleteEvent.class,this::deleteContact);
        employeeForm.addListener(EmployeeForm.CloseEvent.class, e->closedEditor());
        Div content=new Div(employeeForm,grid);

        content.addClassName("content");
        content.setSizeFull();
        add(getToolBar(),content);
        updateGrid();
        closedEditor();
    }



    private void deleteContact(EmployeeForm.DeleteEvent deleteEvent) {
        employeeService.delete(deleteEvent.getEmployee());
        updateList();
        closedEditor();
    }

    private void saveContact(EmployeeForm.SaveEvent saveEvent) {
        employeeService.add(saveEvent.getEmployee());
        updateGrid();
        closedEditor();
    }


    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Filter By Name...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e->updateList());

        Button addPatientsBtn=new Button("Add New Patient", clickEvent -> addEmployee());
        HorizontalLayout toolbar=new HorizontalLayout(filterText,addPatientsBtn);
        toolbar.addClassName("Toolbar");
        return toolbar;
    }

    private void addEmployee() {
        grid.asSingleSelect().clear();
        editEmployee(new Employee());
    }

    private void updateList() {
        grid.setItems(employeeService.findAll(filterText.getValue()));
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
            employeeForm.setEmployee(employee);
            employeeForm.setVisible(true);
            addClassName("editing");
        }
    }
    private void closedEditor() {
        employeeForm.setEmployee(null);
        employeeForm.setVisible(false);
        removeClassName("editing");
    }
}
