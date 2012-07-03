
// prepare the form when the DOM is ready
$(document).ready(function() {
    var options = {
      target:'#filelist'
    };

    // bind form using 'ajaxForm'
    $('#addFile').ajaxForm(options);

    function showAlert(text){
        if(text)
        {

            $('.alerts').append($('#alertSample').clone().append(text).alert()).fadeIn()
        }

    };

    var forbiddenPopoverOptions = {
        placement:'right',
        title:'Ooops!',
        content:'Sorry, you have no reputation to vote yet.',
        trigger:'hover'
      //  delay:{show:200 ,hide:100}

    };

    $('.forbiddenVoteForm').popover(forbiddenPopoverOptions)

    forbiddenPopoverOptions.content = 'Sorry, you have no reputation to add new content'
    $('.forbiddenAddContentForm').popover(forbiddenPopoverOptions)


    $("body").on("submit",".replace-form", function(event) {
        /* stop form from submitting normally */
        event.preventDefault();

        var form = $(this);
        var replaceID = form.data('replace')
        $.post(form.attr( 'action' ),
            form.serialize(), function(data)
            {
                if(data.error)
                {
                    for(i=0;i< data.error.length;i++)
                    {

                       showAlert(data.error[i]) ;
                    }
                }
                else
                {
                    var fileList = $("" + replaceID); //pegar isto de um attribto data-x
                    fileList.empty();
                    fileList.append(data);
                }
            })
            .error(function(data)
            {
                showAlert("Error! Could not process your request! Reason: "+data.statusText);
            })

    });

    $('input.autocomplete-relation').each( function() {
        var $input = $(this);
        // Set-up the autocomplete widget.
        var serverUrl = $input.data('url');
       $input.autocomplete({
            source: serverUrl
        });
    });

    $('#addSubjectButton').click(function()
    {
        var subjectsFirstChildClone = $('#subjects').children().first().clone();

        var input = subjectsFirstChildClone.children().children().first()
        var url = input.data('url');
        input.autocomplete({source:url})

        $('#subjects').append(subjectsFirstChildClone);
    });

    $('#addAuthorButton').click(function()
    {
        var authorsFirstChildClone = $('#authors').children().first().clone();

        var input = authorsFirstChildClone.children().children().first()
        var url = input.data('url');
        input.autocomplete({source:url})
        //$('input.autocomplete-relation', authorsFirstChildClone).autocomplete({source:"/contentinception/authorslist"})

        $('#authors').append(authorsFirstChildClone);
    });


    $(".forbidden").submit(function(event){
        event.preventDefault();
    });

    $("body").on("click",".compare-button", function(event) {

        var currentDescriptionText =  $("#currentDescriptionText").val();

        var descriptionHistory =  $(this).parent().parent().parent()

        var historyText =  descriptionHistory.children().first().text()

        var diffText = diffString(historyText, currentDescriptionText );

        var diffTag = $("#compare-diff-description")

        diffTag.hide()

        diffTag.empty().append(diffText)

        diffTag.fadeIn('slow');
    });

});

