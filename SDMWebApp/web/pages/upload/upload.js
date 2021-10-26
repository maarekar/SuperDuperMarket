$(function () {
    $("form.form-upload").submit(function () {

        var formData = new FormData(this);
        $.ajax({
            method: "POST",
            data: formData,
            url: this.action,
            processData: false,
            contentType: false,
            timeout: 5000,
            error: function () {
                $(".error").text("Failed to upload - AJAX error.");
                console.log("AJAX error.");
            },
            success: function (resp) {
                $(".error").text(resp);
                if(resp === "Zone uploaded successfully."){
                    $('#uploadButton').attr('disabled','disabled');
                    $('#zone-file').attr('disabled','disabled');
                }

            }
        });
        return false
    })
});


