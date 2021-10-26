const zone = sessionStorage["zone"];
var allTheStores, allTheItems;
let choosenItems = new Map();
var x, y, storeName, ppk;


function ChoosenItem(serialNumber, productName, price){
    this.serialNumber = serialNumber;
    this.productName = productName;
    this.price = price;
}


$(function(){
    $('.itemsContainer').hide();
});

$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "zones-items",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(items) {
            allTheItems = items;
        },
    });


    return false;
});

$(function (){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone},
        dataType: 'json',
        url: "zones-stores",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(stores) {
            allTheStores = stores;
        },
    });


    return false;
});

function confirmInput(){
    x = $("#locationX").val();
    y = $("#locationY").val();

    var check = false;

    $.each(allTheStores || [], function(index, store) {
        if(parseInt(x) === parseInt(store.x) && parseInt(y) === parseInt(store.y)){
            $('.error').text("This location already exists. Please change your location");
            check = true;
        }
    });

    if(check === true){
        return;
    }

    storeName = $("#storeName").val();

    if( storeName=== ''){
        return;
    }

    ppk = $("#PPK").val()



    $("#locationX").attr('disabled','disabled');
    $("#locationY").attr('disabled','disabled');
    $("#storeName").attr('disabled','disabled');
    $("#PPK").attr('disabled','disabled');
    $("#confirmButton").attr('disabled','disabled');



    $('.itemsContainer').show();
    $('html, body').animate({scrollTop:$(document).height()}, 'slow');


    updateItems(allTheItems);

}

//list of items to pick
function updateItems(itemsToChoose){

    $.each(itemsToChoose || [], function(index, item) {
        var idItem = item.serialNumber;
        const content1 = `
            <div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 13rem; max-width: 13rem;">
                        <div class="card-header text-center">
                           ${item.productName} - ${item.serialNumber}
                        </div>
                        <div class="card-body">`;

        var content2 ='<p>Choose a price:</p>';





        var  content3 = '<input type="number"  id="' + idItem +'"  min="1"  value="1" required onkeydown="return false"  placeholder="Price">';



        const content4 = `
                            <br>
                          
                            <button class="addToCartButton" onclick="addToCart(${item.serialNumber}, ' ${item.productName}')" > Add to cart</button>
                        </div>
                    </div>
                     `;

        const finalContent = content1 + content2 + content3 + content4;

        $('.ordersOrFeedbacksContainer').append(finalContent);
    });
}

function addToCart(itemId, name){
    const price = parseFloat($("input[id=" + itemId +"]").val());
    if(price){
        choosenItems.set(itemId, new ChoosenItem(itemId, name, price));
        $('#' + itemId).parent().parent().remove();
        updateChoosenItems();
        if($('.ordersOrFeedbacksContainer').children().length === 0){
            $('.ordersOrFeedbacksContainer').append('<h4 style="padding-top: 20%; padding-left: 20%">There are no more items to choose!</h4>')
        }
    }
}

function updateChoosenItems(){
    //clear all current zones
    $("#itemsTable tbody").empty();

    for (let item of choosenItems.values()) {
        $("#dataItems").append('<tr> <td>' + item.productName + ' - ' + item.serialNumber
            + '</td>  <td ">' + item.price
            + '</td></tr>');
    }

}

function confirmInput2(){
    if($("#itemsTable tbody").children().length !== 0){
        var modal = document.getElementById("confirmAddStore");
        modal.style.display = "block";
    }

    $.ajax({
        type: 'POST',
        data: {zoneName : zone,  choosenItems : JSON.stringify(Array.from(choosenItems.entries())), storeName: storeName, ppk : ppk, x : x, y: y},
        dataType: 'json',
        url: "add-store",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function() {

        },
    });
    return false;
}

function closeAddStoreForm(){
    window.location.assign("ownerZone.html");
}