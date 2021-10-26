const zone = sessionStorage["zone"];
var allTheStores;
var itemsToPick;
let choosenItems = new Map();
var choosenOffers = [];
var mode;
var allTheItems;
var storesOfDynamicOrderList;
var x,y;
var allDiscounts;
var storesOfSummary;
var checkboxOfferChoosen = -1;
var totalItemsPrice = 0.0,  totalDeliveryCost=0.0, totalPrice=0.0;
var date;

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

$(function(){
    $('.itemsContainer').hide();
    $('.resumeStoresContainer').hide();
    $('.discountsContainer').hide();
    $('.lastContainer').hide();
});

function ChoosenItem(serialNumber, productName, quantityChoosen){
    this.serialNumber = serialNumber;
    this.productName = productName;
    this.quantityChoosen = quantityChoosen;
}

class ChoosenOffer{
    constructor(itemID, quantity, forAdditional, storeID) {
        this.itemID = itemID;
        this.quantity = quantity;
        this.forAdditional = forAdditional;
        this.storeID =storeID;
    }
}

$(function (){
    $("#comboboxStores").attr('disabled','disabled');
});
function changeMode(checkBox){
    if(checkBox === "multipleStores"){
        $("#oneStore").attr('checked', false);
        $("#comboboxStores").attr('disabled','disabled');
        $(".deliveryPrice").text('');
        mode = 1;
    } else{
        $("#multipleStores").attr('checked', false);
        $("#comboboxStores").removeAttr('disabled');
        mode =2;
    }
}

function changeValue(){
    updateDeliveryPrice();
}

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
            loadStore(stores)
        },
    });


    return false;
});

function loadStore(stores) {
    $.each(stores|| [], function(index, store) {
        $('#comboboxStores').append($('<option>', {
            value: store.id,
            text : store.name
        }));
    });
}

function updateDeliveryPrice(){
    var choosenStore = $('#comboboxStores').val();

    if($('#locationX').val() !== '' && $('#locationY').val() !== '' && choosenStore!== "Choose a store"){
        var x, y, ppk;



        $.each(allTheStores || [], function(index, store) {
            if(store.id == choosenStore){
                x = store.x;
                y = store.y;
                ppk = store.PPK;
            }
        });

        var xCustomer, yCustomer;
        xCustomer = $("#locationX").val();
        yCustomer = $("#locationY").val();


        var delivery = Math.sqrt(Math.pow(Math.abs(x - xCustomer), 2) + Math.pow(Math.abs(y - yCustomer), 2));
        delivery = delivery * ppk;


        $(".deliveryPrice").text("Delivery price: " + delivery.toFixed(2) +"$");
    }

}

function confirmInput(){
    if(mode === 2 &&
        $('#comboboxStores').val() === "Choose a store"){
        return;
    }

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

    if(!$("#date").val()){
        return;
    }

    date = $("#date").val();

    $("#comboboxStores").attr('disabled','disabled');
    $("#locationX").attr('disabled','disabled');
    $("#locationY").attr('disabled','disabled');
    $("#date").attr('disabled','disabled');
    $("#multipleStores").attr('disabled','disabled');
    $("#oneStore").attr('disabled','disabled');
    $("#confirmButton").attr('disabled','disabled');



    $('.itemsContainer').show();
    //$(document).scrollTop($(document).height());
    //$("html, body").animate({ scrollTop: jQuery(window).height()}, 1500);
    $('html, body').animate({scrollTop:$(document).height()}, 'slow');

    if(mode === 1){
        updateItems(allTheItems);
    } else{
        var choosenStore = parseInt($('#comboboxStores').val());
        $.each(allTheStores || [], function(index, store) {
            if(store.id === choosenStore){
                storesOfDynamicOrderList = store;
                updateItems(store.items);
            }
        });
    }
}

