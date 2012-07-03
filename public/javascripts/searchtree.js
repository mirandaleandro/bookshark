$(function () {

    $("#demo").bind("before.jstree", function (e, data) {

        $("#alog").append(data.func + "<br />");

    })

        .jstree({

        // List of active plugins

        //"plugins" : ["themes","json_data","ui","crrm","cookies","dnd","search","types","hotkeys","contextmenu"],
        "plugins" : ["themes","json_data","ui","crrm","cookies","dnd","hotkeys","contextmenu"],

        // I usually configure the plugin that handles the data first

        // This example uses JSON as it is most common

        "json_data" : {

            // This tree is ajax enabled - as this is most common, and maybe a bit more complex

            // All the options are almost the same as jQuery's AJAX (read the docs)

                "ajax" : {

        // the URL to fetch the data

        "url" : "/static/v.1.0pre/_demo/server.php",

        // the `data` function is executed in the instance's scope

        // the parameter is the node being loaded

        // (may be -1, 0, or undefined when loading the root nodes)

        "data" : function (n) {

            // the result is fed to the AJAX request `data` option

            return {

                    "operation" : "get_children",

            "id" : n.attr ? n.attr("id").replace("node_","") : 1

        };

    }

}

},

// Configuring the search plugin

"search" : {

    // As this has been a common question - async search

    // Same as above - the `ajax` config option is actually jQuery's AJAX object

    "ajax" : {

        "url" : "/static/v.1.0pre/_demo/server.php",

        // You get the search string as a parameter

        "data" : function (str) {

            return {

                    "operation" : "search",

            "search_str" : str

        };

    }

}

},

// Using types - most of the time this is an overkill

// read the docs carefully to decide whether you need types

"types" : {

    // I set both options to -2, as I do not need depth and children count checking

    // Those two checks may slow jstree a lot, so use only when needed

    "max_depth" : -2,

    "max_children" : -2,

    // I want only `drive` nodes to be root nodes

    // This will prevent moving or creating any other type as a root node

    "valid_children" : [ "drive" ],

    "types" : {

        // The default type

        "default" : {

            // I want this type to have no children (so only leaf nodes)

            // In my case - those are files

            "valid_children" : "none",

            // If we specify an icon for the default type it WILL OVERRIDE the theme icons

            "icon" : {

                "image" : "/static/v.1.0pre/_demo/file.png"

            }

        },



        // The `folder` type

        "folder" : {

            // can have files and other folders inside of it, but NOT `drive` nodes

            "valid_children" : [ "default", "folder" ],

            "icon" : {

                "image" : "/static/v.1.0pre/_demo/folder.png"

            }

        },

        // The `drive` nodes

        "" : {
            // can have files and folders inside, but NOT other `drive` nodes
            "valid_children" : [ "default", "folder" ],

            "icon" : {

                "image" : "/static/v.1.0pre/_demo/root.png"

            },
            // those prevent the functions with the same name to be used on `drive` nodes
            // internally the `before` event is used
            "start_drag" : false,
            "move_node" : false,
            "delete_node" : false,
            "remove" : false
        }
    }
},
// UI & core - the nodes to initially select and open will be overwritten by the cookie plugin

// the UI plugin - it handles selecting/deselecting/hovering nodes
"ui" : {
    // this makes the node with ID node_4 selected onload
    "initially_select" : [ "node_4" ]
},
// the core plugin - not many options here
"core" : {
    // just open those two nodes up
    // as this is an AJAX enabled tree, both will be downloaded from the server
    "initially_open" : [ "node_2" , "node_3" ]
}
})
    .bind("create.jstree", function (e, data) {
    $.post(
    "/static/v.1.0pre/_demo/server.php",
    {
        "operation" : "create_node",
        "id" : data.rslt.parent.attr("id").replace("node_",""),
        "position" : data.rslt.position,
        "title" : data.rslt.name,
        "type" : data.rslt.obj.attr("rel")
    },
    function (r) {
        if(r.status) {
            $(data.rslt.obj).attr("id", "node_" + r.id);
        }
    else {
            $.jstree.rollback(data.rlbk);
        }
    }
    );
})
    .bind("remove.jstree", function (e, data) {
    data.rslt.obj.each(function () {
        $.ajax({
                async : false,
        type: 'POST',
        url: "/static/v.1.0pre/_demo/server.php",
        data : {
            "operation" : "remove_node",
            "id" : this.id.replace("node_","")
        },
        success : function (r) {
            if(!r.status) {
                data.inst.refresh();
            }
        }
    });
});
})
    .bind("rename.jstree", function (e, data) {
    $.post(
    "/static/v.1.0pre/_demo/server.php",
    {
        "operation" : "rename_node",
        "id" : data.rslt.obj.attr("id").replace("node_",""),
        "title" : data.rslt.new_name
    },
    function (r) {
        if(!r.status) {
            $.jstree.rollback(data.rlbk);
        }
    }
    );
})
    .bind("move_node.jstree", function (e, data) {
    data.rslt.o.each(function (i) {
        $.ajax({
                async : false,
        type: 'POST',
        url: "/static/v.1.0pre/_demo/server.php",
        data : {
            "operation" : "move_node",
            "id" : $(this).attr("id").replace("node_",""),
            "ref" : data.rslt.cr === -1 ? 1 : data.rslt.np.attr("id").replace("node_",""),
            "position" : data.rslt.cp + i,
            "copy" : data.rslt.cy ? 1 : 0
        },
        success : function (r) {
            if(!r.status) {
                $.jstree.rollback(data.rlbk);
            }
        else {
                $(data.rslt.oc).attr("id", "node_" + r.id);
                if(data.rslt.cy && $(data.rslt.oc).children("UL").length) {
                    data.inst.refresh(data.inst._get_parent(data.rslt.oc));
                }
            }
            $("#analyze").click();
        }
    });
});
});

});

