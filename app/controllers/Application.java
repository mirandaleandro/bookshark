package controllers;


import models.User;
import play.mvc.Before;
import play.mvc.Controller;

import java.util.*;

public class Application extends Controller {

    @Before
    static void addUser() {
        User user = connected();
        if(user != null) {
            renderArgs.put("user", user);
        }
    }

    static User connected() {
        if(renderArgs.get("user") != null) {
            return renderArgs.get("user", User.class);
        }
        String username = session.get("user");
        if(username != null) {
            return User.find("byUsername", username).first();
        }
        return null;
    }


    public static void index() {
        User user = connected();
        if(user != null) {
            render(user);
        }
        render();
    }

    public static void register() {
        render();
    }

    public static void saveUser(User user, String verifyPassword)
    {
        validation.required(verifyPassword);
        validation.equals(user.password, verifyPassword).message("Your password doesn't match");
        if(validation.hasErrors())
        {
            System.out.println("Shit! I got errors");
            Application.index();
            render("@register", user, verifyPassword);
        }
        user.hasRegistered();
        user.create();
        user.save();

        session.put("user", user.username);
        flash.success("Welcome, " + user.name);
       Application.index();
    }

    public static void login(String username, String password) {
        User user = User.find("byUsernameAndPassword", username, password).first();
        if(user != null) {
            session.put("user", user.username);
            flash.success("Welcome, " + user.name);
            Application.index();
        }
        // Oops
        flash.put("username", username);
        flash.error("Login failed");
        index();
    }

    public static void logout() {
        session.clear();
        index();
    }
    
    public static void displayError(String message)
    {
        List<String> errList =  new ArrayList<String>(1);
        if(message != null)
        {

            errList.add(message);
        }

        displayJSErrors(errList);
    }
    public static void displayJSErrors(List<String> errors)
    {
        if(errors == null)
        {
            errors = new ArrayList<String>();
        }

        if(errors.isEmpty())
        {
            errors.add("Internal Undefined Server Error");
        }


        Map response = new HashMap();

        response.put("error",errors);

        renderJSON(response);
        
    }

}