//list of items to pick
function updateItems(itemsToChoose){

    $.each(itemsToChoose || [], function(index, item) {
        const content1 = `
            <div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 13rem; max-width: 13rem;">
                        <div class="card-header text-center">
                           ${item.productName} - ${item.serialNumber}
                        </div>
                        <div class="card-body">`;

        var content2 ='';

        if(mode === 2){
            content2=`<p class="card-text">Price per unity: ${item.price}<br></p>` ;
        }
        var content3 ='<p>Choose a quantity:</p>';
        var content4;
        var idItem = item.serialNumber;
        if(item.purchaseCategory === "Quantity"){
             content4 = '<input type="number"  id="' + idItem +'" class="quantity" name="quantity" min="1"  value="1" required onkeydown="return false"  placeholder="Quantity">';
        }
        else{
            content4 = '<input type="number"  id="' + idItem +'" class="quantity" name="quantity" min="0.1" step=".1" value="0.1" required onkeydown="return false" required placeholder="Quantity">';
        }


        const content5 = `
                            <br>
                            <button class="addToCartButton" onclick="addToCart(${item.serialNumber}, ' ${item.productName}')"> Add to cart</button>
                        </div>
                    </div>
                     `;

        const finalContent = content1 + content2 + content3 + content4 + content5;

        $('.ordersOrFeedbacksContainer').append(finalContent);
    });
}

function addToCart(itemId, name){
    const quantity = parseFloat($("input[id=" + itemId +"]").val());
    if(quantity){
        if(choosenItems.has(itemId)){
            choosenItems.get(itemId).quantityChoosen += quantity;
        }
        else{
            choosenItems.set(itemId, new ChoosenItem(itemId, name, quantity));
        }

        updateCart();
    }
}

function updateCart(){
    //clear all current zones
    $("#cartTable tbody").empty();

    for (let item of choosenItems.values()) {
        $("#dataCart").append('<tr> <td>' + item.productName + ' - ' + item.serialNumber
            + '</td>  <td ">' + item.quantityChoosen
            + '</td></tr>');
    }

}

function confirmInput2(){

    if($("#cartTable tbody").children().length !== 0){

        $(".addToCartButton").attr('disabled','disabled');
        $(".quantity").attr('disabled','disabled');
        $("#confirmItems").attr('disabled','disabled');


        if(mode === 1){
            $('.resumeStoresContainer').show();
            //$("html, body").animate({ scrollTop: jQuery(window).height()}, 1500);
            $('html, body').animate({scrollTop:$(document).height()}, 'slow');

            $.ajax({
                type: 'POST',
                data: {zoneName : zone, choosenItems : JSON.stringify(Array.from(choosenItems.entries())), x : x, y: y},
                dataType: 'json',
                url: "dynamic-order",
                timeout: 2000,
                error: function() {
                    //console.error("Failed to submit");
                },
                success: function(stores) {
                    storesOfDynamicOrderList = stores;
                    loadDynamicStores(stores)
                },
            });
            return false;
        }
        else{
            $('.discountsContainer').show();
            //$("html, body").animate({ scrollTop: jQuery(window).height()}, 1500);
            $('html, body').animate({scrollTop:$(document).height()}, 'slow');
            $.ajax({
                type: 'POST',
                data: {zoneName : zone, choosenItems : JSON.stringify(Array.from(choosenItems.entries())), storesList: JSON.stringify(storesOfDynamicOrderList), x : x, y: y},
                dataType: 'json',
                url: "static-order",
                timeout: 2000,
                error: function() {
                    //console.error("Failed to submit");
                },
                success: function(store) {
                    storesOfDynamicOrderList = store;
                    getDiscounts();
                },
            });
            return false;

        }
    }
}

function loadDynamicStores(stores){

    $.each(stores || [], function(index, store) {
        $("#dynamicStores").append('<tr> <td>' + store.name
            + '</td>  <td ">' + store.id
            + '</td>  <td ">' + store.location
            + '</td>  <td ">' + store.distance
            + '</td>  <td ">' + store.PPK
            + '</td>  <td ">' + store.delivery
            + '</td>  <td ">' + store.numberOfItems
            + '</td>  <td ">' + store.price
            + '</td></tr>');
    });
}

function getDiscounts(){
    //var stam = new ChoosenStores(storesOfDynamicOrderList);
    $.ajax({
        type: 'POST',
        data: {zoneName : zone, choosenItems : JSON.stringify(Array.from(choosenItems.entries())), storesList : JSON.stringify(storesOfDynamicOrderList),
        mode: mode},
        dataType: 'json',
        url: "discounts",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(discounts) {
            allDiscounts = discounts;
            loadDiscounts();
        },
    });
    return false;
}