$(document).ready(function(){
    $("#treeViewDiv").jstree({
        "json_data" : {
            "ajax" : {
                "url" : "/subjecttaxonomy/tree",
                "data" : function (n) {
                    return {
                        "operation" : "get_children",
                        "id" : n.attr ? n.attr("id").replace("node_","") : 1
                    };
                }
            }
        },
        "plugins" :[ "themes", "json_data", "ui","dnd","crrm" ],
        // Using types - most of the time this is an overkill


// read the docs carefully to decide whether you need types



        "themes":{
            "theme":"default",
            "dots":false,
            "icons":false
        }
    })
        .bind("select_node.jstree", function(e, data)
        {
          /*  if(jQuery.data(data.rslt.obj[0], "id"))
            {
                //window.location=jQuery.data(data.rslt.obj[0], "href");
                var x =   jQuery.data(data.rslt.obj[0], "id");
                //  alert(x)
            }
            else
            {
                alert("No href defined for this element");
            } */
        })
        .bind("create.jstree", function (e, data)
        {
            $.ajax({
                async : false,
                type: 'POST',
                url: "/subjecttaxonomy/newSubject",
                data : {
                    "parent_id" :jQuery.data(data.rslt.parent[0],'id'),
                    "subject" : data.rslt.name
                },
                success : function (r) {
                    if(r.status)
                    {
                        $(data.rslt.obj).attr("id", r.subject_id);
                    }
                    else {
                        $.jstree.rollback(data.rlbk);
                    }

                }
            });/*
            $.post(
                "/subjecttaxonomy/newSubject",
                {
                    "operation" : "create_node",
                    "id" : data.rslt.parent.attr("id").replace("node_",""),
                    "position" : data.rslt.position,
                    "subject" : data.rslt.name,
                    "type" : data.rslt.obj.attr("rel")
                },
                function (r) {
                    if(r)
                    {

                        $(data.rslt.obj).attr("id", "node_" + r.id);
                    }
                    else {
                        $.jstree.rollback(data.rlbk);
                    }
                }
            );   */
        })
        .bind("move_node.jstree", function (e, data) {

            data.rslt.o.each(function (i) {

                $.ajax({
                    async : false,
                    type: 'POST',
                    url: "/subjecttaxonomy/move",
                    data : {

                        "subject_id" :jQuery.data(data.rslt.o[i],'id'), //$(this).attr("id").replace("node_",""),
                        "parent_id" :jQuery.data(data.rslt.np[i],'id')//data.rslt.cr === -1 ? 1 : data.rslt.np.attr("id").replace("node_",""),

                    },
                    success : function (r) {
                        if(!r) {
                            $.jstree.rollback(data.rlbk);
                        }
                        else {
                            $(data.rslt.oc).attr("id", "node_" + r.id);
                            if(data.rslt.cy && $(data.rslt.oc).children("UL").length) {
                                data.inst.refresh(data.inst._get_parent(data.rslt.oc));
                            }
                        }

                    }
                });
            });
        });
});

$(document).ready(function()
{
    $('#add_element').click(function()
    {
        $("#treeViewDiv").jstree("create", null, "last", { "attr" : { "rel" : this.id.toString().replace("add_", "") } });
    });

});


/* Sample
 "data":[
 {
 "data" : "Search engines",
 "children" :[
 {"data":"Yahoo", "metadata":{"href":"http://www.yahoo.com"}},
 {"data":"Bing", "metadata":{"href":"http://www.bing.com"}},
 {"data":"Google", "children":[{"data":"Youtube", "metadata":{"href":"http://youtube.com"}},{"data":"Gmail", "metadata":{"href":"http://www.gmail.com"}},{"data":"Orkut","metadata":{"href":"http://www.orkut.com"}}], "metadata" : {"href":"http://youtube.com"}}
 ]
 },
 {
 "data" : "Networking sites","metadata":{"href":""},
 "children" :[
 {"data":"Facebook", "metadata":{"href":"http://www.fb.com"}},
 {"data":"Twitter", "metadata":{"href":"http://twitter.com"}}
 ]
 }
 ]*/