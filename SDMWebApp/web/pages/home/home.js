var refreshRate = 2000; //milli seconds
var owner = "Owner";
var customer ="Customer";

$(function() {
    $.ajax({
        method: "POST",
        url: "users",
        timeout: 3000,
        error: function () {
            console.error("Failed to get ajax response");
        },
        success: function (response) {
            sessionStorage["type"] = response;
            if(response === owner){
                $('<a href="" class="btn align-middle   ml-auto mr-3 order-lg-last" id="uploadZone" data-toggle="modal" data-target="#modalContactForm" onclick="removeDisable()">Upload Zone</a>'
                    ).appendTo($("#navBarHome"));
                notifier();
            } else{
                $('<a href="" class="btn align-middle   ml-auto mr-3 order-lg-last" id="chargeCredit" data-toggle="modal" data-target="#chargeCreditForm">Charge Credit</a>'
                ).appendTo($("#navBarHome"));

                $( "#chargeCredit" ).blur(function() {
                    this.value = parseFloat(this.value).toFixed(2);
                });
            }
        }
    });

});


function removeDisable(){
    $("#uploadButton").removeAttr('disabled');
    $("#zone-file").removeAttr('disabled');}


function refreshUsersList(users) {
    console.log("enter2");
    //clear all current users
    $("#listOfUsers").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    const container = document.getElementById('listOfUsers');
    $.each(users || [], function(index, user) {

            // Create card element
            const card = document.createElement('div');
            card.classList = 'card-body';

            // Construct card content
            const content = `
<div class="card shadow p-3 mb-3 bg-white rounded" >
    <div class="card-body">
                <h5 class="card-title username">${user.name}</h5>
                <h6 class="card-subtitle text-muted">${user.accountType}</h6>
            </div>
            </div>
  `;

            // Append newly created card element to the container
            container.innerHTML += content;
    });
}

function ajaxUsersList() {
    console.log("enter1");
    $.ajax({
        method: "GET",
        url: "users",
        success: function(users) {
            refreshUsersList(users);
        }
    });
}

function refreshZonesList(zones) {

    //clear all current zones
    $("#table-zones tbody").empty();

    $.each(zones || [], function(index, zone) {

        $("#dataZones").append('<tr> <td>' + zone.ownerName
            + '</td>  <td class="zoneCell">' + zone.zoneName
            + '</td> <td>' + zone.numberOfItems
            + '</td> <td>' + zone.numberOfStores
            + '</td> <td>' + zone.numberOfOrders
            + '</td> <td>' + zone.averagePriceOrders
            //+ '</td> <td><div><a href="customerZone.html" class="btn showZone btn-secondary btn-lg active"  role="button" aria-pressed="true">Show</a></div>'
            + `</td> <td><button class="showZone use-address" onclick="myFunction( '${zone.zoneName}')">Show</button>`
            + '</td></tr>');


    });
}

function ajaxZonesList() {
    $.ajax({
        method: "GET",
        url: "zones",
        success: function(zones) {
            refreshZonesList(zones);
        }
    });
}


$(function() {
    ajaxUsersList();
    ajaxZonesList();
    //The users list is refreshed automatically every second
    setInterval(ajaxUsersList, refreshRate);

    setInterval(ajaxZonesList, refreshRate);


    //The chat content is refreshed only once (using a timeout) but
    //on each call it triggers another execution of itself later (1 second later)
    //triggerAjaxChatContent();
});


function myFunction(zone){

    //var id = $("#table-zones").find(".zoneCell:first").text();



    sessionStorage["zone"] = zone;
    if(sessionStorage["type"] === customer){
        window.location.assign("customerZone.html");
    } else{
        window.location.assign("ownerZone.html");
    }

    //window.location.assign("customerZone.html");

}

// fix date picker
$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var minDate = year + '-' + month + '-' + day;
    $('#date').attr('min', minDate);
});

$(function () {
    $("form.form-charge").submit(function(){
        console.log("in charge submit");
        $.ajax({
            method: "POST",
            data: $(this).serialize(),
            url: this.action,
            timeout: 2000,
            error: function () {
                $(".error").text("Failed to charge - AJAX error.");
            },
            success: function (resp) {
                $(".error").text(resp);

            }
        });
        return false
    })
});


// fix date picker
$(function(){
    var dtToday = new Date();

    var month = dtToday.getMonth() + 1;
    var day = dtToday.getDate();
    var year = dtToday.getFullYear();
    if(month < 10)
        month = '0' + month.toString();
    if(day < 10)
        day = '0' + day.toString();

    var minDate = year + '-' + month + '-' + day;
    $('#date').attr('min', minDate);
});