function loadDiscounts(){
    var counter =0;
    var counter2 =0;
    $.each(allDiscounts || [], function(index, discount) {
        counter2++;
        if(discount.numberOfTimes > 0){
            counter++;
            var haveChoice;

            const content1 = `<div class="card cardItem2 text-white bg-secondary mb-3 " id="${discount.name.split(' ').join('')}" style="min-width: 20rem; max-width: 20rem;">
                    <div class="card-header text-center">
                        ${discount.name}
                    </div>
                    <div class="card-body detailsDiscount">
                        <p class="card-text text-center"> <u>${discount.operator} </u><br></p>`;
            var content2 ='';
            if(discount.operator === "GET one of them:"){
                haveChoice =1;
                $.each(discount.offers || [], function(index, offer) {
                    /*content2 += `<div>
                            <input type="checkbox" class="${discount.name.split(' ').join('')}" id="`+ offer.itemID +`" onchange="chooseOffer('${offer.itemID}' , '${discount.name.split(' ').join('')}' )">
                            <label>  ${offer.sentenceToPrint}</label>

                        </div>
`;*/
                    content2 += `
                            <label class="container">${offer.sentenceToPrint}
                                    <input type="radio"  class="offerRadioButton" name="radio" id="`+ offer.itemID +`" onchange="chooseOffer('${offer.itemID}')">
                                    <span class="checkmark"></span>
                            </label>
              
                                `;
                });
            } else{
                haveChoice = 0;
                $.each(discount.offers || [], function(index, offer) {
                    content2 += `<div> 
                            <p>  ${offer.sentenceToPrint}</p>
                        </div>
`;
                });
            }

            const content3 = `<div class="text-center">
                            <button class="addDiscountButton" onclick="addDiscount( '${discount.name}')">GET</button>
                        </div>

                    </div>
                </div>`;

            const finalContent = content1 + content2 + content3;

            $('#discounts').append(finalContent);
        }


    });

    if(counter === 0 || counter2 === 0){
        $('.discountsContainer').hide();
        nextButtonToSummary();
    } else{
        $('.discountsContainer').show();
        //$("html, body").animate({ scrollTop: jQuery(window).height()}, 1500);
        $('html, body').animate({scrollTop:$(document).height()}, 'slow');
    }
    
}

function chooseOffer(idOffer){
    checkboxOfferChoosen = idOffer;
}

function addDiscount(discountName){

    $.each(allDiscounts || [], function(index, discount) {
        if(discount.name === discountName){


            if(discount.operator === "GET one of them:"){
                if(checkboxOfferChoosen === -1){
                    return;
                } else{
                    $.each(discount.offers || [], function(index, offer) {
                        if(offer.itemID === parseInt(checkboxOfferChoosen)){
                            choosenOffers.push(new ChoosenOffer(offer.itemID, offer.quantity, offer.forAdditional , offer.storeID));
                            checkboxOfferChoosen = -1;
                        }

                    });
                }

            } else{
                $.each(discount.offers || [], function(index, offer) {
                    choosenOffers.push(new ChoosenOffer(offer.itemID, offer.quantity, offer.forAdditional, offer.storeID));
                });
            }

            discount.numberOfTimes -- ;

            $('#addDiscountContainer').text('The discount ' + discount.name + ' was added successfully to your cart.');

            $('#discounts').empty();

            loadDiscounts();
        }
    });

}

function nextButtonToDiscount(){
    $(".nextButton").attr('disabled','disabled');
    getDiscounts();
}

function nextButtonToSummary(){
    $(".addDiscountButton").attr('disabled','disabled');
    $(".offerRadioButton").attr('disabled','disabled');
    $("#nextButtonToSummary").attr('disabled','disabled');
    $('.lastContainer').show();
    //$("html, body").animate({ scrollTop: jQuery(window).height()}, 1500);
    //$('html, body').animate({scrollTop:$(document).height()}, 1000);
    getSummary();
}

