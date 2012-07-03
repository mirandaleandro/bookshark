package controllers;

public class User extends Application
{
    public static void index()
    {
        models.User user = connected();

        render(user);
    }
}
