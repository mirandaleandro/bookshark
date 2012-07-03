package controllers;

import models.*;
import models.User;
import models.resourseproperties.Subject;

import java.util.*;


public class SubjectTaxonomy extends Application
{
    public static final String TREE_DATA_KEY = "data";
    public static final String TREE_CHILDREN_KEY = "children";

   public static void index()
   {
       List<Subject> subjects = Subject.findAll();
       render(subjects);
   }

    public static void tree()
    {
        //Map map = sampleMap();
        //List list = sampleArray();

        List <Subject> root = Subject.find("parent is null").fetch();

        List<Map> tree = toStringSubjectTree(root);

        renderJSON(tree);
    }

    public static void newSubject(String subject, Long parent_id)
    {

        User creator = connected();
        if(creator == null || !creator.isEditor())
        {
            System.out.println("Action denied");
            return;
        }
        Subject parent = null;
        
        if(parent_id != null)
        {
            parent = Subject.findById(parent_id);
        }

        Subject newSubject = new Subject(subject,creator,parent).save();

        System.out.println("creating new subject: "+subject);
        
        Map response = new HashMap();
        response.put("status",true);
        response.put("subject_id",newSubject.id);

        renderJSON(response);

    }

    public static void move(Long subject_id, Long parent_id)
    {
        Subject subject = Subject.findById(subject_id);
        Subject parent = null;

        if(parent_id != null)
        {
            parent = Subject.findById(parent_id);
        }

        subject.parent = parent;

        subject.save();

        System.out.println(String.format("subject %s has now %s as parent",subject.toString(),parent == null?"(no parent)->element is now root":parent.toString()));

        renderJSON(true);
    }

    public static List<Map> toStringSubjectTree(List<Subject>subjects)
    {
        List<Map> maps = new ArrayList<Map>();
        
        for( Subject s: subjects)
        {
            Map map = new HashMap();
            map.put(TREE_DATA_KEY,s.toString());
            
            Map metadata = new HashMap();
            metadata.put("id",s.id);

            map.put("metadata",metadata);
            
            List<Subject> children = s.children();
            if(children != null && children.size() > 0)
            {
                map.put(TREE_CHILDREN_KEY,toStringSubjectTree(children));
            }

            maps.add(map);
        }

        return maps;
    }

    private static List<Map> sampleArray()
    {
        Map map2 = new HashMap();
        map2.put(TREE_DATA_KEY,"um");
        List<Map> a = new ArrayList<Map>();
        a.add(map2);
        map2 = new HashMap();
        map2.put(TREE_DATA_KEY,"doi2");
        a.add(map2);
        a.add(map2);
        a.add(map2);

        Map map = new HashMap();
        map.put(TREE_DATA_KEY,"dois");


        map.put(TREE_CHILDREN_KEY,a);

        List<Map> m = new ArrayList<Map>();

        m.add(map);
        m.add(map);
        return m;
    }

    public static Map sampleMap(){
        Map map2 = new HashMap();
        map2.put(TREE_DATA_KEY,"um");
        List<Map> a = new ArrayList<Map>();
        a.add(map2);
        map2 = new HashMap();
        map2.put(TREE_DATA_KEY,"doi2");
        a.add(map2);
        a.add(map2);
        a.add(map2);

        Map map = new HashMap();
        map.put(TREE_DATA_KEY,"dois");


        map.put(TREE_CHILDREN_KEY,a);

        return map;
    }
}
