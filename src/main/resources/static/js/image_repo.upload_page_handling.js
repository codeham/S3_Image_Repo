$(document).ready(function () {
    console.log("HELLO FROM JQUERY !")
    // hide success alert
    $('alert').hide();

    $('#image-file').change(function(event){
        $('#json-response').empty();
        $('#json').empty();
        console.log('loadFile() was triggered by change event')
        var output = document.getElementById('output');
        objectURL = URL.createObjectURL(event.target.files[0]);
        console.log(objectURL);
        output.src = objectURL
    })

    $('#submit-button').click(function(event){
        // stop submission, manual post
        event.preventDefault();

        // empty json text from prev post
        $('#json').empty();

        // grab file
        let photoFile = document.getElementById('image-file').files[0];

        // place inside a form data object
        const formData = new FormData();

        // append to form data object
        formData.append('file', photoFile);

        // disabled the submit button
        $("#submit-button").prop("disabled", true);

        $.ajax({
            type: 'POST',
            enctype: 'multipart/form-data',
            url: '/storage/uploadFile',
            data: formData,
            processData: false,
            contentType: false,
            cache: false,
            timeout: 6000,
            success: function(response){
                console.log("Success !");
                console.log(response);
                // show in front end
                $('#json-response').html('<p>' + 'JSON Response ' + "<span class='dot dot-sm dot-success'></span>" + '</p>')
                $('#json').html(JSON.stringify(response, null, 2));
                $('#submit-button').prop("disabled", false);
            },
            error: function(response){
                console.log("Error");
                $('#json-response').html('<p>' + 'JSON Response ' + "<span class='dot dot-sm dot-danger'></span>" + '</p>')
                $('#json').html('Whoops ! an error occured, check file format');
                $('#submit-button').prop("disabled", false);
            }
        })
    })
})