function getSummary(){
    $.ajax({
        type: 'POST',
        data: {zoneName : zone, storesList : JSON.stringify(storesOfDynamicOrderList), offers : JSON.stringify(choosenOffers), mode : mode},
        dataType: 'json',
        url: "summary",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function(stores) {
            storesOfSummary = stores;
            loadSummary(stores)
        },
    });
}

function loadSummary(stores){
    calculatePrice();

    $.each(stores || [], function(index, store) {

        var content2 ='';

         content2 =`<div class="card cardItem2 text-white bg-secondary mb-3 " align="center" style="min-width: 30rem;max-width: 30rem;">
                            <div class="card-header text-center">
                                <h5 class="card-title">${store.name} - ${store.id}</h5>
                            </div>
                            <br>
                            <p class="card-text text-center">Items ordered: </p>
                            <div class="card-body main" style="max-width: 28rem;">`;


        $.each(store.itemsFromOrder || [], function(index, item) {
            content2 += `<div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 18rem; max-width: 18rem;">
                                    <div class="card-header text-center">
                                        ${item.productName} - ${item.serialNumber}
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">Purchase Mode: ${item.purchaseCategory}<br>Quantity: ${item.quantityChoosen}
                                            <br>Price per unity: ${item.price}<br>Total price: ${item.totalPrice}<br>From discount: ${item.fromDiscount}</p>
                                    </div>
                                </div>`

        });

        $.each(store.itemsFromOffer || [], function(index, item) {
            content2 += `<div class="card cardItem2 text-white bg-secondary mb-3 text-center" style="min-width: 18rem; max-width: 18rem;">
                                    <div class="card-header text-center">
                                        ${item.productName} - ${item.serialNumber}
                                    </div>
                                    <div class="card-body">
                                        <p class="card-text">Purchase Mode: ${item.purchaseCategory}<br>Quantity: ${item.quantityChoosen}
                                            <br>Price per unity: ${item.price}<br>Total price: ${item.totalPrice}<br>From discount: ${item.fromDiscount}</p>
                                    </div>
                                </div>`

        });

        content2 += `</div>
                            <p class="card-text text-center">PPK: ${store.PPK}<br>Distance: ${store.distance.toFixed(2)}<br>Delivery: ${store.delivery.toFixed(2)}$</p>
                            <br>
                        </div>`;


        $('.storesOfSummaryContainer').append(content2);

    });

     content3 =`
                   


                    <p class="card-text text-center">Total Price of Items: ` + totalItemsPrice +`$<br>Total Price of Deliveries: ` + totalDeliveryCost +`$<br>Total Price of the Order: ` + totalPrice +`$</p>
                    <br>
                    <br>

                </div>
`;



    $('.summaryContainer').append(content3);


}

function calculatePrice(){
    $.each(storesOfSummary || [], function(index, storeOfSummary) {

        totalDeliveryCost = parseFloat(totalDeliveryCost ) +parseFloat(storeOfSummary.delivery);
        totalDeliveryCost = totalDeliveryCost.toFixed(2);

        $.each(storeOfSummary.itemsFromOrder || [], function(index, itemInCart) {
            totalItemsPrice += parseFloat(itemInCart.totalPrice);
        });

        $.each(storeOfSummary.itemsFromOffer || [], function(index, itemInCart) {
            totalItemsPrice += parseFloat(itemInCart.totalPrice);
        });

    });
    totalItemsPrice = totalItemsPrice.toFixed(2);
    totalPrice = parseFloat(totalItemsPrice) + parseFloat(totalDeliveryCost);
    totalPrice = totalPrice.toFixed(2);
}

function makeOrder(){
    prepareFeedbacksForm(zone, date, storesOfSummary);
    $.ajax({
        type: 'POST',
        data: {zoneName : zone,  storesList : JSON.stringify(storesOfSummary), date: date, x : x, y: y},
        dataType: 'json',
        url: "make-order",
        timeout: 2000,
        error: function() {
            //console.error("Failed to submit");
        },
        success: function() {

        },
    });
    return false;


}

function cancelOrder(){
    window.location.assign("customerZone.html");
}