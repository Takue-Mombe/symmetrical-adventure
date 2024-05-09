package com.example.fuelsystem.Service;


import com.example.fuelsystem.Models.Employee;
import com.example.fuelsystem.Models.Role;
import com.example.fuelsystem.Models.User;
import com.example.fuelsystem.Repositories.EmployeeRepository;
import com.example.fuelsystem.Repositories.UserRepository;
import com.example.fuelsystem.Views.Admin.AdminView;
import com.example.fuelsystem.Views.Admin.FuelListView;
import com.example.fuelsystem.Views.Admin.RequestsView;
import com.example.fuelsystem.Views.MainLayout;
import com.example.fuelsystem.Views.MainLayoutAdmin;
import com.example.fuelsystem.Views.Users.UserView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    private User authenticatedUser;

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username); // Retrieve the user from the repository
    }

    public record AuthorizedRoute(String route, String name, Class<? extends Component>view){

    }
    public class AuthException extends Exception{
    }

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User authenticate(String username, String password) throws AuthException {
        User user = userRepository.findByUsername(username);
        if (user != null && user.checkPassword(password)) {
            authenticatedUser = user; // Set the authenticated user
            createRoutes(user.getRole());
            System.out.println(user.getRole());
            return user; // Return the authenticated user
        } else {
            System.out.println(user);
            System.out.println("Invalid credentials");
            throw new AuthException();
        }
    }

    public User getAuthenticatedUser() {
        return authenticatedUser;
    }



    private void createRoutes(Role role) {

        if (role.equals(Role.ADMIN)) {
            getAuthorizedRoutes(role).forEach(route ->
                    RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainLayoutAdmin.class));
        } else {
            getAuthorizedRoutes(role).forEach(route ->
                    RouteConfiguration.forSessionScope().setRoute(route.route, route.view, MainLayout.class));
        }
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        var routes = new ArrayList<AuthorizedRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("uhome", "Home", UserView.class));
        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("ahome", "Home", AdminView.class));
            routes.add(new AuthorizedRoute("accounts", "Fuel", FuelListView.class));
            routes.add(new AuthorizedRoute("invoices", "Requests", RequestsView.class));
        }

        return routes;
    }


    public List<User> getAll(String filterText){
        if (filterText==null||filterText.isEmpty()){
            return userRepository.findAll();
        }
        return  userRepository.search(filterText);
    }
    public List<User> getAll(){
        return userRepository.findAll();
    }
    public Long count(){
        return userRepository.count();
    }
    public void delete(User user){
        userRepository.delete(user);
    }
    public void save(User user) {
        String search = user.getEmployee().getEmployeeNumber();
        Employee existingPatient = employeeRepository.findByEmployeeNumber(search);

        if(existingPatient != null) {
            // Patient exists, set the patient attribute of accounts and save
            user.setEmployee(existingPatient);
            userRepository.save(user);
        } else {
            // Patient doesn't exist, handle this case if needed
        }
    }

}
