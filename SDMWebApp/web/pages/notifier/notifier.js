var refreshRate = 2000; //milli seconds
var orderAlertVersion, storeAlertVersion;

function notifier(){
    ajaxOrderAlert();
    triggerAjaxOrderAlert();

    ajaxNewStoreAlert();
    triggerAjaxNewStoreAlert();

    ajaxFeedbackAlert();
    triggerFeedbackAlert();

}

function ajaxOrderAlert() {
    //orderAlertVersion = sessionStorage["orderVersion"];
    $.ajax({
        url: "order-alert",
        data: {version : sessionStorage["orderVersion"]},
        dataType: 'json',
        success: function(data) {
            if (parseInt(data.version) !== parseInt(sessionStorage["orderVersion"])) {
                sessionStorage["orderVersion"] = data.version;
                appendToOrderAlertArea(data.entries);
            }
            triggerAjaxOrderAlert();
        },
        error: function(error) {
            triggerAjaxOrderAlert();
        }
    });
}
function triggerAjaxOrderAlert() {
    setTimeout(ajaxOrderAlert, refreshRate);
}

function appendToOrderAlertArea(entries) {

    // add the relevant entries
    $.each(entries || [], function(index, alert){
        const content =`<div class="alert alert-info col-md-11 orderAlert" style="margin:0 auto;">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <div align="center">
                <strong>New Order from the store ${alert.storeName}!</strong> 
            </div>
            
            <u>Order #${alert.orderId}</u><br><u>Customer:</u> ${alert.customerName}<br>
            <u>Number of Items:</u> ${alert.numberOfItems}<br><u>Price of the Items:</u> ${alert.priceOfItems}$<br><u>DeliveryPrice:</u> ${alert.deliveryCost}$
        </div>`;
        $('#alertArea').append(content);
    });
}



function ajaxNewStoreAlert() {
    //storeAlertVersion = sessionStorage["newStoreVersion"];
    $.ajax({
        url: "stores-alert",
        data: {version : sessionStorage["newStoreVersion"]},
        dataType: 'json',
        success: function(data) {
            if (parseInt(data.version) !== parseInt(sessionStorage["newStoreVersion"])) {
                sessionStorage["newStoreVersion"] = data.version;
                appendToNewStoreAlertArea(data.entries);
            }
            triggerAjaxNewStoreAlert();
        },
        error: function(error) {
            triggerAjaxNewStoreAlert();
        }
    });
}
function triggerAjaxNewStoreAlert() {
    setTimeout(ajaxNewStoreAlert, refreshRate);
}

function appendToNewStoreAlertArea(entries) {

    // add the relevant entries
    $.each(entries || [], function(index, alert){
        const content =`<div class="alert alert-info col-md-11 orderAlert" style="margin:0 auto;">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <div align="center">
                <strong>New Store in the zone: ${alert.zoneName}!</strong> 
            </div>
            
            <u>Owner:</u> ${alert.storeOwnerName}<br><u>Store's Name:</u> ${alert.storeName}<br>
            <u>Location:</u> ${alert.location}<br><u>Number of Items:</u> ${alert.numberOfItems}
        </div>`;
        $('#alertArea').append(content);
    });


}

function ajaxFeedbackAlert() {

    $.ajax({
        url: "feedback-alert",
        data: {version : sessionStorage["feedbackVersion"]},
        dataType: 'json',
        success: function(data) {
            if (parseInt(data.version) !== parseInt(sessionStorage["feedbackVersion"])) {
                sessionStorage["feedbackVersion"] = data.version;
                appendToFeedbackAlertArea(data.entries);
            }
            triggerFeedbackAlert();
        },
        error: function(error) {
            triggerFeedbackAlert();
        }
    });
}
function triggerFeedbackAlert() {
    setTimeout(ajaxFeedbackAlert, refreshRate);
}

function appendToFeedbackAlertArea(entries) {

    // add the relevant entries
    $.each(entries || [], function(index, alert){
        const content =`<div class="alert alert-info col-md-11 orderAlert" style="margin:0 auto;">
            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
            <div align="center">
                <strong>You get a new feedback from the store ${alert.storeName}!</strong> 
            </div>
            
            <u>Customer:</u> ${alert.customerName}<br><u>Rating:</u> ${alert.rating}<br>
            <u>Message:</u> ${alert.message}
        </div>`;
        $('#alertArea').append(content);
    });


}



