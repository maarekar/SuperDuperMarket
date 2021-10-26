var zoneName, dateString;
var grade = 1;
var storeId, message;
var size;
var storesOfOrder;

function prepareFeedbacksForm(zone, date, stores){
    zoneName = zone;
    dateString = date;
    size = stores.length;
    storesOfOrder = stores;
    $("#firstRadioButton").prop("checked", true);

    $('#storesForFeedbacks').append('<option selected="">Choose a store</option>');

    $.each(stores|| [], function(index, store) {
        $('#storesForFeedbacks').append($('<option>', {
            value: store.id,
            text : store.name
        }));
    });

    var modal = document.getElementById("feedbacks");
    modal.style.display = "block";
}

function chooseRate(rating){
    grade = rating;
}

function addFeedback(){
    storeId = $('#storesForFeedbacks').val();
    if(storeId === "Choose a store"){
        return;
    }


    message = $.trim($("#comment").val());
    /*if(!message){
        return;
    }*/

    size--;

    $.ajax({
        type: 'POST',
        data: {zoneName : zone,  storesList : storeId, rate : grade, message: message, date: date},
        url: "addFeedback",
        timeout: 2000,
        error: function() {
            $(".succesFeedback").text("Failed to charge - AJAX error.");
        },
        success: function(resp) {
            $(".succesFeedback").text(resp);
            checkSituation();
        },
    });
    return false;
}

function checkSituation(){

    $('#storesForFeedbacks').children().remove();
    $('#storesForFeedbacks').append('<option selected="">Choose a store</option>');

    $.each(storesOfOrder|| [], function(index, store) {
        $('#storesForFeedbacks').append($('<option>', {
            value: store.id,
            text : store.name
        }));
    });

    $("#comment").val('');

    $("#firstRadioButton").prop("checked", true);
}


function closeFeedbacksForm(){
    window.location.assign("customerZone.html");
}