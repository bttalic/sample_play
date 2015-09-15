/**
 * Created by benjamin on 14/09/15.
 */
$(document).ready(function(){

    var files;

    // Add events
    $('input[type=file]').on('change', prepareUpload);

    function prepareUpload(event)
    {
        files = event.target.files;
    }


    // for any form on this page do the follofing
    $('form').submit(function(e, options){



        //if options is not defined define it as a empty object
        var options = options || {};

        //if allow is true let the event propagate to the server
        if(options.allow == true){
            return;
        }

        //prevent submiting the form
        e.preventDefault();

        //clean all error messages
        $("[data-error]").remove();

        //get a refference to the current form
        $form = $(this);

        var data = new FormData($form[0]);
        if(typeof files != 'undefined') {
            $.each(files, function (key, value) {
                data.append(key, value);
            });
        }
       
        $form.each(function(){
            var input = $(this); // This is the jquery object of the input, do what you will
            data.append(input.attr("name"), input.val());
        });


        /*
         * This will setup a AJAX call, the params are
         * url: where to go
         * method: what kind of request
         * data: the data to add to the request
         */
        $.ajax({
            url: $form.attr("action"),
            method: $form.attr("method"),
            data: data,
            cache: false,
            contentType: false,
            processData: false
        }).success(function(response){
            //if the validation did not return an error, triger the form submit with options.allow = true
            $form.trigger("submit", {allow: true});

        }).error(function(response){
            //if we got a bad request we take the JSON object from the response
            var errors = response.responseJSON;

            //get an array of all the keys in the error object
            var keys = Object.keys(errors);

            //itterate over the keys
            for(var i = 0; i < keys.length; i++){

                //get the error messages for the current key (which represents one input field)
                var errorMessages = errors[keys[i]];
                //a string of all the errors for the current input
                var allErrors = "";
                for(var j = 0; j < errorMessages.length; j++){
                    allErrors += errorMessages[j];
                }

                $('input[name="'+keys[i]+'"]').parent().append('<span class="alert-danger" data-error >'+allErrors+'</span>')
            }

        });

    });

});